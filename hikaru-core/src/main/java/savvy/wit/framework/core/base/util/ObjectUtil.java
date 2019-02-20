package savvy.wit.framework.core.base.util;

import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by aj907 on 2018/4/27.
 */
public class ObjectUtil {

    private static Log log = LogFactory.getLog();

    public static Object getValueByFiledName(Object o, String name) {
        try {
            Method[] methods = o.getClass().getMethods();
            List<Method> list = Arrays.asList(methods).parallelStream()
                    .filter(method -> ("get"+name).toLowerCase().equals(method.getName().toLowerCase()) )
                    .collect(Collectors.toList());
            return list.get(0).invoke(o);
        }catch (Exception e){
            log.error(e);
        }
        return null;
    }

    public static Object getValueByFiled(Object o, Field field) {
        return getValueByFiledName(o,field.getName());
    }

    public static Object getValue(Object o, String type, String value) {
        try {
            Method[] methods = o.getClass().getMethods();
            List<Method> list = Arrays.asList(methods).parallelStream()
                    .filter(method -> ("get"+type).toLowerCase().equals(method.getName().toLowerCase()) )
                    .filter(method -> method.getParameterTypes()[0].equals(String.class))
                    .collect(Collectors.toList());
            return list.get(0).invoke(o, value);
        }catch (Exception e){
            log.error(e);
        }
        return null;
    }

    public static void setValueByFieldName(Object obj, String name, Object value) {
        try {
            Method method = Arrays.asList(obj.getClass().getMethods()).parallelStream()
                    .filter(m -> ("set"+name).toLowerCase().equals(m.getName().toLowerCase()) )
                    .collect(Collectors.toList()).get(0);
            autoInvoke(method, obj, value, method.getParameterTypes()[0]);
        }catch (Exception e){
            log.error(e);
        }
    }

    /**
     * 不定长度参数
     * @param object
     * @param name
     * @param value
     */
    public static void setValue(Object object, String name, Object... value) {
        try {
            Arrays.asList(object.getClass().getMethods()).parallelStream()
                    .filter(m -> ("set"+name).toLowerCase().equals(m.getName().toLowerCase()) )
                    .collect(Collectors.toList()).get(0).invoke(object,value);
        }catch (Exception e){
            log.error(e);
        }
    }

    public static void setValueByField(Object o, Field field, Object values) {
        setValueByFieldName(o,field.getName(),values);
    }

    /**
     * 常用数据类型自动装配
     * @param method set方法
     * @param o 装配对象
     * @param value 值
     * @param types 类型
     */
    private static void autoInvoke(Method method, Object o, Object value, Class... types) throws Exception {
        if (null == types || types.length == 0) return;
        for (Class type : types) {
            switch (type.getName()){
                case "String":
                    method.invoke(o,valueOf(value)); break;
                case "java.lang.String":
                    method.invoke(o,valueOf(value)); break;
                case "int":
                    method.invoke(o,Integer.parseInt(valueOf(value))); break;
                case "java.lang.Integer":
                    method.invoke(o,Integer.valueOf(valueOf(value))); break;
                case "byte":
                    method.invoke(o,Byte.parseByte(valueOf(value))); break;
                case "java.lang.Byte":
                    method.invoke(o,Byte.valueOf(valueOf(value))); break;
                case "short":
                    method.invoke(o,Short.parseShort(valueOf(value))); break;
                case "java.lang.Short":
                    method.invoke(o,Short.valueOf(valueOf(value))); break;
                case "char":
                    method.invoke(o,valueOf(value).charAt(0)); break;
                case "java.lang.Character":
                    method.invoke(o,Character.valueOf(valueOf(value).charAt(0))); break;
                case "long":
                    method.invoke(o,Long.parseLong(valueOf(value))); break;
                case "java.lang.Long":
                    method.invoke(o,Long.valueOf(valueOf(value))); break;
                case "float":
                    method.invoke(o,Float.parseFloat(valueOf(value))); break;
                case "java.lang.Float":
                    method.invoke(o,Float.valueOf(valueOf(value))); break;
                case "double":
                    method.invoke(o,Double.parseDouble(valueOf(value))); break;
                case "java.lang.Double":
                    method.invoke(o,Double.valueOf(valueOf(value))); break;
                case "boolean":
                    method.invoke(o,Boolean.parseBoolean(valueOf(value))); break;
                case "java.lang.Boolean":
                    method.invoke(o,Boolean.valueOf(valueOf(value))); break;
            }
        }
    }

    private static String valueOf(Object value) {
        return String.valueOf(value);
    }




}
