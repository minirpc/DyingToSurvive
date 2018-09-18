package com.dyingtosurvive.rpcspringsupport;


import com.dyingtosurvive.rpccore.common.ApplicationConfig;
import com.dyingtosurvive.rpccore.registry.RegistryConfig;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import java.util.HashSet;
import java.util.Set;


/**
 * 自定义命名空间解析器
 * <p>
 * created by changesolider on 2018-08-22
 */
public class RPCNamespaceHandler extends NamespaceHandlerSupport {
    public final static Set<String> registryAppIds= new HashSet<>();
    public final static Set<String> registryDefineIds = new HashSet<>();
    public final static Set<String> referenceConfigDefineIds = new HashSet<>();
    public final static Set<String> serviceConfigDefineIds = new HashSet<>();

    @Override
    public void init() {
        registerBeanDefinitionParser("application", new RPCBeanDefinitionParser(ApplicationConfig.class));
        registerBeanDefinitionParser("reference", new RPCBeanDefinitionParser(ReferenceConfigBean.class));
        registerBeanDefinitionParser("service", new RPCBeanDefinitionParser(ServiceConfigBean.class));
        registerBeanDefinitionParser("registry", new RPCBeanDefinitionParser(RegistryConfig.class));
    }
}
