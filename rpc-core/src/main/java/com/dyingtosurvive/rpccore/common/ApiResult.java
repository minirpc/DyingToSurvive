package com.dyingtosurvive.rpccore.common;

/**
 * Created by change-solider on 18-9-21.
 */
public class ApiResult {
    private String code;
    private String message;
    private Object body;
    static final String FORBIDDEN_ERROR_STATUS = "403";
    static final String LOCKED_ERROR_STATUS = "423";
    static final String UNAUTHORIZED_ERROR_STATUS = "401";
    static final String ERROR_STATUS = "400";
    static final String SUCC_STATUS = "200";

    private ApiResult() {
    }

    private ApiResult(Object body) {
        this.body = body;
    }

    private ApiResult(String code, String message, Object body) {
        this.code = code;
        this.message = message;
        this.body = body;
    }

    protected static ApiResult sucessResult(String message, Object body) {
        return new ApiResult(SUCC_STATUS, message, body);
    }

    protected static ApiResult errorResult(String message, Object body) {
        return new ApiResult(ERROR_STATUS, message, body);
    }

    protected static ApiResult httpErrorResult(String message, String code) {
        throw new BadRequestException(message, code);
    }

    protected static ApiResult httpUnauthorizedErrorResult(String message, String code) {
        //throw new UnauthorizedException(message, code);
        throw new BadRequestException(message, code);
    }

    protected static ApiResult httpLockedErrorResult(String message, String code) {
        //throw new LockedException(message, code);
        throw new BadRequestException(message, code);
    }

    protected static ApiResult httpForbiddenErrorResult(String message, String code) {
        //throw new ForbiddenException(message, code);
        throw new BadRequestException(message, code);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
