package com.dyingtosurvive.rpcregistryzk;


import com.dyingtosurvive.rpccore.common.URL;
import com.dyingtosurvive.rpccore.registry.Registry;
import com.dyingtosurvive.rpccore.registry.RegistryFactory;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkException;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class ZookeeperRegistryFactory  implements RegistryFactory {
    @Override
    public Registry getRegistry(URL url) {
        try {
            ZkClient zkClient = new ZkClient(url.getIp()+":"+url.getPort());
            return new ZookeeperRegistry(url, zkClient);
        } catch (ZkException e) {
            throw e;
        }
    }
}
