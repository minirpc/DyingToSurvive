package com.dyingtosurvive.rpcmessagerocketmq.config;

import com.alibaba.fastjson.JSONObject;
import com.dyingtosurvive.rpccore.message.MessageAwareConfig;
import com.dyingtosurvive.rpccore.message.MessageListener;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
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
        System.out.println("aaaa");
        consumer = new DefaultMQPushConsumer(config.getGroupName());
        consumer.setNamesrvAddr(config.getNameServerAddress());
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                ConsumeConcurrentlyContext context) {
                //todo 收到消息后，转发给多个消费者
                String message = new String(msgs.get(0).getBody());
                System.out.println("receive message:" + message);
                for (MessageListener messageListener : listeners) {
                    messageListener.consumeMessage(message);
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        System.out.println("dddddd");
        try {
            consumer.subscribe(config.getTopic(), "*");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        System.out.println("bbb");
        try {
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }

    }

    public void readMessage(MessageListener messageListener) {
        listeners.add(messageListener);
    }
}
