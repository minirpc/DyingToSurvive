package com.dyingtosurvive.rpccore.communication;

import java.io.Serializable;

/**
 * Created by change-solider on 18-10-15.
 */
public class ServerInfo implements Serializable{
    private String ip;
    private Integer port;
    private String Method;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getMethod() {
        return Method;
    }

    public void setMethod(String method) {
        Method = method;
    }
}
