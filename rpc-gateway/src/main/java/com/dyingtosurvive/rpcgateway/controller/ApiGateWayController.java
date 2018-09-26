package com.dyingtosurvive.rpcgateway.controller;

import com.alibaba.fastjson.JSONObject;
import com.dyingtosurvive.rpcinterface.model.Router;
import com.dyingtosurvive.rpcinterface.service.IRouterService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * api网关控制器
 * <p>
 * <p>
 * Created by change-solider on 18-9-25.
 */
@RestController
public class ApiGateWayController {
    //路由规则，从配置中心加载
    @Autowired
    private IRouterService routerService;

    @RequestMapping(value = "/**")
    public Object dispatchRequestMethod(HttpServletRequest request, HttpServletResponse response) {
        //1.通过服务调用找到应用名称　
        Router router = routerService.getRoutersByUri(request.getRequestURI());
        //todo 2.根据应用名称和注册中心，找到服务提供者
        //todo 3.使用lb算法决定调用的服务提供者
        String address = "http://127.0.0.1:8080/" + router.getAppliation() + "/helloservice/hello?message=111";
        System.out.println("address:" + address);
        Object result = null;
        try {
            HttpResponse<String> httpResponse = Unirest.get(address).asString();
            result = JSONObject.parseObject(httpResponse.getBody(), Object.class);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return result;
    }
}
