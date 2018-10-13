package com.dyingtosurvive.rpccommunicationhttp.server;

import com.dyingtosurvive.rpccore.communication.RPCHandler;
import com.dyingtosurvive.rpccore.communication.RPCRequest;
import com.dyingtosurvive.rpccore.communication.RPCResponse;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by change-solider on 18-10-13.
 */
public class TestHTTPRPCServer {


    @Test
    public void testServer() {
        HTTPRPCServer server = new HTTPRPCServer();
        server.buildServer(new RPCHandler() {
            @Override public RPCResponse handleReqeust(RPCRequest request) {
                System.out.println("handlerequest");
                return null;
            }
        });
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
