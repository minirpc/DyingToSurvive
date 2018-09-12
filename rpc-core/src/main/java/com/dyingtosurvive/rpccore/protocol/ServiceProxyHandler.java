package com.dyingtosurvive.rpccore.protocol;

import com.dyingtosurvive.rpccore.common.URL;
import com.dyingtosurvive.rpccore.common.ZKNode;
import com.dyingtosurvive.rpccore.register.RegistryConfig;
import com.dyingtosurvive.rpccore.registry.Registry;
import com.dyingtosurvive.rpccore.registry.RegistryFactory;
import com.dyingtosurvive.rpccore.spi.RPCServiceLoader;
import com.mashape.unirest.http.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;


public class ServiceProxyHandler<T> implements InvocationHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceProxyHandler.class);
    private static String mapping = "interface org.springframework.web.bind.annotation.RequestMapping";
    private static String requestParam = "interface org.springframework.web.bind.annotation.RequestParam";
    private static String pathVariable = "interface org.springframework.web.bind.annotation.PathVariable";
    private static String requestBody = "interface org.springframework.web.bind.annotation.RequestBody";
    private Class<T> clazz;
    private List<RegistryConfig> registryConfigs;

    public ServiceProxyHandler(Class<T> clazz, List<RegistryConfig> registryConfigs) {
        System.out.println("new ServiceProxyHandler :" + System.currentTimeMillis());
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
        System.out.println("objectproxy invode method is called");
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

        List<ZKNode> nodes = getServerInfo();


        //动态拼装url
        String url =
            "http://" + nodes.get(0).getIp() + ":" + nodes.get(0).getPort() + "/" + nodes.get(0).getProjectName();

        System.out.println(method.getDeclaringClass().getName());
        System.out.println(method.getName());
        for (int i = 0; i < method.getParameterTypes().length; ++i) {
            System.out.println(method.getParameterTypes()[i].getName());
            System.out.println(method.getParameterTypes()[i].getCanonicalName());
            System.out.println(method.getParameterTypes()[i].getSimpleName());
            System.out.println(method.getParameterTypes()[i].getTypeName());
        }
        for (int i = 0; i < args.length; ++i) {
            System.out.println(args[i].toString());
        }
        String mapurl = "";
        for (Annotation an : method.getAnnotations()) {
            if (an.annotationType().toString().equals(mapping)) {
                mapurl = mappingGetValue(an);
                System.out.println(mapurl);
            }
        }
        List<String> params = new ArrayList<>();
        for (Annotation[] annotations : method.getParameterAnnotations()) {
            for (Annotation paramAn : annotations) {
                if (paramAn.annotationType().toString().equals(requestParam)) {
                    System.out.println(
                        paramAn.annotationType().getDeclaredMethod("value", null).invoke(paramAn).toString());
                    params.add(paramAn.annotationType().getDeclaredMethod("value", null).invoke(paramAn).toString());
                }
            }
        }
        for (int i = 0; i < args.length; ++i) {
            System.out.println(args[i].toString());
            params.get(i);
            mapurl = mapurl + "?" + params.get(i) + "=" + args[i].toString();
            System.out.println(mapurl);
        }
        url = url.concat(mapurl);
        System.out.println("url :" + url);
        //String retData = Unirest.get(url).queryString("name", name).asJson().getBody().getObject();
        String result = Unirest.get(url).asString().getBody();
        return result;
    }

    public List<ZKNode> getServerInfo() {
        System.out.println("registryConfigs" + registryConfigs.size());
        String[] registryInfo = registryConfigs.get(0).getAddress().split(":");
        URL url = new URL();
        url.setIp("10.42.0.7");
        url.setPort("2181");


        //使用jdk提供的ServiceLoader来做ＳＰＩ设计
        ServiceLoader<RegistryFactory> factories = RPCServiceLoader.load(RegistryFactory.class);
        Iterator<RegistryFactory> operationIterator = factories.iterator();
        while (operationIterator.hasNext()) {
            RegistryFactory operation = operationIterator.next();
            Registry registry = operation.getRegistry(url);
            ZKNode node = new ZKNode();
            //todo 待从配置文件中得到ip和port和servername
            System.out.println("interfaceName" + clazz.getName());
            node.setPackageName("com.dyingtosurvive.rpcinterface.IHelloService");
            List<ZKNode> nodes = registry.discoverService(node);
            for (ZKNode node1 : nodes) {
                System.out.println(node1.getIp());
            }
            return nodes;
        }
        return null;
    }
}
