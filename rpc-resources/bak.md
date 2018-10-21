### rpc　在ZK中的节点注册　
|-dyingtosurvive
|-------------rpc
|----------------com.dyingtosurvive.rpcinterface.service.IHelloService
|--------------------------------------------------------------providers
|-----------------------------------------------------------------------[127.0.0.1:8080] 临时节点，可多个

### 基于jdbcTemplate封装一个ＯＲＭ框架　
rpc-core中为核心代码,
 
rpc-trace-es为jar项目　
访问rpc-trace-es中的资源是以serviceloader方式的

如在rpc-client中引入了rpc-trace-es jar包，当发生请求时，会将请求放入到es中．

在rpc-manager中也需要引入rpc-trace-es jar包，当要读取数据时，使用serviceloader的方式来读取es中的数据



### 思考：rpcconfig与rpc-trace-### 项目类型的划分　
项目区别在哪儿里，
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



### 思考以下概念如何加入到项目中

服务降级:人为降级,服务可用，但数据不假的之类的．
      
服务熔断:自动熔断与恢复，与系统负载有关

服务限流：可考虑在网关项目rpc-gateway与rpc调用位置做，限流配置在rpc-config项目中

服务隔离:调整服务权重＋服务熔断做隔离？


假设client发送一个请求到server
server收到请求并且处理不了，则应该进行熔断
如果server挂了，client则应该换服务进行重试







计划使用技术:

netty

复杂和时间不可控业务建议投递到后端业务线程池统一处理

不推荐业务和I/O线程共用同一个线程

在rpc-monitor-agent使用使用netty建立与rpc-monitor-server的链接
当rpc-monitor-server收到数据后，将数据交由业务线程池进行处理，

使用netty作为rpc通信框架时，请求需要有requestid, 业务线程池处理返回时，使用requestid回写数据

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




服务治理做更多的扩展。比如：

　　1.基于版本号的服务管理，可以用于灰度发布。

　　2.请求的复制回放，用于模拟真实的流量进行压测。

　　3.给请求打标签用于实时的在线压测。

　　4.更灵活的负载均衡和路由策略。

　　5.内置的熔断机制，避免整个分布式系统产生雪崩效应。
### rpc-monitor与rpc-trace功能的定位:
- rpc-monitor是监控中心，如500,400,CPU,内存指标，重心在机器，jvm,cpu,内存上
- rpc-trace是追踪系统，提供调用链，raw日志，性能日志，重心在日志上



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
