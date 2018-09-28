# DyingToSurvive
java实现的最小的使用http方式的rpc框架


###　项目结构

rpc-demo
    rpc-interface : rpc-client与rpc-server依赖此包，用于定义接口	
    rpc-client : 接口调用方
    rpc-server :  接口提供方，需要实现rpc-interface中的接口

rpc-core	:　核心代码，动态代理＋反射＋http请求

rpc-registry: 服务注册中心

rpc-springsupport: spring支持

rpc-monitor:监测中心,对外提供服务（服务监控）

rpc-loadbalance:自定义负载均衡器

rpc-config:配置中心jar包，对外提供服务（服务权重）

rpc-trace:rpc调用跟踪，调用值,对外提供服务（服务追踪）　

rpc-manager:统一的管理平台　（治理平台）
依赖于项目:rpc-monitor,rpc-config,rpc-trace

rpc-gateway:服务网关

rpc-benchmarks:　测试性能项目


### rpc-monitor与rpc-trace功能的定位:
- rpc-monitor是监控中心，如500,400,CPU,内存指标，重心在机器，jvm,cpu,内存上
- rpc-trace是追踪系统，提供调用链，raw日志，性能日志，重心在日志上



### 开发记录
rpc-server　负责注册服务　(服务名：项目名，接口地址，参数)

rpc-client　负责使用服务：
0.根据配置的注册中心找到注册中心
1.从注册中心中，根据服务名查找已注册的服务，得到服务项目名，接口地址
2.通过服务接口上的注解，查出参数
3.使用ＬＢ算法命中一台机器　
4.拼装请求url(项目名有了，接口地址也有了，参数也有了，)
5.发送请求
6.当动态代理访问时，先调用出registry,再由registry找出支持此服务的url然后再调用　
7.http 支持post请求
8.rpc-config包引入，可以根据rpcconfig中


### 实现效果　
如有productservice

productservice中有服务（产品列表，产品操作）

我们发现产品列表服务的调用次数远远大于产品操作

因此想只对产品列表横向扩展，即增加机器只提供产品列表，不提供产品操作

那么只需要在productservice中将产品操作服务屏蔽掉即可

或者说：在服务管理控制台，动态控制某些机器的产品操作权重为０，则新请求不会打到此机器上．


### 服务治理

服务权重

服务流量

服务降级

服务监控

服务Ａ／Ｂ　testing

服务配置中心

服务追踪　

### todo 
1.服务注册中心　使用配置文件配置服务注册中心  OK
2.使用lb和注册中心，需要是ＳＰＩ设计，确定服务注册中心的负载策略 OK
3.使用spring自定义xml来解析注册中心和自动装配 OK
4.还将学会自定义xml标签 OK
5.加入service与reference的配置 OK
6.增加调用的回调设计
7.增加调用日志的处理用于rpc-monitor使用 OK
8.增加权重，监测中心  OK
9.rpc-monitor：监测中心,性能,主要用于采集性能
10.rpc-config功能:基于mysql配置服务权重,redis进行缓存　,服务鉴权配置,服务限流配置，网关路由配置
11.rpc-trace-es:封装调用日志，提供查询接口,
12.引入本地缓存，当注册中心不可用或注册中心地址不可用时，使用本地缓存的服务地址
13.rpc-manager是一个web项目,用于提供管理权重，查看服务流量，查看调用链,查看监控,



### rpc　在ZK中的节点注册　
|-dyingtosurvive
|-------------rpc
|----------------com.dyingtosurvive.rpcinterface.service.IHelloService
|--------------------------------------------------------------providers
|-----------------------------------------------------------------------[127.0.0.1:8080] 临时节点，可多个



### 此项目关键技术　
- jdk 动态代理　
- spring标签定义　
- java spi设计
- zk操作
- es操作(日志)
- lb算法
- redis操作
- rocketmq操作
- 日志切面，将日志放入到rpc-trace-es中,使用rocketmq进行消息发送，减少对业务的侵入
- threadlocal追踪header信息
- guavacache或springcache
- 使用springjdbctemplate封装数据库操作
　

### 计划引入技术　
- netty
- nginx
websocket


### 一些想法
RAW日志异步输出到队列,交由netty进行处理，netty收到后，使用websocket同步到web界面　

多级缓存:springcache,google guavacache, nginx+lua访问rediscache

秒杀场景:

服务降级:服务端降级,rpc-core包

服务限流

服务鉴权：进行服务调用鉴权

服务网关引入:
第一类网关:聚合网关，完成数据聚合操作,类似于中台项目
第二类网关:应用网关



### rpc-gateway 服务网关
第一步：
增加项目
rpc-gateway

路由:应用名称

使用rpc-config保存网关配置信息,如
/user : rpc-user

第二步:
openresty 对接rpc-gateway

rpc-gateway为web项目

鉴权需要分类
API接口的访问权限控制
用户鉴权


此项目需要引入API接口的访问权限控制,





### 基于jdbcTemplate封装一个ＯＲＭ框架　
rpc-core中为核心代码,
 
rpc-trace-es为jar项目　
访问rpc-trace-es中的资源是以serviceloader方式的

如在rpc-client中引入了rpc-trace-es jar包，当发生请求时，会将请求放入到es中．

在rpc-manager中也需要引入rpc-trace-es jar包，当要读取数据时，使用serviceloader的方式来读取es中的数据



思考：rpcconfig与rpc-trace-es项目区别在哪儿里，
rpcconfig可否使用jar的形式集成到service中

rpcconfig项目会操作数据库，redis

rpc-trace-es项目会操作es

rpc-manager项目管理rpcconfig项目　是通过　api访问的
rpc-trace-es项目管理es是通过serviceloader访问的．


假设只有rpcclient,和rpc-manager
当rpcclient挂了，rpcclient中的rpc-trace-es也挂了．

rpc-manager中的rpc-trace-es没有挂，可以继承得到数据

如果　rpc-manager也挂了

rpc-trace-es 项目也挂了


结论:rpc-trace-es也做成web服务好一些．rpcclient和rpc-manager挂了都不影响rpc-trace-es正常提供服务（写日志，查日志）


### rpc-monitor设计　

rpc-monitor-agent为探针，各个service项目上进行安装　，负责上报系统信息

rpc-monitor-server为服务端，负责收集系统信息

计划使用技术:

netty



### 项目类型的划分　
war包:

rpc-config
rpc-client
rpc-server
rpc-manager
rpc-trace
rpc-gateway
rpc-monitor-server

jar包:
rpc-core
rpc-interface
rpc-loadbalance
rpc-registry
rpc-springsupport
rpc-demo-interface
rpc-benchmarks
rpc-monitor-agent


### rpc-manager项目功能　
rpc-manager项目定位为rpc微服务统一管理　平台　，功能包括:

服务治理：
    权重，流量，服务权限配置，网关路由配置等　
    
服务日志:
    追踪日志，400,500,错误，日志监控规则　
    
机器性能监控
    jvm,tomcat,性能指标，监控规则　
    


### 思考以下概念如何加入到项目中

服务降级
      
服务熔断

服务限流：可考虑在网关项目rpc-gateway与rpc调用位置做，限流配置在rpc-config项目中

服务隔离:调整服务权重＋服务熔断做隔离？


假设client发送一个请求到server
server收到请求并且处理不了，则应该进行熔断
如果server挂了，client则应该换服务进行重试















