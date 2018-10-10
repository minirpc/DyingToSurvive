package com.dyingtosurvive.rpccore.utils;

import com.dyingtosurvive.rpccore.message.MessageChannelFactory;
import com.dyingtosurvive.rpccore.spi.RPCServiceLoader;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Created by change-solider on 18-9-30.
 */
public class SPIPoolUtils {
    private static volatile MessageChannelFactory messageChannelFactory;

    public SPIPoolUtils() {

    }

    public static MessageChannelFactory getMessageChannelFactory() {
        //todo 待使用factory工厂来管理spi插件
        if (messageChannelFactory != null) {
            return messageChannelFactory;
        }
        ServiceLoader<MessageChannelFactory> factories = RPCServiceLoader.load(MessageChannelFactory.class);
        Iterator<MessageChannelFactory> operationIterator = factories.iterator();
        if (!operationIterator.hasNext()) {
            throw new IllegalStateException("请提供RegistryFactory的实现!");
        }
        //todo MessageChannelFactory不应该每次都使用serviceloader加载，应该全局可用
        MessageChannelFactory factory = operationIterator.next();
        messageChannelFactory = factory;
        return factory;
    }

}
