package com.dyingtosurvive.rpccore.communication;

import java.io.Serializable;

/**
 * Created by change-solider on 18-10-13.
 */
public class RPCRequest implements Serializable {
    private String requestId;

    private String uri;

    private Object body;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
