package com.dyingtosurvive.rpccore.ha;

import com.dyingtosurvive.rpccore.lb.LoadBalance;


public interface HaStrategy<T> {

    void call(LoadBalance loadBalance);
}
