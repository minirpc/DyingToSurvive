package com.dyingtosurvive.rpcserver.controller;

import com.dyingtosurvive.rpcinterface.IHelloService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by change-solider on 18-8-23.
 */
@RestController
public class HelloController implements IHelloService {
    @Override
    @RequestMapping(value = "/hello")
    public String helloDyingToService(@RequestParam(value = "helloMessage") String helloMessage) {
        System.out.println("helloMessage:" + helloMessage);
        return helloMessage;
    }
}
