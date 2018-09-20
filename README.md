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

rpc-monitor:管理中心,对外提供api接口

rpc-loadbalance:自定义负载均衡器

rpc-config:配置中心jar包，rpc-monitor依赖配置中心

rpc-trace:rpc调用跟踪，调用值




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
7.增加调用日志的处理用于rpc-monitor使用
8.增加权重，监测中心
9.rpc-monitor是一个web项目,用于提供管理权重，查看服务流量，查看调用链
依赖项目：rpc-config,rpc-trace-es,

10.rpc-config功能:基于mysql配置服务权重,redis进行缓存　

11.rpc-trace-es:封装调用日志，提供查询接口,


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





