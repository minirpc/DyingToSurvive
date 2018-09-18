package com.dyingtosurvive.rpcregistryzk;

import com.dyingtosurvive.rpccore.common.ZKNode;

/**
 * Created by change-solider on 2018-9-8.
 */
public class ZKUtils {
    private final static String baseUrl = "/dyingtosurvive/rpc";

    public static String parseNodeToPath(ZKNode node) {
        return baseUrl + "/" + node.getPackageName() + "/providers";
    }
}
