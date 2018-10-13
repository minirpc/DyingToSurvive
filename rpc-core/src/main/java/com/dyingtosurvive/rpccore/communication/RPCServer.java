package com.dyingtosurvive.rpccore.communication;

/**
 * Created by change-solider on 18-10-13.
 */
public interface RPCServer {
    void buildServer(RPCHandler rpcHandler);

    RPCResponse handleRPCRequest( RPCRequest request);

}
