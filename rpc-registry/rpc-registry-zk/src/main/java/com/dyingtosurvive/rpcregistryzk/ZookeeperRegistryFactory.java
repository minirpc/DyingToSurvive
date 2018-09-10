package com.dyingtosurvive.rpcregistryzk;


import com.dyingtosurvive.rpccore.common.URL;
import com.dyingtosurvive.rpccore.registry.Registry;
import com.dyingtosurvive.rpccore.registry.RegistryFactory;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkException;


/**
 * 使用spi插件式的来使用决定注册中心
 */
public class ZookeeperRegistryFactory  implements RegistryFactory {
    @Override
    public Registry getRegistry(URL url) {
        System.out.println("ZookeeperRegistryFactory getRegistry method is called");
        try {
            ZkClient zkClient = new ZkClient(url.getIp()+":"+url.getPort());
            return new ZookeeperRegistry(url, zkClient);
        } catch (ZkException e) {
            throw e;
        }
    }
}
