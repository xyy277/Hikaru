##Model模块约束
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