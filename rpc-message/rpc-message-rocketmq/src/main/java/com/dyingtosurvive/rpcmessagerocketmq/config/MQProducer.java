package com.dyingtosurvive.rpcmessagerocketmq.config;

import com.alibaba.fastjson.JSONObject;
import com.dyingtosurvive.rpccore.message.MessageAwareConfig;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * Created by change-solider on 18-9-30.
 */
public class MQProducer {
    private DefaultMQProducer producer;
    private String topic;


    public MQProducer(MessageAwareConfig config) {
        producer = new DefaultMQProducer(config.getGroupName());
        producer.setNamesrvAddr(config.getNameServerAddress());
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        topic = config.getTopic();
    }


    public String writeMessage(String json) {
        Message msg = new Message(topic, json.getBytes());
        SendResult sendResult = null;
        try {
            sendResult = producer.send(msg);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(sendResult);
    }
}
