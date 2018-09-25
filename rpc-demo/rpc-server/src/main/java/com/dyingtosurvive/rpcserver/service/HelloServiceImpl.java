package com.dyingtosurvive.rpcserver.service;

import com.dyingtosurvive.rpcdemointerface.service.IHelloService;
import org.springframework.stereotype.Service;

/**
 * Created by change-solider on 18-9-6.
 */
@Service("helloService")
public class HelloServiceImpl implements IHelloService {
    @Override
    public String helloDyingToService(String helloMessage) {
        System.out.println("helloMessage:" + helloMessage);
        return helloMessage;
    }
}
