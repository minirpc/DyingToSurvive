package com.dyingtosurvive.rpccore.trace;

import com.dyingtosurvive.rpccore.common.TraceLog;

/**
 * Created by change-solider on 18-9-17.
 */
public interface ServiceLogger {
    void writeLog(TraceLog traceLog);
    void readLog();
}
