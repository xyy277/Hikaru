package savvy.wit.framework.core.base.service.dao.annotation;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Documented
public @interface Id {

    boolean auto() default false;


}
