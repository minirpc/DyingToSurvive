package com.dyingtosurvive.rpcinterface.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by change-solider on 18-9-26.
 */
public class GetAvailableServiceResponse implements Serializable {
    private List<ZKNode> availableServices;

    public List<ZKNode> getAvailableServices() {
        return availableServices;
    }

    public void setAvailableServices(List<ZKNode> availableServices) {
        this.availableServices = availableServices;
    }
}
