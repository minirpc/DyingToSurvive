package com.dyingtosurvive.rpcinterface.service;

import com.dyingtosurvive.rpcinterface.model.GetAvailableServiceResponse;
import com.dyingtosurvive.rpcinterface.model.GetCanUseServiceRequest;
import com.dyingtosurvive.rpcinterface.model.ServiceType;
import com.dyingtosurvive.rpcinterface.model.ZKNode;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by change-solider on 18-9-21.
 */
public interface IWeightService {
    @RequestMapping(value = "/service/detail")
    ServiceType getServiceById(@RequestParam(value = "id") String id);

    List<ServiceType> getAllServices();

    @RequestMapping(value = "/service/canuseservice",method = RequestMethod.POST)
    GetAvailableServiceResponse getCanUseService(@RequestBody GetCanUseServiceRequest request);

    void writeWeightInfo(ZKNode choseNode);
}
