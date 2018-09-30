package com.dyingtosurvive.rpcmessagerocketmq.config;

import com.alibaba.fastjson.JSONObject;
import com.dyingtosurvive.rpccore.message.MessageAwareConfig;
import com.dyingtosurvive.rpccore.message.MessageListener;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by change-solider on 18-9-30.
 */
public class MQConsumer {
    private DefaultMQPushConsumer consumer;
    private ConcurrentSkipListSet<MessageListener> listeners = new ConcurrentSkipListSet<>();

    public MQConsumer(MessageAwareConfig config) {
        consumer = new DefaultMQPushConsumer("please_rename_unique_group_name_4");
        consumer.setNamesrvAddr("10.42.0.15:9876;10.42.0.16:9876;10.42.0.17:9876");
        try {
            consumer.subscribe("TopicTest", "*");
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                ConsumeConcurrentlyContext context) {
                //todo 收到消息后，转发给多个消费者
                String message = JSONObject.toJSONString(msgs);
                for (MessageListener messageListener : listeners) {
                    messageListener.consumeMessage(message);
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
    }

    public void readMessage(MessageListener messageListener) {
        listeners.add(messageListener);
    }
}
