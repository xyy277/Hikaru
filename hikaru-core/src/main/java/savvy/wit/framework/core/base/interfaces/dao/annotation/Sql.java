package savvy.wit.framework.core.base.interfaces.dao.annotation;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sql {

}
