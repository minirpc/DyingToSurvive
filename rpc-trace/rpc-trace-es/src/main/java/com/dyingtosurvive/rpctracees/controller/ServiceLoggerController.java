package com.dyingtosurvive.rpctracees.controller;

import com.dyingtosurvive.rpcinterface.model.TraceLog;
import com.dyingtosurvive.rpcinterface.service.IServiceLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务日志控制器
 * Created by change-solider on 18-9-28.
 */
@RestController
public class ServiceLoggerController {
    @Autowired
    private IServiceLogger serviceLogger;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void writeLog(@RequestBody TraceLog traceLog) {
        serviceLogger.writeLog(traceLog);
    }
}
