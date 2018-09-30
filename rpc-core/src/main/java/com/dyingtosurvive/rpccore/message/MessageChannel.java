package com.dyingtosurvive.rpccore.message;

/**
 * Created by change-solider on 18-9-30.
 */
public interface MessageChannel {
    void readMessage(MessageListener messageListener);
    String writeMessage(String json);
}
