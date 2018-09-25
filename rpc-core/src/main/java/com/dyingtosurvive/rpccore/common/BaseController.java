package com.dyingtosurvive.rpccore.common;

/**
 * Created by change-solider on 18-9-21.
 */
public class BaseController {
    protected ApiResult returnSuccess(Object body) {
        return ApiResult.sucessResult(null, body);
    }

    protected ApiResult returnSuccess(String message, Object body) {
        return ApiResult.sucessResult(message, body);
    }

    protected ApiResult returnError(String message) {
        return ApiResult.errorResult(message, null);
    }

    protected ApiResult returnErrorWithBody(String message, Object body) {
        return ApiResult.errorResult(message, body);
    }

    protected ApiResult returnHttpError(String message) {
        return ApiResult.httpErrorResult(message, ApiResult.ERROR_STATUS);
    }

    protected ApiResult returnHttpUnauthorizedError(String message) {
        return ApiResult.httpUnauthorizedErrorResult(message, ApiResult.UNAUTHORIZED_ERROR_STATUS);
    }

    protected ApiResult returnHttpLockedError(String message) {
        return ApiResult.httpLockedErrorResult(message, ApiResult.LOCKED_ERROR_STATUS);
    }

    protected ApiResult returnHttpForbiddenError(String message) {
        return ApiResult.httpForbiddenErrorResult(message, ApiResult.FORBIDDEN_ERROR_STATUS);
    }
}
