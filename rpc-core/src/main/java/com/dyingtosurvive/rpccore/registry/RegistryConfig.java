package com.dyingtosurvive.rpccore.registry;

/**
 * Created by change-solider on 18-9-6.
 */
public class RegistryConfig {
    private String id;
    private String protocol;
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
