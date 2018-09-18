package com.dyingtosurvive.rpccore.registry;


import com.dyingtosurvive.rpccore.common.ZKInfo;

/**
 * 注册中心工厂
 * Created by change-solider on 18-9-18.
 */
public interface RegistryFactory {
    Registry getRegistry(ZKInfo url);
}
