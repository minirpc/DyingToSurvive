package com.dyingtosurvive.rpcspringsupport;


import com.dyingtosurvive.rpccore.register.RegistryConfig;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import java.util.HashSet;
import java.util.Set;


public class RPCNamespaceHandler extends NamespaceHandlerSupport {
    //记录bean的id
    public final static Set<String> registryDefineNames = new HashSet<>();
    public final static Set<String> referenceConfigDefineNames = new HashSet<>();
    public final static Set<String> serviceConfigDefineNames = new HashSet<>();

    @Override
    public void init() {
        System.out.println("hello worlda RPCNamespaceHandler");
        System.out.println("aaaa");
        //client bean初始化完成后做一些事
        registerBeanDefinitionParser("reference", new RPCBeanDefinitionParser(ReferenceConfigBean.class, false));
        //server bean初始化完成后做一些事
        registerBeanDefinitionParser("service", new RPCBeanDefinitionParser(ServiceConfigBean.class, true));
        //bean初始化完成后不需要做任何事
        registerBeanDefinitionParser("registry", new RPCBeanDefinitionParser(RegistryConfig.class, true));
    }
}
