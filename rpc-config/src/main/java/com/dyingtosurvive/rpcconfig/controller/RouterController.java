package com.dyingtosurvive.rpcconfig.controller;

import com.dyingtosurvive.rpcinterface.model.Router;
import com.dyingtosurvive.rpcinterface.service.IRouterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 路由控制器
 * Created by change-solider on 18-9-25.
 */
@RestController
public class RouterController {
    @Autowired
    private IRouterService routerService;

    @RequestMapping(value = "/router")
    public Router getRouterByUri(@RequestParam(value = "uri") String uri) {
        return routerService.getRoutersByUri(uri);
    }
}
