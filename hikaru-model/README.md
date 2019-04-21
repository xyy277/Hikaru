##Model模块约束

###关于模型字段的属性类型的定义
常用基本类型请使用包装类：Integer、Character......

###关于Enum的使用
####*每一个Enum枚举类都需要实现EnumValueContract接口*
Enum枚举值将统一以整数形式保存到数据库中

####支持存数组集合等类型，实际存入数据库还是间隔符+字符串的形式，所以数据库空间需要提前规划
间隔符配置，比如： ConfigFactory.setProperty("intervalMark", ","); 默认为逗号，的形式。
####基本类型保存会直接保存到数据库中，数组与List集合类型会以字符串追加，的方式保存，map对象集合将以json字符串的形式保存
在保存这些非基本类型的前提字段注解@Type在mysql数据库中必须如下:
@Type(type = CType.VARCHAR) 用来设置保存格式为字符串


###lombok使用
简化model类，自动设置get、set
```$xslt
@Data ：注解在类上；提供类所有属性的 getting 和 setting 方法，此外还提供了equals、canEqual、hashCode、toString 方法
@Setter：注解在属性上；为属性提供 setting 方法
@Getter：注解在属性上；为属性提供 getting 方法
@Log4j ：注解在类上；为类提供一个 属性名为log 的 log4j 日志对象
@NoArgsConstructor：注解在类上；为类提供一个无参的构造方法
@AllArgsConstructor：注解在类上；为类提供一个全参的构造方法

```

###ORM使用（core包）
######savvy.wit.framework.core.base.interfaces.dao.annotation.*
```$xslt
@Table      ：   类级别注解用于ORM映射
@Id         ：   申明主键（当为整数时，auto属性可以为自增策略）
@Column     ：   该字段用于ORM映射
@Type       ：   指定参数类型  参数： type、width、vacancy 分别为类型、长度、是否为空
@Comment    ：   字段描述备注

```
###Validated数据校验使用
######javax.validation.constraints.*
######当验证某个数据集合List<E>时使用ValidList<E>包装
```$xslt
AssertFalse
AssertTrue
DecimalMax
DecimalMin
Digits
Email
Future
FutureOrPresent
Max
Min
Negative
NegativeOrZero
NotBlank
NotEmpty
NotNull
Null
Past
PastOrPresent
Pattern
Positive
PositiveOrZero
Size
```
###Swagger
```$xslt
@ApiModelProperty 给字段备注
参数：
value–字段说明 
name–重写属性名字 
dataType–重写属性类型 
required–是否必填 
example–举例说明 
hidden–隐藏
```