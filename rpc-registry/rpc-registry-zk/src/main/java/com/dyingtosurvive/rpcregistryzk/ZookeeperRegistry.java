package com.dyingtosurvive.rpcregistryzk;



import com.dyingtosurvive.rpccore.common.ZKInfo;
import com.dyingtosurvive.rpcinterface.model.ZKNode;
import com.dyingtosurvive.rpccore.registry.Registry;
import org.I0Itec.zkclient.ZkClient;

import java.util.ArrayList;
import java.util.List;


/**
 * ZK实现的注册中心
 * Created by change-solider on 18-9-18.
 */
public class ZookeeperRegistry implements Registry {
    private ZkClient zkClient;
    private ZKInfo zkInfo;

    public ZookeeperRegistry(ZKInfo zkInfo, ZkClient zkClient) {
        this.zkClient = zkClient;
        this.zkInfo = zkInfo;
    }

    /**
     * 注册服务
     *
     * @param node
     */
    @Override
    public void registerService(ZKNode node) {
        String path = ZKUtils.parseNodeToPath(node);
        if (!zkClient.exists(path)) {
            zkClient.createPersistent(path, true);
        }
        zkClient.createEphemeral(path + "/" + node.getIp() + ":" + node.getPort(), node.getProjectName());
    }


    /**
     * 发现服务
     *
     * @param node
     * @return
     */
    @Override
    public List<ZKNode> discoverService(ZKNode node) {
        String parentPath = ZKUtils.parseNodeToPath(node);
        if (!zkClient.exists(parentPath)) {
            return null;
        }
        List<String> children = zkClient.getChildren(parentPath);
        List<ZKNode> nodes = new ArrayList<>();
        for (String str : children) {
            String projectName = zkClient.readData(parentPath + "/" + str);
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
