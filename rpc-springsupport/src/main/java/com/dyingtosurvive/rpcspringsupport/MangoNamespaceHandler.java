package com.dyingtosurvive.rpcspringsupport;


import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import java.util.Set;


public class MangoNamespaceHandler extends NamespaceHandlerSupport {
    //记录bean的id
    public final static Set<String> protocolDefineNames = null;

    @Override
    public void init() {
        System.out.println("hello world");
        //bean初始化完成后不需要做任何事
        //registerBeanDefinitionParser("registry", new MangoBeanDefinitionParser(RegistryConfig.class, true));
    }
}
