package com.dyingtosurvive.rpccore;


import java.lang.reflect.Proxy;

/**
 * Created by change-solider on 18-8-21.
 */
public class ServiceCreateHelper {
    public static <T> T buildService(Class<T> interfaceClass) {
        //动态代理创建接口下的定义对象
        return (T) Proxy.newProxyInstance(
            interfaceClass.getClassLoader(),
            new Class<?>[] {interfaceClass},
            new ServiceProxyHandler<T>(interfaceClass)
        );
    }
}
