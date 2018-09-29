package com.dyingtosurvive.rpccore.proxy;

import com.alibaba.fastjson.JSONObject;
import com.dyingtosurvive.rpccore.common.RPCHttpRequest;
import com.dyingtosurvive.rpcinterface.model.TraceLog;
import com.dyingtosurvive.rpccore.common.ZKInfo;
import com.dyingtosurvive.rpcinterface.model.GetAvailableServiceResponse;
import com.dyingtosurvive.rpcinterface.model.GetCanUseServiceRequest;
import com.dyingtosurvive.rpcinterface.model.ZKNode;
import com.dyingtosurvive.rpccore.lb.LoadBalance;
import com.dyingtosurvive.rpccore.registry.RegistryConfig;
import com.dyingtosurvive.rpccore.registry.Registry;
import com.dyingtosurvive.rpccore.registry.RegistryFactory;
import com.dyingtosurvive.rpccore.spi.RPCServiceLoader;
import com.dyingtosurvive.rpcinterface.service.IServiceLogger;
import com.dyingtosurvive.rpcinterface.service.IWeightService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

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
    private static final Logger logger = LoggerFactory.getLogger(ServiceProxyHandler.class);
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

    public static String mappingGetHttpMethod(Annotation an) throws Exception {
        RequestMethod[] methods = (RequestMethod[]) an.annotationType().getDeclaredMethod("method", null).invoke(an);
        if (methods == null || methods.length == 0) {
            return "GET";
        }
        return methods[0].name();
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
        return handleRequest(method, args);
    }

    private Object handleRequest(Method method, Object[] args) throws Exception {
        //1.发现可用服务
        List<ZKNode> services = discoverService();
        if (services == null || services.size() == 0) {
            throw new IllegalStateException("没有找到服务提供者!");
        }

        //2.调用配置中心，得到权重比对后的服务列表,此处需要防止循环调用
        IWeightService calcWeightService = null;
        if (!clazz.equals(IWeightService.class)) {
            //调用配置服务得到服务权重等信息
            calcWeightService = ServiceCreateHelper.buildService(IWeightService.class, registryConfigs);
            GetCanUseServiceRequest request = new GetCanUseServiceRequest();
            request.setServiceName(clazz.getName());
            request.setServices(services);
            try {
                GetAvailableServiceResponse response = calcWeightService.getCanUseService(request);
                if (response != null) {
                    List<ZKNode> shouldUseService = response.getAvailableServices();
                    if (shouldUseService != null && shouldUseService.size() > 0) {
                        //经过配置中心挑选出的服务才是lb可负载的范围
                        services = shouldUseService;
                        System.out.println("shouldUseService size : " + shouldUseService.size());
                    }
                }

            } catch (Exception e) {
                logger.error("未提供配置中心实现，无法使用配置中心做服务权重负载!");
            }
        }

        //3.负载均衡选择服务
        ZKNode choseNode = choseNodeFromLoadBalance(services);

        //4.处理http请求
        Object resutl = handHttpRequest(choseNode, method, args);

        //5.如果有配置中心，则写入权重信息
        if (calcWeightService != null) {
            try {
                calcWeightService.writeWeightInfo(choseNode);
            } catch (Exception e) {
                logger.error("未提供配置中心实现，无法使用配置中心写入服务调用情况!");
            }
        }
        return resutl;
    }

    private Object handHttpRequest(ZKNode choseNode, Method method, Object[] args) throws Exception {
        //组装url
        RPCHttpRequest request = generateRPCHttpRequest(choseNode, method, args);
        System.out.println("url :" + request.getUrl());
        //发送http请求

        HttpResponse<String> response = null;
        if ("GET".equals(request.getMethod())) {
            response = Unirest.get(request.getUrl()).asString();
        } else if ("POST".equals(request.getMethod())) {
            response = Unirest.post(request.getUrl()).header("Content-Type", "application/json").body(
                JSONObject.toJSONString(request.getBody())).asString();
        }
        System.out.println("methodreturntype:" + method.getReturnType());
        Object object = JSONObject.parseObject(response.getBody(), method.getReturnType());

        //trace处理
        handleTrace(request.getUrl(), response, object);
        return object;
    }



    private ZKNode choseNodeFromLoadBalance(List<ZKNode> services) {
        //默认第一个服务
        ZKNode choseNode = services.get(0);
        //SPI负载均衡，可选插件
        ServiceLoader<LoadBalance> factories = RPCServiceLoader.load(LoadBalance.class);
        Iterator<LoadBalance> operationIterator = factories.iterator();
        while (operationIterator.hasNext()) {
            LoadBalance operation = operationIterator.next();
            choseNode = operation.select(services);
        }
        return choseNode;
    }

    private void handleTrace(String url, HttpResponse<String> response, Object result) {
        //todo 发送日志给rpc-trace-es


        //SPI日志追踪，可选插件
        ServiceLoader<IServiceLogger> serviceLoggers = RPCServiceLoader.load(IServiceLogger.class);
        Iterator<IServiceLogger> serviceLoggerIterator = serviceLoggers.iterator();
        if (!serviceLoggerIterator.hasNext()) {
            return;
        }
        IServiceLogger serviceLogger = serviceLoggerIterator.next();
        TraceLog traceLog = new TraceLog();
        traceLog.setFromProject("rpcclient");
        traceLog.setRequestUrl(url);
        traceLog.setToProject("rpcserver");
        traceLog.setRequestTimestamp(new Date());
        traceLog.setResponseTimestamp(new Date());
        traceLog
            .setRequestDuration(traceLog.getResponseTimestamp().getTime() - traceLog.getRequestTimestamp().getTime());
        traceLog.setRequestId(UUID.randomUUID().toString());
        traceLog.setTraceId(UUID.randomUUID().toString());
        traceLog.setResponseCode(response.getStatusText());
        traceLog.setResponseBody(result);
        serviceLogger.writeLog(traceLog);
    }


    private RPCHttpRequest generateRPCHttpRequest(ZKNode choseNode, Method method, Object[] args) throws Exception {
        RPCHttpRequest httpRequest = new RPCHttpRequest();
        //动态拼装url
        String url = "http://" + choseNode.getIp() + ":" + choseNode.getPort() + "/" + choseNode.getProjectName();

        String mapurl = "";
        for (Annotation an : method.getAnnotations()) {
            if (an.annotationType().toString().equals(mapping)) {
                mapurl = mappingGetValue(an);
            }
        }
        String httpMethod = "";
        for (Annotation an : method.getAnnotations()) {
            if (an.annotationType().toString().equals(mapping)) {
                httpMethod = mappingGetHttpMethod(an);
                break;
            }
        }
        System.out.println("httpmethod:" + httpMethod);
        if ("GET".equals(httpMethod)) {
            List<String> params = new ArrayList<>();
            for (Annotation[] annotations : method.getParameterAnnotations()) {
                for (Annotation paramAn : annotations) {
                    if (paramAn.annotationType().toString().equals(requestParam)) {
                        params
                            .add(paramAn.annotationType().getDeclaredMethod("value", null).invoke(paramAn).toString());
                    }
                }
            }
            for (int i = 0; i < args.length; ++i) {
                params.get(i);
                mapurl = mapurl + "?" + params.get(i) + "=" + args[i].toString();
            }
            url = url.concat(mapurl);
        } else {
            url = url.concat(mapurl);
        }

        httpRequest.setMethod(httpMethod);
        if ("POST".equals(httpMethod) && args.length == 1) {
            httpRequest.setBody(args[0]);
        }
        httpRequest.setUrl(url);
        return httpRequest;
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
        //todo zkInfo.setIp(registryInfo[0]);
        zkInfo.setIp("10.42.0.7");
        //todo zkInfo.setPort(registryInfo[1]);
        zkInfo.setPort("2181");
        return zkInfo;
    }
}
