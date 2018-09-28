package com.dyingtosurvive.rpcconfig.controller;

import com.dyingtosurvive.rpcinterface.model.GetAvailableServiceResponse;
import com.dyingtosurvive.rpcinterface.model.GetCanUseServiceRequest;
import com.dyingtosurvive.rpcinterface.model.ServiceWeight;
import com.dyingtosurvive.rpcinterface.service.IWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 权重管理
 * Created by change-solider on 2018-8-23.
 */
@RestController
public class WeightController {
    @Autowired
    private IWeightService weightService;

    @RequestMapping(value = "/service/detail")
    public ServiceWeight getServiceById(@RequestParam(value = "id") String id) {
        return weightService.getServiceById(id);
    }

    @RequestMapping(value = "/service/canuseservice", method = RequestMethod.POST)
    public GetAvailableServiceResponse getCanUseService(@RequestBody GetCanUseServiceRequest request) {
        return weightService.getCanUseService(request);
    }
}
