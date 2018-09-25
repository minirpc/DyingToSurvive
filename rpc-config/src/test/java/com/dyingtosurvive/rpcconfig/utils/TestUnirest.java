package com.dyingtosurvive.rpcconfig.utils;

import com.alibaba.fastjson.JSONObject;
import com.dyingtosurvive.rpccore.common.ApiResult;
import com.dyingtosurvive.rpcinterface.model.ServiceType;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by change-solider on 18-9-21.
 */
public class TestUnirest {
    @Test
    public void testApi() throws Exception {
        HttpResponse<String> response = Unirest.get("http://127.0.0.1:8080/rpcconfig//service/detail?id=1234").asString();
        System.out.println(response.getStatus());
        System.out.println("response.getBody():" + response.getBody());
        ApiResult apiResult = JSONObject.parseObject(response.getBody(),ApiResult.class);
        System.out.println(JSONObject.toJSONString(apiResult.getBody()));
    }

    @Test
    public void testParseList() throws Exception {
        ServiceType serviceType = new ServiceType();
        serviceType.setPackageName("com.dyingtosurvive.rpcconfig.service.IWeightService");
        serviceType.setCreatedBy("zhibing.chen");
        serviceType.setServiceName("IWeightService");


        List<ServiceType>  aa = new ArrayList<>();
        aa.add(serviceType);
        String json = JSONObject.toJSONString(aa);

        Object object = JSONObject.parseObject(json,List.class);
        System.out.println(JSONObject.toJSONString(object));
        System.out.println(object instanceof  List);
    }
}
