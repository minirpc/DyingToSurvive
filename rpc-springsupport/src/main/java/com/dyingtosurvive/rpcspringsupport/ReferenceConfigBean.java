package com.dyingtosurvive.rpcspringsupport;


import com.dyingtosurvive.rpccore.proxy.ServiceCreateHelper;
import com.dyingtosurvive.rpccore.register.RegistryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * rpc:reference代表的bean
 * <p>
 * created by changesolider on 2018-09-18
 *
 * @param <T>
 */
public class ReferenceConfigBean<T> implements FactoryBean<T>, BeanFactoryAware, InitializingBean, DisposableBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private transient BeanFactory beanFactory;

    private String id;

    private Class<T> interfaceClass;

    //ＪＤＫ代理
    private transient volatile T proxy;

    private String interfaceName;

    // 注册中心的配置列表
    private List<RegistryConfig> registries;


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public T getObject() throws Exception {
        if (proxy == null) {
            initInterfaceClass();
            this.proxy = ServiceCreateHelper.buildService(interfaceClass, registries);
        }
        return proxy;
    }

    @Override
    public Class<?> getObjectType() {
        initInterfaceClass();
        return interfaceClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        checkRegistryConfig();
    }

    @Override
    public void destroy() throws Exception {
    }

    private void checkRegistryConfig() {
        if (CollectionUtils.isEmpty(registries)) {
            for (String beanId : RPCNamespaceHandler.registryDefineIds) {
                RegistryConfig registryConfig = beanFactory.getBean(beanId, RegistryConfig.class);
                //创建不变的list
                registries = Collections.singletonList(registryConfig);
            }
        }
    }

    private void initInterfaceClass() {
        if (interfaceClass == null) {
            if (StringUtils.isEmpty(interfaceName)) {
                return;
            }
            try {
                interfaceClass = (Class<T>) Class.forName(interfaceName, true, Thread.currentThread()
                    .getContextClassLoader());
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("reference class not found", e);
            }
        }
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
