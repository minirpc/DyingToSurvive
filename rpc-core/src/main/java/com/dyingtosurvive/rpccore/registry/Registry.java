package com.dyingtosurvive.rpccore.registry;


import com.dyingtosurvive.rpccore.common.URL;
import com.dyingtosurvive.rpccore.common.ZKNode;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public interface Registry {

    void register(ZKNode url);
    void unregister(ZKNode url);
    List<ZKNode> discoverService(ZKNode url);
}
