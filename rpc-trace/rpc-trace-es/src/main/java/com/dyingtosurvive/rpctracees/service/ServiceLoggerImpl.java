package com.dyingtosurvive.rpctracees.service;

import com.dyingtosurvive.rpcinterface.model.TraceLog;
import com.dyingtosurvive.rpcinterface.service.IServiceLogger;
import org.springframework.stereotype.Service;

/**
 * 日志追踪插件
 * Created by change-solider on 18-9-17.
 */
@Service("serviceLogger")
public class ServiceLoggerImpl implements IServiceLogger {
    @Override
    public void writeLog(TraceLog traceLog) {
        System.out.println("tracelog:" + traceLog.toString());
    }

    @Override
    public void readLog() {

    }
}
