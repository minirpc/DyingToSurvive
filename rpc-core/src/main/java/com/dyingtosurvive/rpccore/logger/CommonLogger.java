package com.dyingtosurvive.rpccore.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 公共的logger打印工具类
 * 好处：集中logger的输出，可以做一些事情．
 * created by zhibing.chen on 2018-08-06
 */
public class CommonLogger {
    private Logger logger;
    public CommonLogger(Class<?> loggerClazz) {
        this.logger = LoggerFactory.getLogger(loggerClazz);
    }
    public CommonLogger(String loggerName) {
        this.logger = LoggerFactory.getLogger(loggerName);
    }

    public void error(String msg, Throwable e){
        this.logger.error( msg, e );
        handleErrorLogger(msg);
    }

    public void error(String msg) {
        this.logger.error(msg);
        handleErrorLogger(msg);
    }

    public void info(String msg){
        this.logger.info(msg);
    }


    private void handleErrorLogger(String msg) {
        //todo 此处可以集中处理errorlogger,如将msg发送邮件等　
    }
}
