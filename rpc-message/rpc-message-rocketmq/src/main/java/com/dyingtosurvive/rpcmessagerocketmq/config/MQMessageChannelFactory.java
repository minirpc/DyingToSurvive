package com.dyingtosurvive.rpcmessagerocketmq.config;

import com.dyingtosurvive.rpccore.message.MessageAwareConfig;
import com.dyingtosurvive.rpccore.message.MessageChannel;
import com.dyingtosurvive.rpccore.message.MessageChannelFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 使用rocketmq作为消息中间件，
 * <p>
 * 由channel来连接服务生产者和消费者
 * <p>
 * Created by change-solider on 18-9-30.
 */
public class MQMessageChannelFactory implements MessageChannelFactory {
    private ConcurrentHashMap<MessageAwareConfig, MessageChannel> channelMap = new ConcurrentHashMap<>();

    @Override
    public MessageChannel buildMessageChannel(MessageAwareConfig config) {
        if (channelMap.containsValue(config)) {
            return channelMap.get(config);
        } else {
            MessageChannel messageChannel = new MQMessageChannel(config);
            channelMap.putIfAbsent(config, messageChannel);
            return messageChannel;
        }
    }
}
