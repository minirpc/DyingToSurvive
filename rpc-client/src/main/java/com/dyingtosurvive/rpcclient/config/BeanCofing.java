package com.dyingtosurvive.rpcclient.config;

import com.dyingtosurvive.rpccore.ServiceCreateHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan("com.dyingtosurvive.rpccore.*")
public class BeanCofing {
    @Bean
    public ServiceCreateHelper serviceCreateHelper() {
        return new ServiceCreateHelper();
    }
}
