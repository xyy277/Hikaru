package savvy.wit.framework.core.base.service.cdt.impl;

import savvy.wit.framework.core.base.service.cdt.Cdt;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.base.service.dao.Order;
import savvy.wit.framework.core.base.service.dao.Pagination;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.lang.reflect.Field;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : CdtImpl
 * Author : zhoujiajun
 * Date : 2018/7/7 22:47
 * Version : 1.0
 * Description : 
 ******************************/
public class CdtImpl implements Cdt {

    private static Log log = LogFactory.getLog();

    private StringBuilder sql = new StringBuilder();

    private boolean order = false;

    private static Cdt cdt;

    private Pagination pagination;

    public static Cdt init(){
        cdt = new CdtImpl();
        return cdt;
    }

    private CdtImpl(){}


    public String getCondition() {
        return " " + sql.toString();
    }

    @Override
    public boolean hasCondition() {
        return sql.length() > 1;
    }

    @Override
    public Pagination page() {
        return pagination;
    }

    @Override
    public Cdt where(String... var) {
        boolean like = false;
        if (var.length > 0)
            append("where");
        for (String param : var) {
            if (like) {
                param = "'" + like(param) + "'";
                like = !like;
            } else if ("like".equals(param)) {
                like = true;
            }
            append(param);
        }
        return this;
    }

    private String like(String param) {
        return "%" + param + "%";
    }

    @Override
    public Cdt where(String var1, String var2, Object var3) {
        if (sql.indexOf("where") == -1)
            append(" where");
        else
            append(" and");
        append(var1).append(var2);
        if (var3.getClass().getSimpleName().equals("String"))
            var3 = "'" + (var2.equals("like") ? like(var3.toString()) : var3.toString()) + "'";
        else
            var3 = (var2.equals("like") ? like(var3.toString()) : var3.toString());
        append(var3);
        return this;
    }

    @Override
    public Cdt page(Pagination pagination) {
        this.pagination = pagination;
        return this;
    }

    @Override
    public Cdt page(int pageIndex, int pageSize) {
        Pagination pagination = Pagination.NEW();
        pagination.setPageIndex(pageIndex);
        pagination.setPageSize(pageSize);
        return page(pagination);
    }

    @Override
    public Cdt where(String var1, String var2, Object var3, Cdt c) {
        if (c != null) {
            append(c.getCondition());
        }
        this.pagination = c.page();
        return and(var1, var2, var3);
    }

    @Override
    public Cdt and(String var1, String var2, Object var3) {
        if (sql.indexOf("where") == -1) {
            where(var1, var2, var3);
        }else {
            append(" and ").append(var1).append(var2);
            if (var3.getClass().getSimpleName().equals("String"))
                var3 = "'" + (var2.equals("like") ? like(var3.toString()) : var3.toString()) + "'";
            else
                var3 = (var2.equals("like") ? like(var3.toString()) : var3.toString());
            append(var3);
        }
        return this;
    }

    @Override
    public Cdt and(String var1, String var2, Object var3, Cdt cdt) {
        if (cdt != null) {
            append(cdt.getCondition());
        }
        this.pagination = cdt.page();
        return and(var1, var2, var3);
    }

    /**
     * SELECT * FROM USER WHERE NAME LIKE '%zx%' GROUP BY id ORDER BY age ASC, id DESC LIMIT 0, 20
     * @param var1
     * @param order
     * @return
     */
    @Override
    public Cdt order(Object var1, Order order) {
        if (var1 != null && order != null) {
            if (!this.order) {
                append(" order by ");
                this.order = true;
            } else {
                append(" , ");
            }
            if (var1 instanceof Field) {
                append(((Field) var1).getName());
            } else {
                append(var1.toString() + " ");
            }
            append(order.toString());
        }
        return this;
    }

    @Override
    public Cdt order(Order order, Object... vars) {
        if (order != null && vars != null && vars.length > 0){
            for (Object var : vars) {
                order(var, order);
            }
        }
        return this;
    }

    @Override
    public Cdt asc(String... columns) {
        if (columns != null && columns.length > 0) {
            if (sql.indexOf("order by") == -1) {
                append(" order by ");
            } else {
                append(" , ");
            }
            for (int i = 0; i < columns.length; i++) {
                append(columns[i] + " asc ");
                if (i < columns.length - 1) {
                    append(" , ");
                }
            }
        }
        return this;
    }

    @Override
    public Cdt desc(String... columns) {
        if (columns != null && columns.length > 0) {
            if (sql.indexOf("order by") == -1) {
                append(" order by ");
            } else {
                append(" , ");
            }
            for (int i = 0; i < columns.length; i++) {
                append(columns[i] + " desc ");
                if (i < columns.length - 1) {
                    append(" , ");
                }
            }
        }
        return this;
    }

    private CdtImpl append(Object var) {
        sql.append(var + " ");
        return this;
    }
}
