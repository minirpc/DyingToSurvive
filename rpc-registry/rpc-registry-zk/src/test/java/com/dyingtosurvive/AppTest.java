package com.dyingtosurvive;

import static org.junit.Assert.assertTrue;

import com.dyingtosurvive.rpccore.common.URL;
import com.dyingtosurvive.rpccore.common.ZKNode;
import com.dyingtosurvive.rpccore.registry.Registry;
import com.dyingtosurvive.rpccore.registry.RegistryFactory;
import com.dyingtosurvive.rpcregistryzk.ZookeeperRegistryFactory;
import org.junit.Test;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        URL url = new URL();
        url.setIp("10.42.0.6");
        url.setPort("2181");
        RegistryFactory registryFactory = new ZookeeperRegistryFactory();
        Registry registry = registryFactory.getRegistry(url);
        for (int i = 0 ; i < 3; i ++) {
            ZKNode node = new ZKNode();
            node.setIp("127.0.0." + (i + 1));
            node.setPort("8080");
            node.setPackageName("com.dyingtosurvive.rpcinterface.IHelloService");
            node.setRole("providers");
            node.setProjectName("rpc-server");
            registry.register(node);
            registry.discoverService(node);
        }


        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
