package savvy.wit.framework.core.permission.annotation;

import java.lang.annotation.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : Permission注解
 * File name : Permission
 * Author : zhoujiajun
 * Date : 2019/3/20 16:42
 * Version : 1.0
 * Description : 添加在标注Security注解类的方法上，该注解将启用对该方法的权限验证
 ******************************/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Permission {

    // 权限标识
    String[] value() default {};

    // 权限路径
    String[] path() default {};

    // 权限名称
    String name();


}
