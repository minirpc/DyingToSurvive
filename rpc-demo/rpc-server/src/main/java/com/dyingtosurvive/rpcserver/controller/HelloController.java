package com.dyingtosurvive.rpcserver.controller;

import com.dyingtosurvive.rpccore.common.ApiResult;
import com.dyingtosurvive.rpccore.common.BaseController;
import com.dyingtosurvive.rpcdemointerface.model.User;
import com.dyingtosurvive.rpcdemointerface.service.IHelloService;
import com.dyingtosurvive.rpcdemointerface.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by change-solider on 2018-8-23.
 */
@RestController
public class HelloController {
    @Autowired
    private IHelloService helloService;
    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/hello")
    public String helloDyingToService(@RequestParam(value = "helloMessage") String helloMessage) {
        return helloService.helloDyingToService(helloMessage);
    }

    @RequestMapping(value = "/user")
    public User findUserById(@RequestParam(value = "userId") String userId) {
        return userService.findUserById(userId);
    }

    @RequestMapping(value = "/user/save",method = RequestMethod.POST)
    public User saveUser(@RequestBody User user) {
        System.out.println("-------------------------");
        return userService.saveUser(user);
    }
}
