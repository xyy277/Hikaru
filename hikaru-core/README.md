###core
####simple ORM、simple Log、常用工具
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
#1 Config全局配置
##1.1 ConfigFactory类
####1.1.1.1 数据工厂配置
#####1、设置数据源 setSource(String... paths)
**paths - 配置文件路径，全部加载，重复会覆盖。**
#####2、设置配置参数  setProperty(String key, String value)
**key - value，一个property配置**
#####3、设置泛型类 setEnumClassList(String... pack)
**pack - 泛型类所在包，自动扫描并加载包下所有泛型**
#####4、可以自定义设置全局配置参数到内存,运行期间有效

#2 ORM
##2.1 Dao接口
###2.1.1 配置
####2.1.1.1 Dao需要的全局配置
1、db参数
```
#<!--连接池参数 -->
#初始化连接数
initialSize=20
#最大空闲连接
maxIdle=20
#最小空闲连接
minIdle=5
#最大连接数
maxTotal=50
#最大等待时间
maxWaitMillis=1000
#Driver
driver=com.mysql.jdbc.Driver
#url
url=jdbc:mysql://127.0.0.1:3306/system?useUnicode=true&characterEncoding=utf8&useSSL=false
#user
user=root
#password
password=CUCsSZyDWzXsKJvFj2xsHhe6oCsmpC8ocaVZLDF-nx29DubgDXMCl2J9fCTxZQHN2tX1wYaLdxp97rIrXHgkSbEKloOveUKzl42dFrfENp7Q1mXTNHz-_WgFPn_AIAzZ7n_BNNo6PbiAJarul50TZ_rib1UY1I7KqKv-BWZTZiI
```
2、如若使用到泛型需要手动配置setEnumClassList(String... pack)泛型类包路径用于扫描，配置即开启扫描，默认不开启
3、其他个别参数配置及默认，如vacancy、intervalMark等：
```
a.vacancy保存null会默认数据库保存空字符串""，减少因为部分数据为空而导致报错无法保存的情形；
b.intervalMark为保存数组集合类型时，保存到数据库中的字符串中的间隔符 - 查询后会直接转换为数组或集合，特别设置仅仅是防止可能数据中带有的符号会影响默认间隔符（，）的使用
```
###3 core包
####3.1 常用工具包
**savvy.wit.framework.core.base**
####3.2 文件、表格处理包
**savvy.wit.framework.core.pattern.adapter**
####3.3 base CURD封装 
**savvy.wit.framework.core.service**
####3.4 图形
**savvy.wit.framework.core.structure**
####3.5 权限
**savvy.wit.framework.core.permission**
####3.6 算法模型
**savvy.wit.framework.core.algorithm.model**
####3.7 简单的日志系统
**savvy.wit.framework.core.base.service.log**
####不够完善 后续会加
###author : zhoujiajun@gsafety.com
