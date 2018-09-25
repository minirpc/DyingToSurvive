package com.dyingtosurvive.rpcclient.utils;

import com.alibaba.fastjson.JSONObject;
import com.dyingtosurvive.rpcdemointerface.model.User;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Test;

/**
 * Created by change-solider on 18-9-21.
 */
public class TestUnirest {
    @Test
    public void testsss() {
        User user = new User();
        user.setUserId("1111");
        user.setUserName("zhibing.chen");
        try {
            HttpResponse<String> resutl = Unirest.post("http://127.0.0.1:8080/rpcserver/user/save").header("Content-Type","application/json").body(JSONObject.toJSONString(user)).asString();
            System.out.println(resutl.getStatus());
            User object = JSONObject.parseObject(resutl.getBody(), User.class);
            System.out.println(JSONObject.toJSONString(object));
        } catch (UnirestException e) {
            e.printStackTrace();
        }

    }
}
