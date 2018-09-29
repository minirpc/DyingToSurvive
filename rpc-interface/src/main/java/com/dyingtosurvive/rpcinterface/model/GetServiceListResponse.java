package com.dyingtosurvive.rpcinterface.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by change-solider on 18-9-28.
 */
public class GetServiceListResponse implements Serializable{
    private List<ServiceWeight> services;

    private Integer offset;
    private Integer limit;
    private Integer count;

    public List<ServiceWeight> getServices() {
        return services;
    }

    public void setServices(List<ServiceWeight> services) {
        this.services = services;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
