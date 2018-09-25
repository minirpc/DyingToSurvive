package com.dyingtosurvive.rpcdemointerface.service;

import com.dyingtosurvive.rpcdemointerface.model.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by change-solider on 18-9-18.
 */
public interface IUserService {
    @RequestMapping(value = "/user")
    User findUserById(@RequestParam(value = "userId") String userId);

    @RequestMapping(value = "/user/save",method = RequestMethod.POST)
    User saveUser(@RequestBody User user);
}
