package com.dyingtosurvive.rpcclient.controller;

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
    private IHelloService helloService;

    @RequestMapping(value = "/hello")
    public String helloDyingToService(@RequestParam(value = "helloMessage") String helloMessage) {
        return helloService.helloDyingToService(helloMessage);
    }
}
