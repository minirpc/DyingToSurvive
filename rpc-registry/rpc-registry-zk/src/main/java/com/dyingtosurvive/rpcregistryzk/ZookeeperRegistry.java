package com.dyingtosurvive.rpcregistryzk;



import com.dyingtosurvive.rpccore.common.URL;
import com.dyingtosurvive.rpccore.common.ZKNode;
import com.dyingtosurvive.rpccore.registry.Registry;
import org.I0Itec.zkclient.ZkClient;

import java.util.ArrayList;
import java.util.List;



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
        zkClient.createEphemeral(path + "/" + node.getIp() + ":" + node.getPort(), node.getProjectName());
    }

    @Override
    public void unregister(ZKNode url) {

    }

    @Override
    public List<ZKNode> discoverService(ZKNode node) {
        String parentPath = ZKUtils.parseNodeToPath(node);
        List<String> children = new ArrayList<String>();
        if (zkClient.exists(parentPath)) {
            children = zkClient.getChildren(parentPath);
        }
        List<ZKNode> nodes = new ArrayList<>();
        for (String str : children) {
            System.out.println("url:" + str);
            String projectName = zkClient.readData(parentPath + "/" + str);
            System.out.println("projectname:" + projectName);
            ZKNode zkNode = new ZKNode();
            String[] ips = str.split(":");
            zkNode.setIp(ips[0]);
            zkNode.setPort(ips[1]);
            zkNode.setProjectName(projectName);
            nodes.add(zkNode);
        }
        return nodes;
    }
}
