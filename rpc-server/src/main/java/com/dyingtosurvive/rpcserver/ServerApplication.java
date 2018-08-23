package com.dyingtosurvive.rpcserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ServerApplication extends SpringBootServletInitializer {
    static Logger log = LoggerFactory.getLogger(ServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringApplication.class, args);
    }
}
