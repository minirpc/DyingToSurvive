package com.dyingtosurvive.rpccore.registry;


import com.dyingtosurvive.rpccore.common.URL;


public interface RegistryFactory {
    Registry getRegistry(URL url);
}
