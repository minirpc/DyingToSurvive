package com.dyingtosurvive.rpccore.spi;

import java.util.ServiceLoader;

/**
 * Created by change-solider on 18-9-10.
 */
public class RPCServiceLoader<T> {
    public static <T> ServiceLoader<T> load(Class clzz) {
        System.out.println("classPath:"+System.getProperty("java.class.path"));
        ServiceLoader<T> operations = ServiceLoader.load(clzz);
        return operations;
    }
}
