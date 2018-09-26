package com.dyingtosurvive.rpcserver.service;

import com.alibaba.fastjson.JSONObject;
import com.dyingtosurvive.rpcdemointerface.model.User;
import com.dyingtosurvive.rpcdemointerface.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by change-solider on 18-9-18.
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
    @Override
    public User findUserById(String userId) {
        User user = new User();
        user.setUserId(userId);
        user.setUserName("李四");
        return user;
    }


    @Override
    public User saveUser(User user) {
        System.out.println("saveUser:" + JSONObject.toJSONString(user));
        return user;
    }
}
