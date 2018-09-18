package com.dyingtosurvive.rpccore.proxy;

import com.dyingtosurvive.rpccore.common.TraceLog;
import com.dyingtosurvive.rpccore.common.ZKInfo;
import com.dyingtosurvive.rpccore.common.ZKNode;
import com.dyingtosurvive.rpccore.lb.LoadBalance;
import com.dyingtosurvive.rpccore.registry.RegistryConfig;
import com.dyingtosurvive.rpccore.registry.Registry;
import com.dyingtosurvive.rpccore.registry.RegistryFactory;
import com.dyingtosurvive.rpccore.spi.RPCServiceLoader;
import com.dyingtosurvive.rpccore.trace.ServiceLogger;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.UUID;

/**
 * rpc:reference动态代理
 * <p>
 * created by changesolider on 2018-09-18
 *
 * @param <T>
 */
public class ServiceProxyHandler<T> implements InvocationHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceProxyHandler.class);
    private static String mapping = "interface org.springframework.web.bind.annotation.RequestMapping";
    private static String requestParam = "interface org.springframework.web.bind.annotation.RequestParam";
    private static String pathVariable = "interface org.springframework.web.bind.annotation.PathVariable";
    private static String requestBody = "interface org.springframework.web.bind.annotation.RequestBody";
    private Class<T> clazz;
    private List<RegistryConfig> registryConfigs;

    public ServiceProxyHandler(Class<T> clazz, List<RegistryConfig> registryConfigs) {
        this.clazz = clazz;
        this.registryConfigs = registryConfigs;
    }

    public static String mappingGetValue(Annotation an) throws Exception {
        String[] urls = (String[]) an.annotationType().getDeclaredMethod("value", null).invoke(an);
        if (urls.length == 0) {
            return "/";
        } else {
            return urls[0];
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class == method.getDeclaringClass()) {
            String name = method.getName();
            if ("equals".equals(name)) {
                return proxy == args[0];
            } else if ("hashCode".equals(name)) {
                return System.identityHashCode(proxy);
            } else if ("toString".equals(name)) {
                return proxy.getClass().getName() + "@" +
                    Integer.toHexString(System.identityHashCode(proxy)) +
                    ", with InvocationHandler " + this;
            } else {
                throw new IllegalStateException(String.valueOf(method));
            }
        }
        //发现服务
        List<ZKNode> services = discoverService();
        if (services == null || services.size() == 0) {
            throw new IllegalStateException("没有找到服务提供者!");
        }

        //默认第一个服务
        ZKNode choseNode = services.get(0);
        //SPI负载均衡，可选插件
        ServiceLoader<LoadBalance> factories = RPCServiceLoader.load(LoadBalance.class);
        Iterator<LoadBalance> operationIterator = factories.iterator();
        while (operationIterator.hasNext()) {
            LoadBalance operation = operationIterator.next();
            choseNode = operation.select(services);
        }
        //组装url
        String url = assembleRequestUrl(choseNode, method, args);
        System.out.println("url :" + url);
        //发送http请求
        HttpResponse<String> response = Unirest.get(url).asString();
        String result = response.getBody();

        //trace处理
        handleTrace(url, response, result);
        return result;
    }

    private void handleTrace(String url, HttpResponse<String> response, String result) {
        //SPI日志追踪，可选插件
        ServiceLoader<ServiceLogger> serviceLoggers = RPCServiceLoader.load(ServiceLogger.class);
        Iterator<ServiceLogger> serviceLoggerIterator = serviceLoggers.iterator();
        if (!serviceLoggerIterator.hasNext()) {
            return;
        }
        ServiceLogger serviceLogger = serviceLoggerIterator.next();
        TraceLog traceLog = new TraceLog();
        traceLog.setFromProject("rpcclient");
        traceLog.setRequestUrl(url);
        traceLog.setToProject("rpcserver");
        traceLog.setRequestTimestamp(new Date());
        traceLog.setResponseTimestamp(new Date());
        traceLog.setRequestDuration(traceLog.getResponseTimestamp().getTime() - traceLog.getRequestTimestamp().getTime());
        traceLog.setRequestId(UUID.randomUUID().toString());
        traceLog.setTraceId(UUID.randomUUID().toString());
        traceLog.setResponseCode(response.getStatusText());
        traceLog.setResponseBody(result);
        serviceLogger.writeLog(traceLog);
    }


    private String assembleRequestUrl(ZKNode choseNode, Method method, Object[] args) throws Exception {
        //动态拼装url
        String url = "http://" + choseNode.getIp() + ":" + choseNode.getPort() + "/" + choseNode.getProjectName();

        String mapurl = "";
        for (Annotation an : method.getAnnotations()) {
            if (an.annotationType().toString().equals(mapping)) {
                mapurl = mappingGetValue(an);
            }
        }
        List<String> params = new ArrayList<>();
        for (Annotation[] annotations : method.getParameterAnnotations()) {
            for (Annotation paramAn : annotations) {
                if (paramAn.annotationType().toString().equals(requestParam)) {
                    params.add(paramAn.annotationType().getDeclaredMethod("value", null).invoke(paramAn).toString());
                }
            }
        }
        for (int i = 0; i < args.length; ++i) {
            params.get(i);
            mapurl = mapurl + "?" + params.get(i) + "=" + args[i].toString();
        }
        url = url.concat(mapurl);
        return url;
    }


    /**
     * 发现服务
     *
     * @return
     */
    private List<ZKNode> discoverService() {
        //使用ServiceLoader查找注册中心的实现
        ServiceLoader<RegistryFactory> factories = RPCServiceLoader.load(RegistryFactory.class);
        Iterator<RegistryFactory> operationIterator = factories.iterator();
        if (!operationIterator.hasNext()) {
            throw new IllegalStateException("请提供RegistryFactory的实现!");
        }
        RegistryFactory registryFactory = operationIterator.next();
        //连接zk
        Registry registry = registryFactory.getRegistry(getZKInfoFromRegistries());
        ZKNode node = new ZKNode();
        node.setPackageName(clazz.getName());
        //发现服务
        List<ZKNode> nodes = registry.discoverService(node);
        return nodes;
    }

    private ZKInfo getZKInfoFromRegistries() {
        String[] registryInfo = registryConfigs.get(0).getAddress().split(":");
        ZKInfo zkInfo = new ZKInfo();
        //zkInfo.setIp(registryInfo[0]);
        zkInfo.setIp("10.42.0.7");
        //zkInfo.setPort(registryInfo[1]);
        zkInfo.setPort("2181");
        return zkInfo;
    }
}
