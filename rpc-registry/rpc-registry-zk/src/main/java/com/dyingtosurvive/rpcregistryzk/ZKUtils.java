package com.dyingtosurvive.rpcregistryzk;

import com.dyingtosurvive.rpccore.common.ZKNode;

/**
 * dyingtosurvive/rpc/包名/providers ,值为ip:port/项目名
 * Created by change-solider on 18-9-8.
 */
public class ZKUtils {
    private final static String baseUrl = "/dyingtosurvive/rpc";
    public static String parseNodeToPath(ZKNode node) {
        return baseUrl + "/" + node.getPackageName() + "/providers" ;
    }
}
