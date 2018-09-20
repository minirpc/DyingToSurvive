package com.dyingtosurvive.rpcserver.service;

import com.dyingtosurvive.rpcinterface.model.User;
import com.dyingtosurvive.rpcinterface.service.IUserService;
import org.springframework.stereotype.Service;

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
}
