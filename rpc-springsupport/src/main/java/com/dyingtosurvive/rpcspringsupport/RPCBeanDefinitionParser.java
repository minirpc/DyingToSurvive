package com.dyingtosurvive.rpcspringsupport;


import com.dyingtosurvive.rpccore.register.RegistryConfig;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;


public class RPCBeanDefinitionParser implements BeanDefinitionParser {

    private final Class<?> beanClass;

    private final boolean required;

    public RPCBeanDefinitionParser(Class<?> beanClass, boolean required) {
        this.beanClass = beanClass;
        this.required = required;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static BeanDefinition parse(Element element, ParserContext parserContext, Class<?> beanClass,
        boolean required)
        throws ClassNotFoundException {
        //RootBeanDefinition:概念化的bean，会绑定具体的bean
        RootBeanDefinition bd = new RootBeanDefinition();
        //设置bean所代表的对象
        bd.setBeanClass(beanClass);
        // 不允许lazy init
        bd.setLazyInit(false);

        // 如果没有id则按照规则生成一个id,注册id到context中
        String id = element.getAttribute("id");
        if ((id == null || id.length() == 0) && required) {
            String generatedBeanName = element.getAttribute("name");
            if (generatedBeanName == null || generatedBeanName.length() == 0) {
                generatedBeanName = element.getAttribute("interface");
            }
            if (generatedBeanName == null || generatedBeanName.length() == 0) {
                generatedBeanName = beanClass.getName();
            }
            id = generatedBeanName;
            int counter = 2;
            while (parserContext.getRegistry().containsBeanDefinition(id)) {
                id = generatedBeanName + (counter++);
            }
        }
        if (id != null && id.length() > 0) {
            if (parserContext.getRegistry().containsBeanDefinition(id)) {
                throw new IllegalStateException("Duplicate spring bean id " + id);
            }
            parserContext.getRegistry().registerBeanDefinition(id, bd);
        }
        System.out.println("id:" + id);
        bd.getPropertyValues().addPropertyValue("id", id);

        //解析属性
        if (RegistryConfig.class.equals(beanClass)) {
            RPCNamespaceHandler.registryDefineNames.add(id);
            parseCommonProperty("protocol", null, element, bd, parserContext);
            parseCommonProperty("address", null, element, bd, parserContext);
            parseCommonProperty("connect-timeout", "connectTimeout", element, bd, parserContext);
            parseCommonProperty("session-timeout", "sessionTimeout", element, bd, parserContext);
            parseCommonProperty("username", null, element, bd, parserContext);
            parseCommonProperty("password", null, element, bd, parserContext);
            parseCommonProperty("default", "isDefault", element, bd, parserContext);
        } else if (ReferenceConfigBean.class.equals(beanClass)) {
            RPCNamespaceHandler.referenceConfigDefineNames.add(id);

            parseCommonProperty("interface", "interfaceName", element, bd, parserContext);

            String registry = element.getAttribute("registry");
            if (!StringUtils.isEmpty(registry)) {
                parseMultiRef("registries", registry, bd, parserContext);
            }

            parseCommonProperty("group", null, element, bd, parserContext);
            parseCommonProperty("version", null, element, bd, parserContext);

            parseCommonProperty("timeout", null, element, bd, parserContext);
            parseCommonProperty("retries", null, element, bd, parserContext);
            parseCommonProperty("check", null, element, bd, parserContext);

        } else if (ServiceConfigBean.class.equals(beanClass)) {
            RPCNamespaceHandler.serviceConfigDefineNames.add(id);

            parseCommonProperty("interface", "interfaceName", element, bd, parserContext);

            parseSingleRef("ref", element, bd, parserContext);

            String registry = element.getAttribute("registry");
            if (!StringUtils.isEmpty(registry)) {
                parseMultiRef("registries", registry, bd, parserContext);
            }

            String protocol = element.getAttribute("protocol");
            if (!StringUtils.isEmpty(protocol)) {
                parseMultiRef("protocols", protocol, bd, parserContext);
            }

            parseCommonProperty("timeout", null, element, bd, parserContext);
            parseCommonProperty("retries", null, element, bd, parserContext);

            parseCommonProperty("group", null, element, bd, parserContext);
            parseCommonProperty("version", null, element, bd, parserContext);

        }
        return bd;
    }

    private static void parseCommonProperty(String name, String alias, Element element, BeanDefinition bd,
        ParserContext parserContext) {

        String value = element.getAttribute(name);
        if (!StringUtils.isEmpty(value)) {
            String property = alias != null ? alias : name;
            bd.getPropertyValues().addPropertyValue(property, value);
        }
    }

    /**
     * 处理单个引用
     *
     * @param property
     * @param element
     * @param bd
     * @param parserContext
     */
    private static void parseSingleRef(String property, Element element, BeanDefinition bd,
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

    /**
     * 处理多个引用
     * <p>
     * 运行时的依赖注入
     *
     * @param property
     * @param value
     * @param bd
     * @param parserContext
     */
    private static void parseMultiRef(String property, String value, BeanDefinition bd,
        ParserContext parserContext) {
        String[] values = value.split("\\s*[,]+\\s*");
        ManagedList list = null;
        for (int i = 0; i < values.length; i++) {
            String v = values[i];
            if (v != null && v.length() > 0) {
                if (list == null) {
                    list = new ManagedList();
                }
                list.add(new RuntimeBeanReference(v));
            }
        }

        bd.getPropertyValues().addPropertyValue(property, list);
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        try {

            return parse(element, parserContext, beanClass, required);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
