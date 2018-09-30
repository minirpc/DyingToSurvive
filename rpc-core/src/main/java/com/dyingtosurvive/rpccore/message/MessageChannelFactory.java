package com.dyingtosurvive.rpccore.message;

/**
 * Created by change-solider on 18-9-30.
 */
public interface MessageChannelFactory {
    MessageChannel buildMessageChannel(MessageAwareConfig config);
}
