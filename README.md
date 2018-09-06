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
2.使用lb　和ＳＰＩ设计，确定服务注册中心的负载策略
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
4.拼装请求url
5.发送请求

















　




