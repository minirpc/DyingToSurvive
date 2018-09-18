package com.dyingtosurvive.rpcserver.service;

import com.dyingtosurvive.rpcinterface.IHelloService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
