package com.dyingtosurvive.rpccore.lb;

import com.dyingtosurvive.rpccore.common.ZKNode;

import java.util.List;


public interface LoadBalance {
    ZKNode select(List<ZKNode> zkNodes);
}
