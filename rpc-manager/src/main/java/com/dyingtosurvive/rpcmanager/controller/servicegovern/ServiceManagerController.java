package com.dyingtosurvive.rpcmanager.controller.servicegovern;

import com.dyingtosurvive.rpcinterface.model.GetAvailableServiceResponse;
import com.dyingtosurvive.rpcinterface.model.GetCanUseServiceRequest;
import com.dyingtosurvive.rpcinterface.model.GetServiceListResponse;
import com.dyingtosurvive.rpcinterface.service.IWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务管理控制器
 * Created by change-solider on 18-9-28.
 */
@RestController
@RequestMapping("/servicemanager")
public class ServiceManagerController {
    @Autowired
    private IWeightService weightService;

    /**
     * 得到服务列表
     *
     * @param applicationName
     * @param serviceName
     * @param serviceInterface
     * @param status
     * @param createdBy
     * @return
     */
    @RequestMapping(value = "/service/list", method = RequestMethod.GET)
    public GetServiceListResponse selectForList(
        @RequestParam(value = "applicationName",required = false) String applicationName,
        @RequestParam(value = "serviceName",required = false) String serviceName,
        @RequestParam(value = "serviceInterface",required = false) String serviceInterface,
        @RequestParam(value = "status",required = false) String status, @RequestParam(value = "createdBy",required = false) String createdBy) {
        return weightService.selectForList(applicationName, serviceName, serviceInterface, status, createdBy);
    }
}
