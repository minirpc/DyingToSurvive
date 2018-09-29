package com.dyingtosurvive.rpcconfig.dao;

import com.dyingtosurvive.rpcinterface.model.ServiceWeight;

import java.util.List;

/**
 * Created by change-solider on 18-9-26.
 */
public interface IWeightDAO {
    Integer saveServiceWeight(ServiceWeight serviceType);

    ServiceWeight selectByPrimaryKey(Integer id);

    Integer updateServiceWeightByPrimaryKey(ServiceWeight serviceWeight);

    List<ServiceWeight> selectForList(String applicationName, String serviceName, String serviceInterface, String status, String createdBy);
}
