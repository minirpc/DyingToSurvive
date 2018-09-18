package com.dyingtosurvive.rpcloadbalance.lb;

import com.dyingtosurvive.rpccore.common.ZKNode;
import com.dyingtosurvive.rpccore.lb.LoadBalance;

import java.util.List;
import java.util.Random;

/**
 * 随机负载均衡策略
 * Created by change-solider on 18-9-12.
 */
public class RandomLoadBalance implements LoadBalance {
    @Override
    public ZKNode select(List<ZKNode> zkNodes) {
        Random random = new Random();
        Integer index = random.nextInt(zkNodes.size());
        return zkNodes.get(index);
    }
}
