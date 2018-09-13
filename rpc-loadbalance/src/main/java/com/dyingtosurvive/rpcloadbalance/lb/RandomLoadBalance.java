package com.dyingtosurvive.rpcloadbalance.lb;

import com.dyingtosurvive.rpccore.common.ZKNode;
import com.dyingtosurvive.rpccore.lb.LoadBalance;

import java.util.List;

/**
 * Created by change-solider on 18-9-12.
 */
public class RandomLoadBalance implements LoadBalance {
    @Override
    public ZKNode select(List<ZKNode> zkNodes) {
        System.out.println("chorse node called!");
        return zkNodes.get(0);
    }
}
