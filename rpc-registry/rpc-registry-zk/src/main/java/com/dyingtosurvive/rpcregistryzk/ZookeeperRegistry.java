package com.dyingtosurvive.rpcregistryzk;



import com.dyingtosurvive.rpccore.common.URL;
import com.dyingtosurvive.rpccore.common.ZKNode;
import com.dyingtosurvive.rpccore.registry.Registry;
import org.I0Itec.zkclient.ZkClient;



public class ZookeeperRegistry implements Registry {
    private ZkClient zkClient;
    private URL url;

    public ZookeeperRegistry(URL url, ZkClient zkClient) {
        this.zkClient = zkClient;
        this.url = url;
    }

    @Override
    public void register(ZKNode node) {
        System.out.println("register is called");
        System.out.println("node:" + node.getPackageName());

        String path = ZKUtils.parseNodeToPath(node);
        if (!zkClient.exists(path)) {
            zkClient.createPersistent(path, true);
        }
        zkClient.createEphemeral(path +"/" + node.getIp() + ":" + node.getPort(), node.getProjectName());
    }

    @Override
    public void unregister(ZKNode url) {

    }
}
