package savvy.wit.framework.core.pattern.proxy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : SqlProxy
 * Author : zhoujiajun
 * Date : 2019/3/22 10:43
 * Version : 1.0
 * Description : 
 ******************************/
public class SqlBuilder implements SqlProxy {

    private boolean generate;

    private String sql;

    private Map<String, String> param = new HashMap<>();

    protected SqlBuilder() {}

    /**
     * 请求sql代理，返回代理单例对象
     * @return
     */
    public static SqlProxy ask() {
        return LazyInit.INITIALIZATION;
    }

    private static class LazyInit {
        private static final SqlProxy INITIALIZATION = new SqlBuilder();
    }

    /**
     * 代理sql
     * @param sql 请求被代理的sql数组
     */
    public SqlProxy proxy(String sql) {
        this.sql = sql;
        return LazyInit.INITIALIZATION;
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public SqlProxy param(String key, Object value) {
        StringBuilder builder = new StringBuilder();
        if (value.getClass().isArray() || value instanceof List) {
            builder.append(" ( ");
            Object[] objects = null;
            if (value.getClass().isArray())
                objects = (Object[]) value;
            else
                objects = ((List)value).toArray();
            for (int var = 0; var < objects.length; var++) {
                if (objects[var] instanceof String) {
                    builder.append("'")
                            .append(objects[var].toString())
                            .append("'");
                } else
                    builder.append(objects[var].toString());
                if (var < objects.length -1)
                    builder.append(", ");
            }
            builder.append(" ) ");
        }
        param.put(key, builder.toString());

        return LazyInit.INITIALIZATION;
    }

    /**
     * 内部解析sql：
     *  param --》 sql
     * 1、设置动态参数
     * 2、防止sql注入
     */
    private void parsing() {

    }

    /**
     * 响应代理获取sql
     * @return
     */
    public String request() {
        parsing();
        return this.sql;
    }
}
