package com.dyingtosurvive.rpcconfig.dao.impl;

import com.dyingtosurvive.rpcconfig.dao.BaseDAO;
import com.dyingtosurvive.rpcconfig.dao.IWeightDAO;
import com.dyingtosurvive.rpcinterface.model.ServiceWeight;
import org.springframework.stereotype.Repository;

/**
 * Created by change-solider on 18-9-26.
 */
@Repository
public class WeightDAOImpl extends BaseDAO<ServiceWeight> implements IWeightDAO {
    @Override
    public Integer saveServiceWeight(ServiceWeight serviceType) {
        return save(serviceType);
    }

    @Override
    public ServiceWeight selectByPrimaryKey(Integer id) {
        return getByPrimaryKey(id);
    }
}
