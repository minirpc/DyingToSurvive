package com.dyingtosurvive.rpcmonitoragent.gather;


import com.dyingtosurvive.rpccommunicationhttp.client.HTTPRPCClient;
import com.dyingtosurvive.rpccore.communication.RPCClient;
import com.dyingtosurvive.rpccore.communication.RPCRequest;
import com.dyingtosurvive.rpccore.communication.RPCResponse;
import com.dyingtosurvive.rpccore.communication.RPCServer;
import com.dyingtosurvive.rpccore.spi.RPCServiceLoader;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Created by change-solider on 18-10-11.
 */
public class RPCMonitorAgent {
    private static RPCMonitorAgent agent;
    private RPCClient rpcClient;

    private RPCMonitorAgent(String monitorServerHost, Integer monitorServerPort) {
        ServiceLoader<RPCClient> factories = RPCServiceLoader.load(RPCClient.class);
        Iterator<RPCClient> operationIterator = factories.iterator();
        if (!operationIterator.hasNext()) {
            throw new IllegalStateException("请提供RPCServer的实现!,监控中心需要服务才能收集客户端上报的指标");
        }
        rpcClient = operationIterator.next();
        rpcClient.buildClientAndConnectServer(monitorServerHost, monitorServerPort);
    }

    public synchronized static RPCMonitorAgent build(String monitorServerHost, Integer monitorServerPort) {
        if (agent != null) {
            return agent;
        }
        agent = new RPCMonitorAgent(monitorServerHost, monitorServerPort);
        return agent;
    }


    public void gatherSystemInfo() {
        //todo 定时收集系统信息，并使用
        RPCRequest rpcRequest = new RPCRequest();
        RPCResponse response = rpcClient.sendRPCRequest(rpcRequest);
    }
}
