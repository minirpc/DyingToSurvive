package com.dyingtosurvive.rpccommunicationnetty.model;

import com.alibaba.fastjson.JSONObject;
import com.dyingtosurvive.rpccore.communication.RPCRequest;
import com.dyingtosurvive.rpccore.communication.RPCResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


public class RpcRequestDecoder extends ByteToMessageDecoder {



    @Override
    public final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("decode");
        int readableBytes = in.readableBytes();
        /*if (readableBytes < 4) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (dataLength <= 0) {
            ctx.close();
        }
        if (readableBytes < dataLength) {
            in.resetReaderIndex();
            return;
        }*/

        byte[] data = new byte[readableBytes];
        in.readBytes(data);
        String request = new String(data);
        RPCRequest response = JSONObject.parseObject(request,RPCRequest.class);
        out.add(response);
    }

}
