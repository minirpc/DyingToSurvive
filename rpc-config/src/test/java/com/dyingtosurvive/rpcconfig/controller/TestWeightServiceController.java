package com.dyingtosurvive.rpcconfig.controller;

import com.alibaba.fastjson.JSONObject;
import com.dyingtosurvive.rpcconfig.ServerApplication;
import com.dyingtosurvive.rpccore.common.ApiResult;
import com.dyingtosurvive.rpcinterface.model.GetCanUseServiceRequest;
import com.dyingtosurvive.rpcinterface.model.ZKNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * controller测试
 * Created by change-solider on 2018-8-6.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ActiveProfiles("test")
public class TestWeightServiceController {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testHello() throws Exception {
        ApiResult result = testRestTemplate.getForObject("/service/detail?id=123", ApiResult.class);
        System.out.println(JSONObject.toJSONString(result.getBody()));
        System.in.read();
    }


    @Test
    public void testGetCanUseService() throws Exception{
        ZKNode zkNode = new ZKNode();
        zkNode.setIp("aaaa");
        zkNode.setPackageName("awwww");
        zkNode.setProjectName("asfww");
        List<ZKNode> nodeList = new ArrayList<>();
        nodeList.add(zkNode);
        GetCanUseServiceRequest request = new GetCanUseServiceRequest();
        request.setServiceName("aaa");
        request.setServices(nodeList);
        ResponseEntity<List> responseEntity  = testRestTemplate.postForEntity("/service/canuseservice", request,List.class);
        System.out.println(JSONObject.toJSONString(responseEntity.getBody().get(0)));
        System.in.read();
    }
}

