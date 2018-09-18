package com.dyingtosurvive.rpcspringsupport;


import com.dyingtosurvive.rpccore.common.ApplicationConfig;
import com.dyingtosurvive.rpccore.common.ZKInfo;
import com.dyingtosurvive.rpccore.common.ZKNode;
import com.dyingtosurvive.rpccore.registry.RegistryConfig;
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
 * rpc:server代表的bean
 *
 * created by changesolider on 2018-09-18
 * @param <T>
 */
public class ServiceConfigBean<T>
    implements BeanFactoryAware, InitializingBean, ApplicationListener<ContextRefreshedEvent>, DisposableBean {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String interfaceName;
    //注册中心的配置列表
    private List<RegistryConfig> registries;
    private String id;
    private transient BeanFactory beanFactory;
    //interfaceName指向的实现类
    private T ref;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //使用ServiceLoader查找注册中心的实现
        ServiceLoader<RegistryFactory> factories = RPCServiceLoader.load(RegistryFactory.class);
        Iterator<RegistryFactory> operationIterator = factories.iterator();
        if (!operationIterator.hasNext()) {
            throw new IllegalStateException("请提供RegistryFactory的实现!");
        }
        RegistryFactory registryFactory = operationIterator.next();
        //连接zk
        Registry registry = registryFactory.getRegistry(getZKInfoFromRegistries());
        //发布服务
        registry.registerService(assembleZKNode());
    }

    private ZKNode assembleZKNode() {
        ApplicationConfig applicationConfig = null;
        for (String beanId : RPCNamespaceHandler.registryAppIds) {
            applicationConfig = beanFactory.getBean(beanId, ApplicationConfig.class);
        }
        if (applicationConfig == null) {
            throw new IllegalStateException("未找到应用配置!");
        }
        ZKNode node = new ZKNode();
        node.setIp(applicationConfig.getIp());
        node.setPort(applicationConfig.getPort());
        node.setPackageName(interfaceName);
        node.setRole("providers");
        node.setProjectName(applicationConfig.getRootPath());
        return node;
    }

    private ZKInfo getZKInfoFromRegistries() {
        String[] registryInfo = registries.get(0).getAddress().split(":");
        ZKInfo zkInfo = new ZKInfo();
        zkInfo.setIp(registryInfo[0]);
        zkInfo.setPort(registryInfo[1]);
        return zkInfo;
    }


    /**
     * 完成属性注入后回调
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //每个rpc:server bean都需要知道注册中心的信息
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

    public T getRef() {
        return ref;
    }

    public void setRef(T ref) {
        this.ref = ref;
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
}
