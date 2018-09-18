package com.dyingtosurvive.rpctracees.trace;

import com.dyingtosurvive.rpccore.common.TraceLog;
import com.dyingtosurvive.rpccore.trace.ServiceLogger;
import org.json.JSONObject;

/**
 * 日志追踪插件
 * Created by change-solider on 18-9-17.
 */
public class ESServiceLogger implements ServiceLogger {
    @Override
    public void writeLog(TraceLog traceLog) {
        System.out.println("tracelog:" + traceLog.toString());
    }

    @Override
    public void readLog() {

    }
}
