package com.dyingtosurvive.rpcinterface.model;

import java.io.Serializable;

import com.dyingtosurvive.rpcinterface.db.annotation.*;

/**
 * Created by change-solider on 18-9-21.
 */
@RPCTable(tableName = "service_weight")
public class ServiceWeight implements Serializable {
    @RPCField(fieldName = "id", fieldType = "Integer")
    private Integer id;
    @RPCField(fieldName = "service_name", fieldType = "String")
    private String serviceName;
    @RPCField(fieldName = "service_interface", fieldType = "String")
    private String serviceInterface;
    @RPCField(fieldName = "application_name", fieldType = "String")
    private String applicationName;
    @RPCField(fieldName = "created_by", fieldType = "String")
    private String createdBy;
    @RPCField(fieldName = "updated_by", fieldType = "String")
    private String updatedBy;
    @RPCField(fieldName = "weight", fieldType = "Integer")
    private Integer weight;
    @RPCField(fieldName = "status", fieldType = "String")
    private String status;
    @RPCField(fieldName = "is_valid", fieldType = "String")
    private String isValid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(String serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }
}
