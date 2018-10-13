package com.dyingtosurvive.rpcmonitorserver.config;

import com.dyingtosurvive.rpccore.communication.RPCServer;
import com.dyingtosurvive.rpccore.spi.RPCServiceLoader;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Created by change-solider on 18-10-12.
 */
@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        ServiceLoader<RPCServer> factories = RPCServiceLoader.load(RPCServer.class);
        Iterator<RPCServer> operationIterator = factories.iterator();
        if (!operationIterator.hasNext()) {
            throw new IllegalStateException("请提供RPCServer的实现!,监控中心需要服务才能收集客户端上报的指标");
        }
        RPCServer rpcServer = operationIterator.next();
        rpcServer.buildServer("127.0.0.1", 18080, new MonitorRPCHandler());
    }
}
