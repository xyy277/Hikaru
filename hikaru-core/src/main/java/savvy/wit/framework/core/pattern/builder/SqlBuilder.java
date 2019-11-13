package savvy.wit.framework.core.pattern.builder;

import savvy.wit.framework.core.base.cache.SQLCache;
import savvy.wit.framework.core.base.callback.LogicCallBack;
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

    private SQLCache cache;

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
        synchronized (SqlBuilder.class) {
            this.sql = sql;
            return LazyInit.INITIALIZATION;
        }
    }

    /**
     * 参数化注入
     * @param placeholder
     * @param value
     * @return
     */
    public SqlProxy param(String placeholder, Object value) {
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

        param.put(placeholder, builder.toString());
        return LazyInit.INITIALIZATION;
    }

    @Override
    public SqlProxy param(String placeholder, Object value, boolean condition) {
        if (condition)
           return  param(placeholder, value);
        return LazyInit.INITIALIZATION;
    }

    @Override
    public SqlProxy param(String placeholder, Object value, LogicCallBack callBack) {
        return param(placeholder, value, callBack.fun());
    }

    /**
     * 拼接
     * @param placeholder 占位符
     * @param sql
     * @return
     */
    public SqlProxy joint(String placeholder, String sql) {
        if (Provider.inject) {
            param.put(placeholder, sql);
        } else {
            param.remove(placeholder);
        }
        return LazyInit.INITIALIZATION;
    }

    /**
     * sql解析
     * @param sql
     * @return
     */
    private String parsing(String sql) {
        StringBuilder result = new StringBuilder();
        char[] chars = sql.toCharArray();
        boolean start = false;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '$') {
                if (i < chars.length - 1 &&  chars[i+1] == '{') {
                    start = true;
                }
            }
            if (start) {
                if (c == '}') {
                    start = false;
                }
                continue;
            }
            result.append(c);
        }
        return result.toString();
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
        try {
            param.forEach((s, s2) -> {
                String key = "${" + s + "}";
                if (this.sql.indexOf(key) != -1) {
                    this.sql = this.sql.replace(key, s2);
                }
            });
            this.sql = parsing(this.sql);
            return this.sql;
        } finally {
            Provider.close();
            clean();
        }
    }

    /**
     * 代理结束，data recover
     */
    private void clean() {
        this.sql = "";
        param = new HashMap<>();
    }

    /**
     * 开启注入
     *
     * @return
     */
    public SqlProxy inject() {
        Provider.open();
        return LazyInit.INITIALIZATION;
    }

    private static class Provider {

        private static boolean inject = false;

        private static void open() {
            inject = true;
        }

        private static void close() {
            inject = false;
        }
    }
}
