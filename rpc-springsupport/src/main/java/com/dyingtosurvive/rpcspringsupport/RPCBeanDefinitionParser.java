package com.dyingtosurvive.rpcspringsupport;


import com.dyingtosurvive.rpccore.common.ApplicationConfig;
import com.dyingtosurvive.rpccore.register.RegistryConfig;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;


/**
 * 自定义bean解析器
 * created by changesolider on 2018-08-22
 */
public class RPCBeanDefinitionParser implements BeanDefinitionParser {
    //存储需要解析的bean
    private final Class<?> beanClass;

    public RPCBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        try {
            return parseRPCBean(element, parserContext);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private BeanDefinition parseRPCBean(Element element, ParserContext parserContext)
        throws ClassNotFoundException {
        //定义bean
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);

        //生成beanid
        String id = element.getAttribute("id");
        if (StringUtils.isEmpty(id)) {
            //rpc:server
            id = element.getAttribute("interface");
            if (StringUtils.isEmpty(id)) {
                //rpc:registry,rpc:application
                id = beanClass.getName();
            }
        }

        //判断容器中是否已经存在此bean了
        if (parserContext.getRegistry().containsBeanDefinition(id)) {
            throw new IllegalStateException("spring容器中已经存在id为" + id + "的bean!");
        }
        //注册
        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
        beanDefinition.getPropertyValues().addPropertyValue("id", id);
        //解析属性
        if (ApplicationConfig.class.equals(beanClass)) {
            RPCNamespaceHandler.registryAppIds.add(id);
            parseCommonProperty("name", null, element, beanDefinition, parserContext);
            parseCommonProperty("ip", null, element, beanDefinition, parserContext);
            parseCommonProperty("port", null, element, beanDefinition, parserContext);
            parseCommonProperty("rootPath", null, element, beanDefinition, parserContext);
        } else if (RegistryConfig.class.equals(beanClass)) {
            RPCNamespaceHandler.registryDefineIds.add(id);
            parseCommonProperty("protocol", null, element, beanDefinition, parserContext);
            parseCommonProperty("address", null, element, beanDefinition, parserContext);
        } else if (ReferenceConfigBean.class.equals(beanClass)) {
            RPCNamespaceHandler.referenceConfigDefineIds.add(id);
            parseCommonProperty("interface", "interfaceName", element, beanDefinition, parserContext);
        } else if (ServiceConfigBean.class.equals(beanClass)) {
            RPCNamespaceHandler.serviceConfigDefineIds.add(id);
            parseCommonProperty("interface", "interfaceName", element, beanDefinition, parserContext);
            parseSingleRef("ref", element, beanDefinition, parserContext);
        }
        return beanDefinition;
    }



    private void parseCommonProperty(String name, String alias, Element element, BeanDefinition bd,
        ParserContext parserContext) {
        String value = element.getAttribute(name);
        if (!StringUtils.isEmpty(value)) {
            String property = alias != null ? alias : name;
            bd.getPropertyValues().addPropertyValue(property, value);
        }
    }


    private void parseSingleRef(String property, Element element, BeanDefinition bd,
        ParserContext parserContext) {
        String value = element.getAttribute(property);
        if (!StringUtils.isEmpty(value)) {
            if (parserContext.getRegistry().containsBeanDefinition(value)) {
                BeanDefinition refBean = parserContext.getRegistry().getBeanDefinition(value);
                if (!refBean.isSingleton()) {
                    throw new IllegalStateException(
                        "The exported service ref " + value + " must be singleton! Please set the " + value
                            + " bean scope to singleton, eg: <bean id=\"" + value + "\" scope=\"singleton\" ...>");
                }
            }
            bd.getPropertyValues().addPropertyValue(property, new RuntimeBeanReference(value));
        }
    }
}
