package com.dyingtosurvive.rpcmonitorserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@Configuration
@ImportResource(locations = {"classpath:rpc-monitor-server.xml"})
@ComponentScan(basePackages = "com.dyingtosurvive.rpctracees")
@EnableAutoConfiguration
public class ServerApplication extends SpringBootServletInitializer {
    static Logger log = LoggerFactory.getLogger(ServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringApplication.class, args);
    }
}
