package com.dyingtosurvive.rpcmonitorserver.config;

import com.dyingtosurvive.rpccommunicationhttp.server.HTTPRPCServer;
import com.dyingtosurvive.rpccore.communication.RPCServer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * Created by change-solider on 18-10-12.
 */
public class AppConfig implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("11111111111111");
        //RPCServer irpcServer = new NettyRPCServer();
        RPCServer irpcServer = new HTTPRPCServer();
        irpcServer.buildServer(new MyRPCHandler());
    }
}
