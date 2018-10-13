package com.dyingtosurvive.rpccommunicationnetty.server;

import com.dyingtosurvive.rpccore.communication.RPCHandler;
import com.dyingtosurvive.rpccore.communication.RPCRequest;
import com.dyingtosurvive.rpccore.communication.RPCResponse;

import java.io.IOException;

/**
 * Created by change-solider on 18-10-11.
 */
public class TestNettyRPCServer {
    public static void main(String[] args) {
        NettyRPCServer nettyRPCServer = new NettyRPCServer();
        nettyRPCServer.buildServer("127.0.0.1", 18080, new RPCHandler() {
            @Override public RPCResponse handleReqeust(RPCRequest request) {
                System.out.println("netty handle request");
                RPCResponse response = new RPCResponse();
                response.setCode(200);
                response.setMessage("请求成功");
                return response;
            }
        });
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
