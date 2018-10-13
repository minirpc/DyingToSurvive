package com.dyingtosurvive.rpccommunicationnetty.server;

import com.dyingtosurvive.rpccommunicationnetty.model.RpcRequestDecoder;
import com.dyingtosurvive.rpccommunicationnetty.model.RpcResponseEncoder;
import com.dyingtosurvive.rpccore.communication.RPCServer;
import com.dyingtosurvive.rpccore.communication.RPCHandler;
import com.dyingtosurvive.rpccore.communication.RPCRequest;
import com.dyingtosurvive.rpccore.communication.RPCResponse;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by change-solider on 18-10-11.
 */
public class NettyRPCServer implements RPCServer {
    private String ip;
    private Integer port;



    private List<RPCHandler> rpcHandlerList = new ArrayList<>();

    public NettyRPCServer() {
        System.out.println("使用netty作为通信组件");
    }

    @Override
    public void buildServer(String ip, Integer port, RPCHandler rpcHandler) {
        this.ip = ip;
        this.port = port;
        rpcHandlerList.add(rpcHandler);
        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public RPCResponse handleRPCRequest(RPCRequest request) {
        if (rpcHandlerList == null || rpcHandlerList.size() == 0) {
            System.out.println("请提供RPCHandler的实现");
            throw new IllegalStateException("请提供RPCHandler的实现");
        }
        for (RPCHandler rpcHandler : rpcHandlerList) {
            return rpcHandler.handleReqeust(request);
        }
        return null;
    }

    public void start() throws Exception {
        final NettyServerHandler nettyServerHandler = new NettyServerHandler(this);
        EventLoopGroup bossGroup = null;
        EventLoopGroup workerGroup = null;
        if (bossGroup == null && workerGroup == null) {
            //指挥线程组
            bossGroup = new NioEventLoopGroup();
            //工作线程组
            workerGroup = new NioEventLoopGroup();
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel channel) throws Exception {
                        //初始化启用通道,增加各种过滤器
                        channel.pipeline()
                            //.addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0))
                            .addLast(new RpcRequestDecoder())
                            .addLast(new RpcResponseEncoder())
                            .addLast(nettyServerHandler);


                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);


            ChannelFuture future = bootstrap.bind(ip, port).sync();

            System.out.println("Server started on port " + port + " in host " + ip);
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture f) throws Exception {
                    System.out.println("Rpc Server bind port:{} success");
                }
            });
            //future.channel().closeFuture().sync();
        }
    }

}
