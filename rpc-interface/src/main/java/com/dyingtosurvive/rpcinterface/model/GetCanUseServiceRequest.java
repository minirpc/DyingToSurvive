package com.dyingtosurvive.rpcinterface.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by change-solider on 18-9-21.
 */
public class GetCanUseServiceRequest implements Serializable{
    private List<ZKNode> services;
    private String serviceName;

    public List<ZKNode> getServices() {
        return services;
    }

    public void setServices(List<ZKNode> services) {
        this.services = services;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
