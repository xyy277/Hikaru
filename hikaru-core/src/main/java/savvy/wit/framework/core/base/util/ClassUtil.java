package savvy.wit.framework.core.base.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import savvy.wit.framework.core.base.annotations.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ClassUtil
 * Author : zhoujiajun
 * Date : 2019/3/25 12:35
 * Version : 1.0
 * Description : 
 ******************************/
public class ClassUtil {


    protected ClassUtil() {

    }

    public static ClassUtil me() {
        return LazyInit.INITIALIZATION;
    }

    private static class LazyInit {
        private static ClassUtil INITIALIZATION = new ClassUtil();
    }

    public <T> Class<T> getGenericSuperclass(Class clazz) {
        Type type = clazz.getGenericSuperclass(); // 拿到带类型参数的泛型父类
        if(type instanceof ParameterizedType){ // 这个Type对象根据泛型声明，就有可能是4中接口之一，如果它是BaseServiceImpl<User>这种形式
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments(); // 获取泛型的类型参数数组
            if(actualTypeArguments != null && actualTypeArguments.length > 0) {
                if(actualTypeArguments[0] instanceof Class){ // 类型参数也有可能不是Class类型
                    clazz = (Class<T>) actualTypeArguments[0];
                }
            }
        }
        return clazz;
    }

    /**
     * 获取方法中声明的注解
     *
     * @param joinPoint
     * @return
     * @throws NoSuchMethodException
     */
    public <A extends Annotation>A getDeclaredAnnotation(ProceedingJoinPoint joinPoint, Class<A> aClass) throws NoSuchMethodException {
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 反射获取目标类
        Class<?> targetClass = joinPoint.getTarget().getClass();
        // 拿到方法对应的参数类型
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        // 根据类、方法、参数类型（重载）获取到方法的具体信息
        Method objMethod = targetClass.getMethod(methodName, parameterTypes);
        // 拿到方法定义的注解信息
        A a = objMethod.getDeclaredAnnotation(aClass);
        // 返回
        return a;
    }
}
