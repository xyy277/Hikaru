package savvy.wit.framework.core.pattern.builder;

import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.factory.LogFactory;
import savvy.wit.framework.core.pattern.proxy.SqlProxy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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

    private Log log = LogFactory.getLog();

    private boolean generate;

    private String sql;

    private static String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
            + "(\\b(select|update|union|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";

    private static Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);

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
                Object param = objects[var];
                // 防止注入
                if (injectDefender(param.toString())) {
                    throw new RuntimeException("inject sql warning:\t" + param.toString());
                }
                if (param instanceof String) {
                    builder.append("'")
                            .append(param.toString())
                            .append("'");
                } else
                    builder.append(param.toString());
                if (var < objects.length -1)
                    builder.append(", ");
            }
            builder.append(" ) ");
        } else {

            // 防止注入
            if (injectDefender(value.toString())) {
                throw new RuntimeException("inject sql warning:\t"+ value.toString());
            }
            if (value instanceof String) {
                builder.append(" '")
                        .append(value.toString())
                        .append("' ");
            } else
                builder.append(value.toString());
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
        if (isValid(this.sql)) {
            log.log(sql);
        } else
            throw new RuntimeException("SQL inject warning");
    }

    private boolean isValid(String str) {
        if (sqlPattern.matcher(str).find()) {
            return true;
        }
        return false;
    }

    /**
     * 参数化防止注入
     * @param val
     * @return
     */
    private boolean injectDefender(String val) {
        return isValid(val);
    }

    /**
     * 响应代理获取sql
     * @return
     */
    public String request() {
        param.forEach((s, s2) -> {
            String key = "${" + s + "}";
            if (this.sql.indexOf(key) != -1) {
                this.sql = this.sql.replace(key, s2);
            }
        });
//        parsing();
        return this.sql;
    }
}
