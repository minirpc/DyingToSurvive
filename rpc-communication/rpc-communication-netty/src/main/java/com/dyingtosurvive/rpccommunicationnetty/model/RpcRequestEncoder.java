package com.dyingtosurvive.rpccommunicationnetty.model;

import com.alibaba.fastjson.JSONObject;
import com.dyingtosurvive.rpccore.communication.RPCRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


public class RpcRequestEncoder extends MessageToByteEncoder {


    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        RPCRequest str = (RPCRequest)in;
        out.writeBytes(JSONObject.toJSONString(str).getBytes());
    }
}
