package savvy.wit.framework.core.base.interfaces.dao.annotation;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface Type {

    CType type() default CType.AUTO ;

    int width() default 255;

    boolean vacancy() default false;

}
