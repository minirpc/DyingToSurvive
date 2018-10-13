package com.dyingtosurvive.rpcmonitoragent.gather;


import com.dyingtosurvive.rpccommunicationhttp.client.HTTPRPCClient;
import com.dyingtosurvive.rpccore.communication.RPCClient;
import com.dyingtosurvive.rpccore.communication.RPCRequest;
import com.dyingtosurvive.rpccore.communication.RPCResponse;

/**
 * Created by change-solider on 18-10-11.
 */
public class RPCMonitorAgent {
    private static RPCMonitorAgent agent;
    private RPCClient irpcClient;

    private RPCMonitorAgent(String monitorServerHost, Integer monitorServerPort) {
        irpcClient = new HTTPRPCClient();
        irpcClient.buildClientAndConnectServer(monitorServerHost, monitorServerPort);

        //irpcClient = new NettyRPCClient(monitorServerHost, monitorServerPort);
        //irpcClient.buildClientAndConnectServer(monitorServerHost, monitorServerPort);

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
        RPCResponse response = irpcClient.sendRPCRequest(rpcRequest);
    }
}
