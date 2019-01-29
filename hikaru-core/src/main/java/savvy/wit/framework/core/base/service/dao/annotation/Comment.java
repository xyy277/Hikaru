package savvy.wit.framework.core.base.service.dao.annotation;

import java.lang.annotation.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Comment
 * Author : zhoujiajun
 * Date : 2018/10/23 19:19
 * Version : 1.0
 * Description : 
 ******************************/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface Comment {

    enum Parameter {
        COMMENT;
        private Parameter() {

        }
    }
    String value() default "";
}
