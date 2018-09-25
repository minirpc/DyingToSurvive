package com.dyingtosurvive.rpcconfig.controller;

import com.dyingtosurvive.rpcinterface.model.GetCanUseServiceRequest;
import com.dyingtosurvive.rpcinterface.model.ServiceType;
import com.dyingtosurvive.rpcinterface.model.ZKNode;
import com.dyingtosurvive.rpcinterface.service.IWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by change-solider on 2018-8-23.
 */
@RestController
public class WeightController {
    @Autowired
    private IWeightService weightService;

    @RequestMapping(value = "/service/detail")
    public ServiceType getServiceById(@RequestParam(value = "id") String id) {
        return weightService.getServiceById(id);
    }

    @RequestMapping(value = "/service/canuseservice", method = RequestMethod.POST)
    public List<ZKNode> getCanUseService(@RequestBody GetCanUseServiceRequest request) {
        return weightService.getCanUseService(request);
    }
}
