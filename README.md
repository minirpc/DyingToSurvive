# DyingToSurvive
java实现的最小的使用http方式的rpc框架


###　项目结构

rpc-client : 接口调用方

rpc-core	:　核心代码，动态代理＋反射＋http请求

rpc-interface : rpc-client与rpc-server依赖此包，用于定义接口	

rpc-server :  接口提供方，需要实现rpc-interface中的接口

rpc-registry: 服务注册中心

rpc-springsupport: spring支持

todo 
1.服务注册中心　使用配置文件配置服务注册中心
2.使用lb和注册中心，需要是ＳＰＩ设计，确定服务注册中心的负载策略
3.使用spring自定义xml来解析注册中心和自动装配
4.还将学会自定义xml标签
5.加入service与reference的配置

当动态代理访问时，先调用出registry,再由registry找出支持此服务的url然后再调用　


rpc-server　负责注册服务　(服务名：项目名，接口地址，参数)


rpc-client　负责使用服务：
0.根据配置的注册中心找到注册中心
1.从注册中心中，根据服务名查找已注册的服务，得到服务项目名，接口地址
2.通过服务接口上的注解，查出参数
3.使用ＬＢ算法命中一台机器　
4.拼装请求url(项目名有了，接口地址也有了，参数也有了，)
5.发送请求



计划增加服务中心管理平台





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



　
t



