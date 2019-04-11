# DyingToSurvive
自研微服务框架,包括服务配置,服务发现,服务注册,调用链路追踪,服务监控,服务网关,治理平台等模块.致力于开发出高度插件式服务框架.致力于成为一个微服务框架入门项目.

### 总则
* 能自己实现就不依赖第三方,自己实现难度大才依赖第三方
* 高度插件式服务框架

### 项目目的
*  1.学习没有掌握的中间件，通过使用VirtualBox搭建的服务集群与此项目结合产生出微服务集群架构;
*  2.提高编码水平，提高抽象编程能力，
*  3.增加对服务治理平台的认知，
*  4.增加对rpc通信原理的认知


### 项目结构概览
* rpc-interface:内部服务调用接口定义
* rpc-core:核心代码，动态代理＋反射＋http请求
* rpc-registry: 服务注册中心
rpc-registry-zookeeper为服务注册中心具体实现
* rpc-springsupport: spring支持
* rpc-communication:通信组件
 rpc-communication-http:使用http建立的通信组件
 rpc-communication-netty:使用netty建立的通信组件
* rpc-monitor:服务监控中心,对外提供服务监控查询服务
* rpc-loadbalance:自定义负载均衡器组件
* rpc-config:服务配置中心，对外提供服务
* rpc-trace:rpc调用跟踪，调用值,对外提供服务（服务追踪）　
rpc-trace-es为调用跟踪具体实现
* rpc-gateway:网关服务
* rpc-message:基础通信组件
   rpc-message-rocketmq为其具体实现
* rpc-manager:服务治理平台,依赖于项目:rpc-monitor,rpc-config,rpc-trace
* rpc-demo: 测试demo
    rpc-interface : demo接口定义
    rpc-client : 服务调用方
    rpc-server : 服务提供方
* rpc-benchmarks:　测试性能项目

### 依赖服务
* mysql
* redis
* elasticsearch
* nginx
* tomcat
* rocketmq
* kafka
* flume
* strom
* zookeeper

### 依赖服务集群搭建说明
https://github.com/chenzhibing/FinalDestination/tree/master/centos系列

### 项目所用技术　
- jdk 动态代理　
- spring标签定义　
- java spi设计
- zk操作
- es操作
- lb算法
- redis操作
- rocketmq操作
- 使用springjdbctemplate封装数据库操作
- nginx
- netty
　
### 计划引入技术　
- websocket
- 池化http请求
- 日志统一输出
- 线程池，线程组，监控，线程隔离
- 日志切面，将日志放入到rpc-trace-es中,使用rocketmq进行消息发送，减少对业务的侵入
- threadlocal追踪header信息
- guavacache或springcache

### 模块类型划分　
#### war包:
* rpc-config
* rpc-client
* rpc-server
* rpc-manager
* rpc-trace
* rpc-gateway
* rpc-monitor-server

#### jar包:
* rpc-core
* rpc-interface
* rpc-loadbalance
* rpc-registry
* rpc-message
* rpc-springsupport
* rpc-demo-interface
* rpc-benchmarks
* rpc-monitor-agent
* rpc-communication-http
* rpc-communication-netty

### 模块功能细节

#### rpc-config
服务配置中心
基于mysql配置服务权重,redis进行缓存,服务鉴权配置,服务限流配置，网关路由配置

####  rpc-message
当Ａ服务调用Ｂ服务时，需要将调用日志通过消息中间件传入到rpc-trace中
消息中间件的注入使用ＳＰＩ方式，做到解偶．rpc-message-rocketmq为其具体实现

#####  rpc-message-rocketmq
使用rocketmq 作为消息中心件，负责转发日志消息
rocketmq将作为rpc-message的一个实现方式

####  rpc-communication
建立基础通信组件，为后续项目提供通信的基础框架，通信组件主要包含client端和server端

server端用于建立服务
client端用于访问服务　

在使用具体的通信组件时，需要确保server端与client是一致的，即都使用同一个具体组件，

具体实现有：

##### rpc-communication-http
使用httpserver建立的通信组件，使用http作为通信组件

#####  rpc-communication-netty
使用netty建立的通信组件，使用netty作为通信组件

####  rpc-gateway 
负责请求路由到后端服务上

#### rpc-monitor设计　
rpc-monitor包含rpc-monitor-agent与rpc-monitor-server两个模块

##### rpc-monitor-agent
监控指标收集探针，各个service项目上进行集成，负责上报指标信息

##### rpc-monitor-server
监控指标收集服务端，主要功能为：
1. .收集agent上报来的监控指标
2. 将监控指标存储，为提供查询接口

####  rpc-manager
rpc-manager项目定位为rpc微服务统一管理平台,功能包括:
1. 服务治理：权重(rpc-config)，流量(rpc-config)，服务权限配置(rpc-config)，网关路由配置(rpc-config)等　
2. 服务日志:追踪日志(rpc-message,rpc-trace)，400,500,错误日志，日志监控规则　
3. 服务监控：机器性能监控， jvm,tomcat,性能指标，监控规则　


### 用户请求的完成流程(待完善)
0.根据配置的注册中心找到注册中心
1.从注册中心中，根据服务名查找已注册的服务，得到服务项目名，接口地址
2.通过服务接口上的注解，查出参数
3.使用ＬＢ算法命中一台机器　
4.拼装请求url(项目名有了，接口地址也有了，参数也有了，)
5.发送请求
6.当动态代理访问时，先调用出registry,再由registry找出支持此服务的url然后再调用　
7.http 支持post请求
8.rpc-config包引入，可以根据rpcconfig中

可以参照：微服务框架在集群部署后的架构图

### 微服务框架在集群部署后的架构图如下：
![avatar](http://on-img.com/chart_image/5bc97cbce4b08faf8c83ee6d.png?_=1540020487577)

### todo＆开发日志 
* 1.服务注册中心　使用配置文件配置服务注册中心  OK
* 2.使用lb和注册中心，需要是ＳＰＩ设计，确定服务注册中心的负载策略 OK
* 3.使用spring自定义xml来解析注册中心和自动装配 OK
* 4.还将学会自定义xml标签 OK
* 5.加入service与reference的配置 OK
* 6.增加调用的回调设计
* 7.增加调用日志的处理用于rpc-monitor使用 OK
* 8.增加权重，监测中心  OK
* 9.rpc-monitor：监测中心,性能,主要用于采集性能
* 10.rpc-config功能:基于mysql配置服务权重,redis进行缓存　,服务鉴权配置,服务限流配置，网关路由配置
* 11.rpc-trace-es:封装调用日志，提供查询接口,
* 12.引入本地缓存，当注册中心不可用或注册中心地址不可用时，使用本地缓存的服务地址
* 13.rpc-manager是一个web项目,用于提供管理权重，查看服务流量，查看调用链,查看监控,
* 14.rpc-client访问rpc-server时，会将追踪日志通过rpc-message传递到rpc-trace项目中:已打通
* 15.rpc-communication包封装
* 16.接口版本管理(提供接口降级这样的需求)

### 启动流程
1.启动zk机器, zk已开启开机自启动，无须手动启动
2.启动rpc-server
配置应用，使用如下方式测试请求是否相通
http://localhost:8080/rpcserver/hello?helloMessage=123456444
tomcat部署的应用名为rpcserver

3.启动rpc-client
tomcat部署的应用名为rpc-client


