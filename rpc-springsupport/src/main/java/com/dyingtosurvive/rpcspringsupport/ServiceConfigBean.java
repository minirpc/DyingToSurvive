package com.dyingtosurvive.rpcspringsupport;


import com.dyingtosurvive.rpccore.register.RegistryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class ServiceConfigBean<T> implements BeanFactoryAware,
    InitializingBean,
    ApplicationListener<ContextRefreshedEvent>,
    DisposableBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private transient BeanFactory beanFactory;
    protected String interfaceName;
    protected String group;
    protected String version;
    protected Integer timeout;
    protected Integer retries;

    private Class<T> interfaceClass;
    private T ref;

    public T getRef() {
        return ref;
    }

    public void setRef(T ref) {
        this.ref = ref;
    }

    public Class<T> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    // 注册中心的配置列表
    protected List<RegistryConfig> registries;

    // 是否进行check，如果为true，则在监测失败后抛异常
    protected Boolean check = Boolean.TRUE;

    protected String id ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getRetries() {
        return retries;
    }

    public void setRetries(Integer retries) {
        this.retries = retries;
    }

    public List<RegistryConfig> getRegistries() {
        return registries;
    }

    public void setRegistries(List<RegistryConfig> registries) {
        this.registries = registries;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("onApplicationEvent called");
        //配置了几个service,此方法就会被调用几次
        //在此方法中调用父类的export方法　 此方法在afterPropertiesSet方法之后调用
       /* if (!isExported()) {
            //export导出，意为bean和registry,protocol都准备好了，需要对外暴露服务了
            //暴露服务需要有：注册中心，协议，nettyserver,目前都准备齐了
            export();
        }*/
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //配置了几个service,此方法就会被调用几次
        logger.debug("check service interface:%s config");
        //检查注册中心
        checkRegistryConfig();
    }

    @Override
    public void destroy() throws Exception {
        //super.destroy0();
    }

    private void checkRegistryConfig() {
        //判断注册中心是否已经有值了，没有值则从bean中取
        /*if (CollectionUtil.isEmpty(getRegistries())) {
            for (String name : MangoNamespaceHandler.registryDefineNames) {
                //beanfactory为得到的bean
                RegistryConfig rc = beanFactory.getBean(name, RegistryConfig.class);
                if (rc == null) {
                    continue;
                }
                if (MangoNamespaceHandler.registryDefineNames.size() == 1) {
                    //设置注册中心
                    setRegistry(rc);
                } else if (rc.isDefault() != null && rc.isDefault().booleanValue()) {
                    setRegistry(rc);
                }
            }
        }
        if (CollectionUtil.isEmpty(getRegistries())) {
            setRegistry(FrameworkUtils.getDefaultRegistryConfig());
        }*/
    }
}
