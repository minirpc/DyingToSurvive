package com.dyingtosurvive.rpccore.common;

/**
 * Created by change-solider on 18-9-21.
 */
public class FailureResultException extends RuntimeException {
    private static final long serialVersionUID = -4644075679931047367L;
    private final int statusCode;
    private final String errorMsg;
    private final String errorCode;
    private boolean isShow = false;

    public FailureResultException(int statusCode, String errorMsg, String errorCode, boolean isShow) {
        this.statusCode = statusCode;
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
        this.isShow = isShow;
    }

    public FailureResultException(int statusCode, String errorMsg, String errorCode) {
        this.statusCode = statusCode;
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }


    public boolean isShow() {
        return this.isShow;
    }

    public void setShow(boolean isShow) {
        this.isShow = isShow;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}
