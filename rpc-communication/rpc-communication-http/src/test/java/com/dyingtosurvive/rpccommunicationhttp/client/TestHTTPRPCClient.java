package com.dyingtosurvive.rpccommunicationhttp.client;

import com.dyingtosurvive.rpccore.communication.RPCResponse;
import org.junit.Test;

/**
 * Created by change-solider on 18-10-13.
 */
public class TestHTTPRPCClient {
    @Test
    public void testSendReqeust() {
        HTTPRPCClient httprpcClient = new HTTPRPCClient();
        httprpcClient.buildClientAndConnectServer("127.0.0.1",8001);
        RPCResponse response = httprpcClient.sendRPCRequest(null);
    }
}
