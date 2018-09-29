package com.dyingtosurvive.rpcconfig.dao.impl;

import com.dyingtosurvive.rpcconfig.dao.BaseDAO;
import com.dyingtosurvive.rpcconfig.dao.IWeightDAO;
import com.dyingtosurvive.rpccore.db.DBUtils;
import com.dyingtosurvive.rpccore.utils.ClassUtils;
import com.dyingtosurvive.rpcinterface.model.ServiceWeight;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Integer updateServiceWeightByPrimaryKey(ServiceWeight serviceWeight) {
        return updateByPrimaryKey(serviceWeight);
    }

    @Override
    public List<ServiceWeight> selectForList(String applicationName, String serviceName, String serviceInterface,
        String status, String createdBy) {
        DBUtils<ServiceWeight> dbUtils = DBUtils.buildDBUtils(ClassUtils.getClassObject(this.getClass()));
        Map<String, String> params = new HashMap<>();
        if (!StringUtils.isEmpty(applicationName)) {
            params.put("applicationName", dbUtils.must(applicationName));
        }
        if (!StringUtils.isEmpty(serviceInterface)) {
            params.put("serviceName", dbUtils.must(serviceName));
        }
        if (!StringUtils.isEmpty(serviceInterface)) {
            params.put("serviceInterface", dbUtils.must(serviceInterface));
        }
        if (!StringUtils.isEmpty(status)) {
            params.put("status", dbUtils.must(status));
        }
        if (!StringUtils.isEmpty(createdBy)) {
            params.put("createdBy", dbUtils.must(createdBy));
        }
        return selectForList(params);
    }
}
