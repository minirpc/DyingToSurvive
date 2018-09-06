package com.dyingtosurvive.rpcclient.controller;

import com.dyingtosurvive.rpccore.protocol.ServiceCreateHelper;
import com.dyingtosurvive.rpcinterface.IHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by change-solider on 18-8-23.
 */
@RestController
public class HelloController {
    @Autowired
    private ServiceCreateHelper serviceCreateHelper;

    @RequestMapping(value = "/hello")
    public String helloDyingToService(@RequestParam(value = "helloMessage") String helloMessage) {
        IHelloService helloService = serviceCreateHelper.buildService(IHelloService.class);
        return helloService.helloDyingToService(helloMessage);
    }
}
