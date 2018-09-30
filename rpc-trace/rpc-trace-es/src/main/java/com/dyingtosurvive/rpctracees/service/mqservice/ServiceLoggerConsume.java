package com.dyingtosurvive.rpctracees.service.mqservice;

import com.dyingtosurvive.rpccore.message.MessageAwareConfig;
import com.dyingtosurvive.rpccore.message.MessageChannel;
import com.dyingtosurvive.rpccore.message.MessageChannelFactory;
import com.dyingtosurvive.rpccore.message.MessageListener;
import com.dyingtosurvive.rpccore.spi.RPCServiceLoader;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Created by change-solider on 18-9-30.
 */
@Service("serviceLoggerConsume")
public class ServiceLoggerConsume {

    public ServiceLoggerConsume() {
        consume();
    }

    public void consume() {
        ServiceLoader<MessageChannelFactory> factories = RPCServiceLoader.load(MessageChannelFactory.class);
        Iterator<MessageChannelFactory> operationIterator = factories.iterator();
        if (!operationIterator.hasNext()) {
            throw new IllegalStateException("请提供RegistryFactory的实现!");
        }
        MessageChannelFactory factory = operationIterator.next();
        MessageAwareConfig config = new MessageAwareConfig();
        //todo config待从配置文件中取
        config.setGroupName("rpc-trace");
        config.setNameServerAddress("10.42.0.15:9876;10.42.0.16:9876;10.42.0.17:9876");
        config.setTopic("rpc-trace-message");
        MessageChannel messageChannel = factory.buildMessageChannel(config);
        messageChannel.readMessage(new MessageListener() {
            @Override
            public String consumeMessage(String message) {
                //todo message为收取到的消息
                System.out.println("已收到消息:" + message);
                return null;
            }
        });
    }
}
