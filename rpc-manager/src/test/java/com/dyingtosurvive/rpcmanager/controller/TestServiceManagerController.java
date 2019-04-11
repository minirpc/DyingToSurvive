package com.dyingtosurvive.rpcmanager.controller;

import com.alibaba.fastjson.JSONObject;
import com.dyingtosurvive.rpcinterface.model.GetServiceListResponse;
import com.dyingtosurvive.rpcmanager.ServerApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * controller测试
 * Created by change-solider on 2018-8-6.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ActiveProfiles("test")
public class TestServiceManagerController {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testHello() throws Exception {
        GetServiceListResponse result = testRestTemplate.getForObject("/service/list", GetServiceListResponse.class);
        System.out.println("result :" + result.getCount());
    }
}
