package com.dyingtosurvive.rpcinterface.model;

/**
 * Created by change-solider on 18-9-25.
 */
public class Router {
    private String appliation;
    private String uri;
    private String ip;
    private String port;

    public String getAppliation() {
        return appliation;
    }

    public void setAppliation(String appliation) {
        this.appliation = appliation;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

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
