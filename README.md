# DyingToSurvive
java实现的最小的使用http方式的rpc框架


###　项目结构

rpc-client : 接口调用方

rpc-core	:　核心代码，动态代理＋反射＋http请求

rpc-interface : rpc-client与rpc-server依赖此包，用于定义接口	

rpc-server :  接口提供方，需要实现rpc-interface中的接口

