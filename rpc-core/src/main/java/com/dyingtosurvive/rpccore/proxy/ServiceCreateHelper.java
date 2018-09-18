package com.dyingtosurvive.rpccore.proxy;


import com.dyingtosurvive.rpccore.registry.RegistryConfig;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * JDK动态代理
 * Created by change-solider on 18-8-21.
 */
public class ServiceCreateHelper {
    public static <T> T buildService(Class<T> interfaceClass, List<RegistryConfig> registryConfigList) {
        //动态代理创建接口下的定义对象
        return (T) Proxy.newProxyInstance(
            interfaceClass.getClassLoader(),
            new Class<?>[] {interfaceClass},
            new ServiceProxyHandler<T>(interfaceClass, registryConfigList)
        );
    }
}
