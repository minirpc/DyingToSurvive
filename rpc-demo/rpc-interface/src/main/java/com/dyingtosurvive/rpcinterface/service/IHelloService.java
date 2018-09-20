package com.dyingtosurvive.rpcinterface.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * Created by change-solider on 18-8-23.
 */
public interface IHelloService {
    @RequestMapping(value = "/hello")
    String helloDyingToService(@RequestParam(value = "helloMessage") String helloMessage);
}
