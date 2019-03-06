package savvy.wit.framework.core.base.service.dao.annotation;

import savvy.wit.framework.core.base.service.dao.Order;

import java.lang.annotation.*;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {

    // 是否索引,全部参数的前提条件，默认：false 非索引
    boolean index() default false;

    // 索引类型，可选参数
    KeyType type() default KeyType.DEFAULT;

    enum KeyType{

        // 默认无含义
        DEFAULT,

        // 可选参数，表示唯一索引
        UNIQUE,

        // 可选参数，表示全文索引
        FULLTEXT,

        // 可选参数，表示空间索引
        SPATIAL;

        private KeyType() {

        }
    }

    // 排序可选参数
    Order order() default Order.DEFAULT;

    // 索引别名
    String alias() default "";

    // 索引对应字段名
    String name() default "";
}
