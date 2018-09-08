package com.dyingtosurvive.rpccore.common;

import java.io.Serializable;

/**
 * dyingtosurvive/rpc/包名/providers ,值为ip:port/项目名
 * Created by change-solider on 18-9-8.
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


    @Override public String toString() {
        return "ZKNode{" +
            "packageName='" + packageName + '\'' +
            ", role='" + role + '\'' +
            ", ip='" + ip + '\'' +
            ", port='" + port + '\'' +
            ", projectName='" + projectName + '\'' +
            '}';
    }
}
