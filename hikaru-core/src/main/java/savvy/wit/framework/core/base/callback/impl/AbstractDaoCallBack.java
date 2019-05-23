package savvy.wit.framework.core.base.callback.impl;

import com.alibaba.fastjson.JSON;
import savvy.wit.framework.core.base.callback.DaoCallBack;
import savvy.wit.framework.core.base.service.dao.annotation.Column;
import savvy.wit.framework.core.base.service.dao.annotation.QueryLink;
import savvy.wit.framework.core.base.service.enumerate.impl.EnumConvertBase;
import savvy.wit.framework.core.base.util.ClassUtil;
import savvy.wit.framework.core.base.util.ObjectUtil;
import savvy.wit.framework.core.base.util.StringUtil;
import savvy.wit.framework.core.base.util.Strings;
import savvy.wit.framework.core.pattern.decorate.Counter;
import savvy.wit.framework.core.pattern.factory.ConfigFactory;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : AbstractDaoCallBack
 * Author : zhoujiajun
 * Date : 2019/5/22 9:18
 * Version : 1.0
 * Description : 
 ******************************/
public class AbstractDaoCallBack<T> implements DaoCallBack<T> {

    private static String intervalMark = "";
    private List<Class<?>> enumClassList = ConfigFactory.me().getEnumClassList();
    private Counter counter = Counter.create();

    @Override
    public T savvy(ResultSet resultSet) throws Exception {
        Class clazz = ClassUtil.me().getGenericSuperclass(this.getClass());
        T t = (T) clazz.newInstance();
        Map<String, List<String>> map = getParamTypeAndName(clazz);
        List<String> types = map.get("types");
        List<String> names = map.get("names");
        for (int var = 0; var < names.size(); var++) {
            Object value = null;
            try {
                value = getValueAdapter(resultSet, types.get(var), Strings.hump2Line(names.get(var)));
            } catch (Exception e) {
                LogFactory.getLog().log(e);
            }
            Object object = counter.getValue(names.get(var));
            // integer 转泛型
            if (null != object && value instanceof Integer) {
                value = EnumConvertBase.convert().value2Enum((Class) object, (Integer) value);
            }
            ObjectUtil.setValue(t, names.get(var), value);
        }
        return t;
    }

    private Map<String, List<String>> getParamTypeAndName(Class clazz) {
        Map<String, List<String>> map = new HashMap<>();
        List<String> types = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> links = new ArrayList<>();
        while (clazz != null && clazz != Object.class) {
            Arrays.asList(clazz.getDeclaredFields()).forEach(field -> {
                if (field.isAnnotationPresent(Column.class)) {
                    names.add(field.getName());
                    // 泛型转换
                    List<Class> classList = enumClassList.parallelStream()
                            .filter(aClass -> aClass.getSimpleName().equals(field.getType().getSimpleName()))
                            .collect(Collectors.toList());
                    if (classList.size() > 0) {
                        types.add("int");
                        counter.setValue(field.getName(), classList.get(0));
                    } else {
                        types.add(field.getType().getSimpleName());
                    }
                    // 关联是否为查询属性Object
                } else if (field.isAnnotationPresent(QueryLink.class)) {
                   QueryLink queryLink = field.getAnnotation(QueryLink.class);
                   // 关联类
                   Class linkClass = queryLink.value();
                   // 关联属性
                   String linkName = queryLink.name();
                }
            });
            clazz = clazz.getSuperclass();
        }
        map.put("types", types);
        map.put("names", names);
        map.put("links", links);
        return map;
    }

    private Object getValueAdapter(ResultSet resultSet, String type, String name) {
        intervalMark = StringUtil.isNotBlank(ConfigFactory.me().getProperty("intervalMark")) ? ConfigFactory.me().getProperty("intervalMark") : intervalMark;
        boolean primitive = true;
        String columnType = type;
        if ("Integer".equals(type)) {
            columnType = "int";
        } else if (type.indexOf("[]") != -1 || type.indexOf("List") != -1 || type.indexOf("Map") != -1) { // 数组或集合
            primitive = false;
            columnType = "String";
        }
        Object o = ObjectUtil.getValue(resultSet, columnType, name);
        if (!primitive) {
            if (type.indexOf("[]") != -1 && o.toString().indexOf(intervalMark) != -1) {
                return  o.toString().split(intervalMark);
            } else if (type.indexOf("List") != -1  && o.toString().indexOf(intervalMark) != -1) {
                return Arrays.asList(o.toString().split(intervalMark));
            } else if (type.indexOf("Map") != -1) {
                try {
                    Map map = (Map) JSON.parse(o.toString());
                    return map;
                } catch (ClassCastException e) {
                    Map map = new HashMap();
                    map.put(name, o.toString());
                    return map;
                }
            }
        }
        return o;
    }
}
