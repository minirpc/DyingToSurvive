package com.dyingtosurvive.rpcinterface.service;


import com.dyingtosurvive.rpcinterface.model.TraceLog;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by change-solider on 18-9-17.
 */
public interface IServiceLogger {

    @RequestMapping(value = "", method = RequestMethod.POST)
    void writeLog(@RequestBody TraceLog traceLog);

    void readLog();
}
