package savvy.wit.framework.core.base.service.dao.annotation;

import java.lang.annotation.*;


@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sql {

    /**
     *
     * @return
     */
    String pre() default "";

    String value() default "";
}
