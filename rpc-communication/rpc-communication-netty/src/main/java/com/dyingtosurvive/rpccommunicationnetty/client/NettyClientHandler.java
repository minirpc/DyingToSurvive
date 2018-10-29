package com.dyingtosurvive.rpccommunicationnetty.client;

import com.alibaba.fastjson.JSONObject;
import com.dyingtosurvive.rpccommunicationnetty.model.RPCFuture;
import com.dyingtosurvive.rpccore.communication.RPCRequest;
import com.dyingtosurvive.rpccore.communication.RPCResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by change-solider on 18-10-11.
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<RPCResponse> {

    //存放正在请求的rpc接口
    private ConcurrentHashMap<String, RPCFuture> pendingRPC = new ConcurrentHashMap<>();

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
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RPCResponse response) throws Exception {
        System.out.println("receive server response:" + JSONObject.toJSONString(response));
        pendingRPC.get(response.getRequestId()).setRPCResponse(response);
    }


    public RPCResponse sendRequest(RPCRequest request) {
        //使用countdownlatch确保只有请求发出后才放入到map中
        CountDownLatch countDownLatch = new CountDownLatch(1);
        channel.writeAndFlush(request).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                //写入请求的完成时间，不是请求得到结果完成后的回调，
                //请求得到完成后的回调是在channelRead0方法中
                System.out.println("nettyclient send request complete");
                //System.out.println("write and flush operation complete" + System.currentTimeMillis());
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RPCFuture rpcFuture = new RPCFuture();
        pendingRPC.put(request.getRequestId(), rpcFuture);
        return pendingRPC.get(request.getRequestId()).get();
    }
}
