package com.dyingtosurvive.rpccommunicationnetty.client;

import com.dyingtosurvive.rpccommunicationnetty.model.RpcRequestEncoder;
import com.dyingtosurvive.rpccommunicationnetty.model.RpcResponseDecoder;
import com.dyingtosurvive.rpccore.communication.RPCClient;
import com.dyingtosurvive.rpccore.communication.RPCRequest;
import com.dyingtosurvive.rpccore.communication.RPCResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by change-solider on 18-10-11.
 */
public class NettyRPCClient implements RPCClient {
    private String monitorServerHost;
    private Integer monitorServerPort;
    private List<NettyClientHandler> handlers = new ArrayList<>();

    @Override
    public void buildClientAndConnectServer(String ip, Integer port) {
        this.monitorServerHost = ip;
        this.monitorServerPort = port;
        connectServer();
    }

    @Override
    public RPCResponse sendRPCRequest(RPCRequest request) {
        for (NettyClientHandler handler : handlers) {
            return handler.sendRequest(request);
        }
        return null;
    }

    public void connectServer() {
        new Thread(new Runnable() {
            @Override public void run() {
                EventLoopGroup eventLoopGroup = new NioEventLoopGroup(4);
                Bootstrap b = new Bootstrap();
                //连接到server并且使用RpcClientInitializer处理连接后的请求
                b.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler((new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {
                            //初始化启用通道,增加各种过滤器
                            ChannelPipeline cp = channel.pipeline();
                            cp.addLast(new RpcRequestEncoder());
                            //cp.addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0));
                            cp.addLast(new RpcResponseDecoder());
                            cp.addLast(new NettyClientHandler());
                        }
                    }));
                System.out.println("开始建立client与service的连接:" + System.currentTimeMillis());
                ChannelFuture channelFuture = b.connect(monitorServerHost, monitorServerPort);
                channelFuture.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                        if (channelFuture.isSuccess()) {
                            System.out
                                .println("Successfully connect to remote server. remote peer = 127.0.0.1:" + 18080);
                            NettyClientHandler handler =
                                channelFuture.channel().pipeline().get(NettyClientHandler.class);
                            handlers.add(handler);
                        }
                    }
                });

            }
        }).start();
    }
}
