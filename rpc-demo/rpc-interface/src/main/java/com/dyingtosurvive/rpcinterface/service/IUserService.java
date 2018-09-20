package com.dyingtosurvive.rpcinterface.service;

import com.dyingtosurvive.rpcinterface.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by change-solider on 18-9-18.
 */
public interface IUserService {
    @RequestMapping(value = "/user")
    User findUserById(@RequestParam(value = "userId") String userId);
}
