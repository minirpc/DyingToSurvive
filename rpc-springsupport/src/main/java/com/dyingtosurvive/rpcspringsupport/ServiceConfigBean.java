package com.dyingtosurvive.rpcspringsupport;


import com.dyingtosurvive.rpccore.common.URL;
import com.dyingtosurvive.rpccore.common.ZKNode;
import com.dyingtosurvive.rpccore.register.RegistryConfig;
import com.dyingtosurvive.rpccore.registry.Registry;
import com.dyingtosurvive.rpccore.registry.RegistryFactory;
import com.dyingtosurvive.rpccore.spi.RPCServiceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class ServiceConfigBean<T>
    implements BeanFactoryAware, InitializingBean, ApplicationListener<ContextRefreshedEvent>, DisposableBean {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected String interfaceName;
    protected String group;
    protected String version;
    protected Integer timeout;
    protected Integer retries;
    // 注册中心的配置列表
    protected List<RegistryConfig> registries;
    // 是否进行check，如果为true，则在监测失败后抛异常
    protected Boolean check = Boolean.TRUE;
    protected String id;
    private transient BeanFactory beanFactory;
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
        //注册地址到zookeeper中
        ///mango/default_rpc/mango.demo.service.UserService/providers
        //注册中心数据内容为：dyingtosurvive/rpc/包名/providers ,值为ip:port/项目名

        String[] registryInfo = registries.get(0).getAddress().split(":");
        URL url = new URL();
        url.setIp(registryInfo[0]);
        url.setPort(registryInfo[1]);


        //使用jdk提供的ServiceLoader来做ＳＰＩ设计
        ServiceLoader<RegistryFactory> factories = RPCServiceLoader.load(RegistryFactory.class);
        Iterator<RegistryFactory> operationIterator = factories.iterator();
        while (operationIterator.hasNext()) {
            RegistryFactory operation = operationIterator.next();
            Registry registry = operation.getRegistry(url);
            ZKNode node = new ZKNode();
            //todo 待从配置文件中得到ip和port和servername
            node.setIp("127.0.0.1");
            node.setPort("8080");
            System.out.println("interfaceName" + interfaceName);
            node.setPackageName("com.dyingtosurvive.rpcinterface.IHelloService");
            node.setRole("providers");
            node.setProjectName("rpc-server");
            registry.register(node);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("afterPropertiesSet called");
        checkRegistryConfig();
    }

    @Override
    public void destroy() throws Exception {
        logger.info("destroy called");
    }

    private void checkRegistryConfig() {
        if (CollectionUtils.isEmpty(registries)) {
            for (String name : RPCNamespaceHandler.registryDefineNames) {
                RegistryConfig registryConfig = beanFactory.getBean(name, RegistryConfig.class);
                System.out.println(registryConfig.getAddress());
                registries = Collections.singletonList(registryConfig);
            }
        }
    }
}
