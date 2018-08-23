package com.dyingtosurvive.rpccore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class ServiceProxyHandler<T> implements InvocationHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceProxyHandler.class);
    private static String mapping = "interface org.springframework.web.bind.annotation.RequestMapping";
    private static String requestParam = "interface org.springframework.web.bind.annotation.RequestParam";
    private static String pathVariable = "interface org.springframework.web.bind.annotation.PathVariable";
    private static String requestBody = "interface org.springframework.web.bind.annotation.RequestBody";
    private Class<T> clazz;

    public ServiceProxyHandler(Class<T> clazz) {
        System.out.println("new ServiceProxyHandler :" + System.currentTimeMillis());
        this.clazz = clazz;
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

        //
        String url = "http://127.0.0.1:8080/tripservice/internal/";

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

        //CloseableHttpClient httpClient  = HttpClientFactory.getHttpClientWithRetry();
        //String retData = JavaClientHelper.instance().sendHttpGetRequest(httpClient, url.toString());
        return null;
    }


}
