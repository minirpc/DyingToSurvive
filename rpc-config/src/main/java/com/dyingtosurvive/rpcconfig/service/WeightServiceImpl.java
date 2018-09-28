package com.dyingtosurvive.rpcconfig.service;

import com.alibaba.fastjson.JSONObject;
import com.dyingtosurvive.rpcinterface.model.GetAvailableServiceResponse;
import com.dyingtosurvive.rpcinterface.model.GetCanUseServiceRequest;
import com.dyingtosurvive.rpcinterface.model.ServiceWeight;
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
    public ServiceWeight getServiceById(String id) {
        ServiceWeight serviceType = new ServiceWeight();
        serviceType.setId(null);
        serviceType.setServiceInterface("com.dyingtosurvive.rpcconfig.service.IWeightService");
        serviceType.setCreatedBy("zhibing.chen");
        serviceType.setServiceName("IWeightService");
        return serviceType;
    }

    @Override
    public List<ServiceWeight> getAllServices() {
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
