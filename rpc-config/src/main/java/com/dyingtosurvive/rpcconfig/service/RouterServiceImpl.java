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
        router.setAppliation("rpcclient");
        router.setUri(uri);
        return router;
    }
}
