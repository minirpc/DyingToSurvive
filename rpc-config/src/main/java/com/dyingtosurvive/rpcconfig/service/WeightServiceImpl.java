package com.dyingtosurvive.rpcconfig.service;

import com.alibaba.fastjson.JSONObject;
import com.dyingtosurvive.rpcinterface.model.GetAvailableServiceResponse;
import com.dyingtosurvive.rpcinterface.model.GetCanUseServiceRequest;
import com.dyingtosurvive.rpcinterface.model.ServiceType;
import com.dyingtosurvive.rpcinterface.model.ZKNode;
import com.dyingtosurvive.rpcinterface.service.IWeightService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by change-solider on 18-9-6.
 */
@Service("weightService")
public class WeightServiceImpl implements IWeightService {
    @Override
    public ServiceType getServiceById(String id) {
        ServiceType serviceType = new ServiceType();
        serviceType.setId(id);
        serviceType.setPackageName("com.dyingtosurvive.rpcconfig.service.IWeightService");
        serviceType.setCreatedBy("zhibing.chen");
        serviceType.setServiceName("IWeightService");
        return serviceType;
    }

    @Override
    public List<ServiceType> getAllServices() {
        return null;
    }

    @Override
    public GetAvailableServiceResponse getCanUseService(GetCanUseServiceRequest request) {
        GetAvailableServiceResponse response = new GetAvailableServiceResponse();
        response.setAvailableServices(request.getServices());
        return response;
    }

    @Override
    public void writeWeightInfo(ZKNode choseNode) {
        System.out.println("writeWeightInfo:" + JSONObject.toJSONString(choseNode));
    }
}
