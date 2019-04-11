package com.dyingtosurvive.rpcconfig.service;

import com.dyingtosurvive.rpcinterface.model.Router;
import com.dyingtosurvive.rpcinterface.service.IRouterService;
import org.springframework.stereotype.Service;

/**
 *
 * 路由服务
 * Created by change-solider on 18-9-25.
 */
@Service("routerService")
public class RouterServiceImpl implements IRouterService {
    @Override
    public Router getRoutersByUri(String uri) {
        Router router = new Router();
        //router.setAppliation("rpcclient");
        uri = uri.substring(1);
        String gateWay = uri.substring(0,uri.indexOf("/"));
        uri = uri.substring(gateWay.length()+1);
        String appName = uri.substring(0,uri.indexOf("/") );
        uri = uri.substring(appName.length()+1);
        router.setAppliation(appName);
        router.setUri(uri);
        router.setIp("127.0.0.1");
        router.setPort("8080");
        return router;
    }
}
