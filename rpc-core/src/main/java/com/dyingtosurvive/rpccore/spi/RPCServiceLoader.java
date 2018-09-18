package com.dyingtosurvive.rpccore.spi;

import java.util.ServiceLoader;

/**
 * 使用JDK的ＳＰＩ来做插件
 * Created by change-solider on 2018-9-10.
 */
public class RPCServiceLoader {
    public static <T> ServiceLoader<T> load(Class<T> clzz) {
        ServiceLoader<T> operations = ServiceLoader.load(clzz);
        return operations;
    }
}
