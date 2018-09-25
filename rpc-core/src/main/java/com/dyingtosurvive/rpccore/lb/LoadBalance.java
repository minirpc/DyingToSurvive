package com.dyingtosurvive.rpccore.lb;

import com.dyingtosurvive.rpcinterface.model.ZKNode;

import java.util.List;


public interface LoadBalance {
    ZKNode select(List<ZKNode> zkNodes);
}
