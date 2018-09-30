package com.dyingtosurvive.rpccore.message;

import java.io.Serializable;

/**
 * Created by change-solider on 18-9-30.
 */
public class MessageAwareConfig implements Serializable{
    private String groupName;
    private String nameServerAddress;
    private String topic;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getNameServerAddress() {
        return nameServerAddress;
    }

    public void setNameServerAddress(String nameServerAddress) {
        this.nameServerAddress = nameServerAddress;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
