package savvy.wit.framework.core.base.annotations;

import savvy.wit.framework.core.base.enums.LogType;

import java.lang.annotation.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Log
 * Author : zhoujiajun
 * Date : 2019/12/26 16:50
 * Version : 1.0
 * Description : 
 ******************************/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Log {


    String id() default "id";

    Class<?> name() default Object.class;

    LogType[] type() default {LogType.SYSTEM};
}
