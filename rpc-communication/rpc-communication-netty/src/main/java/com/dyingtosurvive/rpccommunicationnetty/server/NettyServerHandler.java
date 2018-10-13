package com.dyingtosurvive.rpccommunicationnetty.server;

import com.dyingtosurvive.rpccore.communication.RPCRequest;
import com.dyingtosurvive.rpccore.communication.RPCResponse;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by change-solider on 18-10-11.
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<RPCRequest> {
    private NettyRPCServer nettyRPCServer;

    public NettyServerHandler(NettyRPCServer nettyRPCServer) {
        this.nettyRPCServer = nettyRPCServer;
        System.out.println("NettyServerHandler init method is called!");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RPCRequest o) throws Exception {
        System.out.println("receive client request:" + o);
        //todo 生成request与response,解决header,编码，解码问题
        RPCResponse response = nettyRPCServer.handleRPCRequest(o);
        channelHandlerContext.writeAndFlush(response).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                //写入请求的完成时间，不是请求得到结果完成后的回调，
                //请求得到完成后的回调是在channelRead0方法中
                System.out.println("write and flush operation complete" + System.currentTimeMillis());
            }
        });
    }
}
