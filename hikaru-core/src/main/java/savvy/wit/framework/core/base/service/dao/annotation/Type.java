package savvy.wit.framework.core.base.service.dao.annotation;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface Type {

    /**
     * column 类型
     * @return
     */
    CType type() default CType.AUTO ;

    /**
     * 宽度
     * @return
     */
    int width() default 255;

    /**
     * 默认非空,若为boolean 默认false
     * @return
     */
    boolean vacancy() default false;

    /**
     * 整型默认值
     * @return
     */
    int acquiescence() default 0;

}
