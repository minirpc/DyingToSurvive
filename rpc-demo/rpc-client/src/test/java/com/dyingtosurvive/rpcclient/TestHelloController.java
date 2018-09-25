package com.dyingtosurvive.rpcclient;

import com.alibaba.fastjson.JSONObject;
import com.dyingtosurvive.rpcdemointerface.model.User;
import com.mashape.unirest.http.JsonNode;
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
public class TestHelloController {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testHello() throws Exception {
        String result = testRestTemplate.getForObject("/hello?helloMessage=123", String.class);
        System.out.println("result :" + result);
        Assert.assertEquals("123", result);
    }

    @Test
    public void testSave() throws Exception {
        User user = new User();
        user.setUserId("11111");
        user.setUserName("zhibing.li");
        ResponseEntity<User> userResponseEntity = testRestTemplate.postForEntity("/user/save", user, User.class);
        System.out.println("status:" + userResponseEntity.getStatusCode());
        User users = userResponseEntity.getBody();
        System.out.println(JSONObject.toJSONString(users));
    }
}
