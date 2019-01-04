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


####kafka server：三台云服务器，172.21.15.29 - 31
 
#####hikaru消息系统对于消息存储并不是消费完了就进行删除，而是根据时间策略（默认保存一周）或者文件定制大小，删除旧消息。
######故此，启动服务访问/kafka/{topic}/{amount}/{num} 将由num个线程并发生产消息存入hikaru，测试hikaru及zookeeper日志文件增长情况。
######控制台会即时打印消费信息 
######/kafka/stop 停止生产线程



















#####author:zhoujiajun@gsafety.com(大神manito)

