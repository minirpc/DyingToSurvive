package com.dyingtosurvive.rpcinterface.service;

import com.dyingtosurvive.rpcinterface.model.GetAvailableServiceResponse;
import com.dyingtosurvive.rpcinterface.model.GetCanUseServiceRequest;
import com.dyingtosurvive.rpcinterface.model.GetServiceListResponse;
import com.dyingtosurvive.rpcinterface.model.ServiceWeight;
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
    @RequestMapping(value = "/service/detail") ServiceWeight getServiceById(@RequestParam(value = "id") String id);

    List<ServiceWeight> getAllServices();

    @RequestMapping(value = "/service/canuseservice", method = RequestMethod.POST)
    GetAvailableServiceResponse getCanUseService(@RequestBody GetCanUseServiceRequest request);

    //todo 回写权重信息，待完善　
    void writeWeightInfo(ZKNode choseNode);

    @RequestMapping(value = "/service/list", method = RequestMethod.GET) GetServiceListResponse selectForList(
        @RequestParam(value = "applicationName",required = false) String applicationName,
        @RequestParam(value = "serviceName",required = false) String serviceName,
        @RequestParam(value = "serviceInterface",required = false) String serviceInterface,
        @RequestParam(value = "status",required = false) String status,
        @RequestParam(value = "createdBy",required = false) String createdBy);
}
