#application启动模块
###快速启动-
####1、consul
####2、redis
####3、kafka（zookeeper）
###配置文件-
####打包命令：mvn clean package
####配置文件：部署目录结构
```$xslt
application.properties  |   基本应用配置
bootstrap.properties    |   辅助配置
hikaru-server.jar       |   服务包
db.properties           |   动态配置数据源
```
###启动命令
#####linux  : nohup java -jar hikaru-server.jar
#####window : java -jar hikaru-server.jar

##spring Config
#####对于WebMvcConfigurerAdapter不推荐使用可以实现WebMvcConfigurer
