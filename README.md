#拿来就用主义
##framework for anyone
###spring boot project
#####可插拔式升级cloud，分布式framework
**代码结构**
```
 hikaru-server/
    .idea
    hikaru-api             | conrtoller    - rest服务
    hikaru-application     | application   - springboot 启动
    hikaru-common          | common        - 基础应用抽分
    hikaru-core            | core          - 核心封装，不可依赖其他模块
    hikaru-entity          | 实体          - 业务实体
    hikaru-feign           | feign client  - 
    hikaru-model           | model         - 业务模型
    hikaru-service         | 业务          - 业务实现
    logs                   | log           - core测试日志输出
    out                    | build         - 打包
    .gitignore             |
    hs_err_pid24096.log    |
    hikaru-server.iml      |
    pom.xml                |
    README.md              |
```


###一键打包部署 运行服务
 
####打包命令：mvn clean package，将jar 打包到hikaru-application/target目录下
#####运行命令：java -jar hikaru-server.jar，jar包同一级目录可手动定制配置properties
#####访问地址默认跳转swagger首页：${server.address}:${server.port}/${server.context-path}
#####rest接口
#
###
###一些约定
#####bootstrap.properties ，用于辅助应用相关配置
#####application.properties，用于应用级别相关配置
##快速上手
####hikaru-application，boot模块
#####Application及相关配置
#####consul部署见↓
#####windows本地快速启动consul单机模式- consul agent -dev -ui -node= node1
#####linux consul快速启动单机模式 - nohup ./consul agent -server -bootstrap-expect 1 -data-dir /tmp/consul-node=node_1 -bind={{your ip}} -config-dir=/etc/consul.d -client 0.0.0.0 -ui &

####hikaru-common，自定义framework模块
#####目前现有的一些参考的技术选型，非最终版本
```
   application  |   framework自定义配置
   exception    |   自定义exception处理
   filter       |   自定义过滤器
   global       |   全局参数配置
   helper       |   常用helper函数
   interceptor  |   自定义拦截器
   listener     |   自定义监听器
   middleware   |   集成的框架与中间件
                activity        -   工作流
                camunda         -   一个流程引擎框架，fork activiti得来
                elasticsearch   -   elasticsearch + logstash + kibana 日志管理
                generator       -   分布式Id生成器
                kafka           -   kafka，吞度量大，数据的存储和获取是本地磁盘顺序批量操作，O(1)，消息处理的效率很高
                quartz          -   quartz持久化定时任务，适合更复杂应用场景，支持分布式，与spring独立
                rabbitmq        -   rabbitmq，可靠性强，支持对消息的可靠的传递，支持事务，不支持批量的操
                redis           -   redis缓存
                shedule         -   shedule，不支持分布式，需用分布式锁自己实现
                shiro           -   shiro，java安全框架
   websocket    |   消息推送，单个TCP连接上进行全双工通信的协议，使得客户端和服务器之间的数据交换变得更加简单
   ```

##
###微服务
####服务的发现、注册、路由、熔断、降级、分布式配置
#####当服务数并不大、业务不怎么发生变化、吞吐量稳定的时候，可以不必要进行引入，引入服务发现需要以下技术栈
######consul、Nginx、FeignClient -- dubbo、zookeeper -- Eureka（2.0 闭源） -- gateway
######微服务的限流熔断 Hystrix（Netflix 宣布停止开发 Hystrix）-- Resilience4j和Sentinel可选

######consul - nginx - consul-template 安装部署
https://blog.csdn.net/delongcpp/article/details/80119307
https://blog.csdn.net/songhaifengshuaige/article/details/79111676










#
#
#
#
####作者:zhoujiajun@gsafety.com(manito)，QQ（邮箱微信同号）: 907507646
#####group:等待你的加入
######Fork 本项目
     1、新建 Feat_xxx 分支
     2、提交代码
     3、新建 Pull Request
#
#####SOLID：单一职责原则、开闭原则、李氏代换原则、接口隔离原则、依赖倒置原则
#####开发约束：结构化编程、面向对象编程、函数式编程
#####
####采坑记录：spring boot1.x —> spring boot2.x + cloud Finchley.x
###by the way 既然你都来了点个star再走吧。
