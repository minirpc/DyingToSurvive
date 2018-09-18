package com.dyingtosurvive.rpccore.trace;

/**
 * Created by change-solider on 18-9-17.
 */
public interface ServiceLogger {
    void writeLog(Object request, Object response);
    void readLog();
}
