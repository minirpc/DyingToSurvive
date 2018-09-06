package com.dyingtosurvive.rpcspringsupport;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class ReferenceConfigBean<T> implements
    FactoryBean<T>, BeanFactoryAware,
    InitializingBean, DisposableBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private transient BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public T getObject() throws Exception {
        return null;
        // return get();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
        //return getInterfaceClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
        /*logger.debug("check reference interface:%s config", getInterfaceName());
        //检查依赖的配置
        checkApplication();
        checkProtocolConfig();
        checkRegistryConfig();

        if(StringUtils.isEmpty(getGroup())) {
            setGroup(URLParam.group.getValue());
        }
        if(StringUtils.isEmpty(getVersion())) {
            setVersion(URLParam.version.getValue());
        }

        if(getTimeout()==null) {
            setTimeout(URLParam.requestTimeout.getIntValue());
        }
        if(getRetries()==null) {
            setRetries(URLParam.retries.getIntValue());
        }*/
    }

    @Override
    public void destroy() throws Exception {
        //super.destroy0();
    }

    private void checkRegistryConfig() {
        /*if (CollectionUtil.isEmpty(getRegistries())) {
            for (String name : MangoNamespaceHandler.registryDefineNames) {
                RegistryConfig rc = beanFactory.getBean(name, RegistryConfig.class);
                if (rc == null) {
                    continue;
                }
                if (MangoNamespaceHandler.registryDefineNames.size() == 1) {
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
