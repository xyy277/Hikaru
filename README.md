##framework for 512
###spring boot project
**代码结构**
```
 hikaru-server/
    .idea
    hikaru-api             | conrtoller    - rest服务
    hikaru-application     | application   - springboot 启动
    hikaru-common          | common        - 基础应用抽分
    hikaru-core            | core          - 核心封装，不可依赖其他模块
    hikaru-entity          | 实体          - 业务实体
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
 
####打包命令：mvn clean package
#####运行命令：java -jar hikaru-server.jar，jar包同一级目录可手动定制配置properties
#####访问地址默认跳转swagger首页：${server.address}:${server.port}/${server.context-path}
#####rest接口
#
###
###一些约定
#####bootstrap.properties ，用于数据库相关配置
#####application.properties，用于应用级别相关配置
##快速上手
####hikaru-application，boot模块
#####Application及相关配置

####hikaru-common，自定义framework模块
#####目前现有的一些参考的技术选型，非最终版本
```
   application  |   framework自定义配置
   exception    |   自定义exception
   filter       |   自定义过滤器
   global       |   全局参数配置
   helper       |   helper函数
   interceptor  |   自定义拦截器
   listener     |   自定义监听器
   middleware   |   集成的框架与中间件
                activity        -   工作流
                elasticsearch   -   elk 日志管理
                generator       -   分布式Id生成器
                kafka           -   kafka，吞度量大，数据的存储和获取是本地磁盘顺序批量操作，O(1)，消息处理的效率很高
                quartz          -   quartz持久化定时任务，适合更复杂应用场景，支持分布式，与spring独立
                rabbitmq        -   rabbitmq，可靠性强，支持对消息的可靠的传递，支持事务，不支持批量的操
                redis           -   redis缓存
                shedule         -   shedule，不支持分布式，需用分布式锁自己实现
                shiro           -   shiro，java安全框架
   websocket    |   单个TCP连接上进行全双工通信的协议，使得客户端和服务器之间的数据交换变得更加简单
   ```















#
#
#
#
####author:zhoujiajun@gsafety.com(大神manito)，QQ: 907507646

