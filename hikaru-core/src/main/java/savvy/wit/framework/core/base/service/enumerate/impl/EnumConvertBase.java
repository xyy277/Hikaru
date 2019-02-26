package savvy.wit.framework.core.base.service.enumerate.impl;

import savvy.wit.framework.core.base.service.enumerate.EnumValueContract;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : Enum转化
 * File name : EnumConvert
 * Author : zhoujiajun
 * Date : 2019/2/25 11:48
 * Version : 1.0
 * Description : Enum实体与Integer之间的转化
 ******************************/
public class EnumConvertBase<E extends EnumValueContract> {

    protected static Log log = LogFactory.getLog();

    public static EnumConvertBase convert() {
        return LazyInit.INITIALIZATION;
    }


    private static class LazyInit {
        private static EnumConvertBase INITIALIZATION = new EnumConvertBase();
    }
    /**
     * enum 转为 整型
     * @param value
     * @return
     */
    public Integer enum2Value(E value) {
        return value == null ? null : value.getParamType();
    }

    /**
     * 整型转 enum
     * @param value
     * @return
     */
    public E value2Enum(Integer value) {
        if (value == null)
            return null;
        // 获取子类泛型
        Class<E> enumClass = getEnumClass();
        E[] es = enumClass.getEnumConstants();
        Method method = null;
        try {
            method = enumClass.getMethod("getParamType");
            for (E e : es) {
                Integer integer = (Integer) method.invoke(e);
                if (integer.equals(value)) {
                    return e;
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    public E value2Enum(Class<E> enumClass, Integer value) {
        if (value == null)
            return null;
        // 获取子类泛型
        E[] es = enumClass.getEnumConstants();
        Method method = null;
        try {
            method = enumClass.getMethod("getParamType");
            for (E e : es) {
                Integer integer = (Integer) method.invoke(e);
                if (integer.equals(value)) {
                    return e;
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    /**
     * 获取子类泛型
     * @return
     */
    public Class<E> getEnumClass() {
        return (Class<E>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

}
