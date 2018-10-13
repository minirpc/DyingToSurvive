package com.dyingtosurvive.rpcmonitorserver.config;

import com.dyingtosurvive.rpccore.communication.RPCHandler;
import com.dyingtosurvive.rpccore.communication.RPCRequest;
import com.dyingtosurvive.rpccore.communication.RPCResponse;

/**
 * Created by change-solider on 18-10-13.
 */
public class MyRPCHandler implements RPCHandler {
    @Override
    public RPCResponse handleReqeust(RPCRequest request) {
        System.out.println("处理请求");
        return null;
    }
}
