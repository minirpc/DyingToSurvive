package com.dyingtosurvive.rpcconfig.controller;

import com.alibaba.fastjson.JSONObject;
import com.dyingtosurvive.rpcconfig.ServerApplication;
import com.dyingtosurvive.rpccore.common.ApiResult;
import com.dyingtosurvive.rpcinterface.model.Router;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ActiveProfiles("test")
public class TestRouterController {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testHello() throws Exception {
        Object result = testRestTemplate.getForObject("/helloservice/hello?message=111", Object.class);
        System.out.println(JSONObject.toJSONString(result));
        System.in.read();
    }
}
