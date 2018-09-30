package com.dyingtosurvive.rpcmessagerocketmq.config;

import com.dyingtosurvive.rpccore.message.MessageAwareConfig;
import com.dyingtosurvive.rpccore.message.MessageChannel;
import com.dyingtosurvive.rpccore.message.MessageListener;

/**
 * Created by change-solider on 18-9-30.
 */
public class MQMessageChannel implements MessageChannel {
    private  MQProducer mqProducer;
    private  MQConsumer mqConsumer;

    public MQMessageChannel(MessageAwareConfig config) {
        mqProducer = new MQProducer(config);
        mqConsumer = new MQConsumer(config);
    }

    @Override
    public void readMessage(MessageListener messageListener) {
        mqConsumer.readMessage(messageListener);
    }

    @Override
    public String writeMessage(String json) {
        return mqProducer.writeMessage(json);
    }
}
