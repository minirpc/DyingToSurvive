package com.dyingtosurvive.rpccommunicationnetty.client;

import com.alibaba.fastjson.JSONObject;
import com.dyingtosurvive.rpccore.communication.RPCRequest;
import com.dyingtosurvive.rpccore.communication.RPCResponse;

import java.util.UUID;

/**
 * Created by change-solider on 18-10-11.
 */
public class TestNettyRPCClient {
    public static void main(String[] args) {
        NettyRPCClient nettyClient = new NettyRPCClient();
        nettyClient.buildClientAndConnectServer("127.0.0.1", 18080);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RPCRequest request = new RPCRequest();
        request.setBody(null);
        request.setUri("/getusers");
        request.setRequestId(UUID.randomUUID().toString());
        RPCResponse response = nettyClient.sendRPCRequest(request);
        System.out.println(JSONObject.toJSONString(response));
    }
}
