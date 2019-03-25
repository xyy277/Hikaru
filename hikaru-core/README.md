###core
####simpleORM、测试级别Log、常用工具
#####代码结构
```
----------core----------
algorithm
base
construction
pattern
permission
service
structure
```
#1 
#2 ORM
##1.1 DAO
###1.1.1 配置
####1.1.1.1 DbFactory 数据工厂配置
#####1、设置数据源 setSource(String... paths)
**paths - 配置文件路径，先入先加载，加载成功即不再加载。**
#####2、设置配置参数  setProperty(String key, String value)
**key - value，一个property配置**
#####3、设置泛型类 setEnumClassList(String... pack)
**pack - 泛型类所在包，自动扫描并加载包下所有泛型**
####1.1.1.2 
###使用
###author : zhoujiajun@gsafety.com
