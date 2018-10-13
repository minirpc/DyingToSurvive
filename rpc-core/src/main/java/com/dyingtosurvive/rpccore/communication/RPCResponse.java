package com.dyingtosurvive.rpccore.communication;

import java.io.Serializable;

/**
 * Created by change-solider on 18-10-13.
 */
public class RPCResponse implements Serializable {
    private Integer code;
    private String message;
    private Object body;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
