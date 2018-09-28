package com.dyingtosurvive.rpcconfig.dao;

import com.dyingtosurvive.rpcinterface.model.ServiceWeight;

/**
 * Created by change-solider on 18-9-26.
 */
public interface IWeightDAO {
    Integer saveServiceWeight(ServiceWeight serviceType);

    ServiceWeight selectByPrimaryKey(Integer id);
}
