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


####一键打包部署 运行服务
 
#####打包命令：mvn clean package
######运行命令：java -jar hikaru-server.jar，jar包同一级目录可手动定制配置properties
######访问地址默认跳转swagger首页：${server.address}:${server.port}/${server.context-path}
######rest接口



















#####author:zhoujiajun@gsafety.com(大神manito)

