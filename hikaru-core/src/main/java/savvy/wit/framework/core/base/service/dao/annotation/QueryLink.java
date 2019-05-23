package savvy.wit.framework.core.base.service.dao.annotation;

import java.lang.annotation.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 设置关联查询
 * File name : QueryLink
 * Author : zhoujiajun
 * Date : 2019/5/22 12:22
 * Version : 1.0
 * Description : 
 ******************************/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryLink {

    Class value();

    String name();
}
