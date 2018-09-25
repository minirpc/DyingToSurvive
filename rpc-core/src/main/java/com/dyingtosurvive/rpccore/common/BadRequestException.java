package com.dyingtosurvive.rpccore.common;

/**
 * Created by change-solider on 18-9-21.
 */
public class BadRequestException extends FailureResultException {
    private static final long serialVersionUID = -580037397968508653L;

    public BadRequestException(String erroMsg, String errorCode) {
        super(400, erroMsg, errorCode);
    }

    public BadRequestException(String erroMsg, String errorCode, boolean isShow) {
        super(400, erroMsg, errorCode, isShow);
    }
}
