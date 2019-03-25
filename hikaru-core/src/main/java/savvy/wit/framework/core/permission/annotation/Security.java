package savvy.wit.framework.core.permission.annotation;

import java.lang.annotation.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : Security注解
 * File name : Security
 * Author : zhoujiajun
 * Date : 2019/3/22 11:41
 * Version : 1.0
 * Description : 在类上添加该注解以加入对当前类的权限设置
 ******************************/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Security {
}
