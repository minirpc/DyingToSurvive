package com.dyingtosurvive.rpccore.registry;


import com.dyingtosurvive.rpcinterface.model.ZKNode;

import java.util.List;

/**
 * 注册中心
 * Created by change-solider on 18-9-18.
 */
public interface Registry {

    //注册服务
    void registerService(ZKNode zkNode);


    //发现服务　
    List<ZKNode> discoverService(ZKNode zkNode);
}
