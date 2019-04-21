package savvy.wit.framework.core.base.service.dao.annotation;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Documented
public @interface Id {

    // 针对 数据库 int型自增主键
    boolean auto() default false;

    int step() default 1;

}
