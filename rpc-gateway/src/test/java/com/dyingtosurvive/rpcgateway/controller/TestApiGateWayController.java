package com.dyingtosurvive.rpcgateway.controller;

import com.alibaba.fastjson.JSONObject;
import com.dyingtosurvive.rpcgateway.ServerApplication;
import com.dyingtosurvive.rpcinterface.model.Router;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by change-solider on 18-9-25.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ActiveProfiles("test")
public class TestApiGateWayController {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testHello() throws Exception {
        Router result = testRestTemplate.getForObject("/router?uri=123", Router.class);
        System.out.println(JSONObject.toJSONString(result));
        System.in.read();
    }
}
