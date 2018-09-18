package com.dyingtosurvive.rpccore.common;

import java.io.Serializable;

/**
 * Created by change-solider on 18-9-8.
 */
public class ZKInfo implements Serializable {
    private String ip;
    private String port;


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
