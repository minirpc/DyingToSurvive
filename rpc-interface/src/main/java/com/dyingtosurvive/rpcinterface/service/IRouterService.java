package com.dyingtosurvive.rpcinterface.service;

import com.dyingtosurvive.rpcinterface.model.Router;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by change-solider on 18-9-25.
 */
public interface IRouterService {
    @RequestMapping(value = "/router")
    Router getRoutersByUri(@RequestParam(value = "uri") String uri);
}
