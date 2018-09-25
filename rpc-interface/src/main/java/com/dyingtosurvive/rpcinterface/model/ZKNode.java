package com.dyingtosurvive.rpcinterface.model;

import java.io.Serializable;

/**
 *
 * Created by change-solider on 2018-9-8.
 */
public class ZKNode implements Serializable {
    private String packageName;
    private String role;
    private String ip;
    private String port;
    private String projectName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
