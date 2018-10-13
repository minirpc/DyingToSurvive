package com.dyingtosurvive.rpcmonitorserver.config;

import com.alibaba.fastjson.JSONObject;
import com.dyingtosurvive.rpccore.communication.RPCHandler;
import com.dyingtosurvive.rpccore.communication.RPCRequest;
import com.dyingtosurvive.rpccore.communication.RPCResponse;

/**
 * Created by change-solider on 18-10-13.
 */
public class MonitorRPCHandler implements RPCHandler {
    @Override
    public RPCResponse handleReqeust(RPCRequest request) {
        System.out.println("处理请求:" + JSONObject.toJSONString(request));
        RPCResponse response = new RPCResponse();
        response.setCode(200);
        response.setMessage("处理成功");
        response.setRequestId(request.getRequestId());
        //todo 将请求的参数写入到数据库或es中.
        return response;
    }
}
