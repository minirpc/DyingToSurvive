package com.dyingtosurvive.rpccommunicationhttp.client;

import com.dyingtosurvive.rpccore.communication.RPCClient;
import com.dyingtosurvive.rpccore.communication.RPCRequest;
import com.dyingtosurvive.rpccore.communication.RPCResponse;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by change-solider on 18-10-13.
 */
public class HTTPRPCClient implements RPCClient {
    private String ip;
    private Integer port;


    @Override
    public void buildClientAndConnectServer(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }


    @Override
    public RPCResponse sendRPCRequest(RPCRequest request) {
        String url = generateRequestUrl(request);
        HttpResponse<String> response = null;
        try {
            response = Unirest.get(url).asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        //RPCResponse rpcResponse = JSONObject.parseObject(response.getBody(), RPCResponse.class);
        RPCResponse rpcResponse = new RPCResponse();
        return rpcResponse;
    }

    private String generateRequestUrl(RPCRequest request) {
        String url = "http://";
        url = url.concat(ip).concat(":").concat("" + port).concat("/").concat(request.getUri());
        return url;
    }
}
