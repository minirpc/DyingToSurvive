package com.dyingtosurvive.rpcconfig.controller;

import com.alibaba.fastjson.JSONObject;
import com.dyingtosurvive.rpcconfig.ServerApplication;
import com.dyingtosurvive.rpccore.common.ApiResult;
import com.dyingtosurvive.rpcinterface.model.ServiceType;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
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
public class TestServiceController {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testHello() throws Exception {
        ApiResult result = testRestTemplate.getForObject("/service/detail?id=123", ApiResult.class);
        System.out.println(JSONObject.toJSONString(result.getBody()));
        System.in.read();
    }



}

