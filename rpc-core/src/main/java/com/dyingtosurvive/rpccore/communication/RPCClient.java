package com.dyingtosurvive.rpccore.communication;

/**
 * Created by change-solider on 18-10-13.
 */
public interface RPCClient {
    void buildClientAndConnectServer(String ip, Integer port);

    RPCResponse sendRPCRequest(RPCRequest request);
}
