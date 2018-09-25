package com.dyingtosurvive.rpccore.common;

import java.io.Serializable;

/**
 * Created by change-solider on 18-9-21.
 */
public class RPCHttpRequest implements Serializable {
    private String url;
    private String method;
    private Object body;
    private Object header;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public Object getHeader() {
        return header;
    }

    public void setHeader(Object header) {
        this.header = header;
    }
}
