package com.dyingtosurvive.rpccommunicationhttp.server;

import com.dyingtosurvive.rpccore.communication.RPCServer;
import com.dyingtosurvive.rpccore.communication.RPCHandler;
import com.dyingtosurvive.rpccore.communication.RPCRequest;
import com.dyingtosurvive.rpccore.communication.RPCResponse;
import com.dyingtosurvive.rpccore.communication.ServerInfo;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by change-solider on 18-10-13.
 */
public class HTTPRPCServer implements RPCServer, HttpHandler {
    private String ip;
    private Integer port;

    private List<RPCHandler> rpcHandlerList = new ArrayList<>();

    @Override
    public void buildServer(String ip, Integer port, RPCHandler rpcHandler) {
        this.ip = ip;
        this.port = port;
        this.rpcHandlerList.add(rpcHandler);
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8001), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.createContext("/", this);
        server.start();
    }

    public HTTPRPCServer addHandler(RPCHandler rpcHandler) {
        rpcHandlerList.add(rpcHandler);
        return this;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "hello world";
        exchange.sendResponseHeaders(200, 0);
        OutputStream os = exchange.getResponseBody();
        //todo 生成request与response,解决header,编码，解码问题
        RPCResponse rpcResponse = handleRPCRequest(null);
        os.write(response.getBytes());
        os.close();
    }

    @Override
    public RPCResponse handleRPCRequest(RPCRequest request) {
        if (rpcHandlerList == null || rpcHandlerList.size() == 0) {
            System.out.println("请提供RPCHandler的实现");
            throw new IllegalStateException("请提供RPCHandler的实现");
        }
        for (RPCHandler rpcHandler : rpcHandlerList) {
            return rpcHandler.handleReqeust(request);
        }
        return null;
    }

    @Override
    public ServerInfo getServerInfo() {
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setIp(ip);
        serverInfo.setPort(port);
        serverInfo.setMethod("http");
        return serverInfo;
    }
}

