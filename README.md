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






　




