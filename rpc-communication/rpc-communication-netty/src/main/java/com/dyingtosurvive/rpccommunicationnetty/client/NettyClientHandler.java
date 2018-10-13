package com.dyingtosurvive.rpccommunicationnetty.client;

import com.dyingtosurvive.rpccore.communication.RPCRequest;
import com.dyingtosurvive.rpccore.communication.RPCResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by change-solider on 18-10-11.
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<RPCResponse> {
    private volatile Channel channel;

    public NettyClientHandler() {
        System.out.println("NettyClientHandler init");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
    }



    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RPCResponse o) throws Exception {
        System.out.println("receive server response:" + o);
    }


    public RPCResponse sendRequest(RPCRequest request) {
        channel.writeAndFlush(request).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                //写入请求的完成时间，不是请求得到结果完成后的回调，
                //请求得到完成后的回调是在channelRead0方法中
                System.out.println("nettyclient send request complete");
                //System.out.println("write and flush operation complete" + System.currentTimeMillis());
            }
        });
        return null;
    }
}
