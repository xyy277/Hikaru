package savvy.wit.framework.core.base.service.impl;

import savvy.wit.framework.core.base.service.Cdt;
import savvy.wit.framework.core.base.service.Log;
import savvy.wit.framework.core.base.service.Order;
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
    public Pagination page() {
        return pagination;
    }

    @Override
    public Cdt where(String... var) {
        boolean like = false;
        append("where");
        for (String param : var) {
            if (like) {
                param = like(param);
            }else if ("like".equals(param)) {
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
        append(" where").append(var1).append(var2);
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
     * @param var3
     * @param order
     * @return
     */
    @Override
    public Cdt order(Object var3, Order order) {
        if (!this.order) {
            append(" order by ");
            this.order = true;
        } else {
            append(" , ");
        }
        if (var3 instanceof Field) {
            append(((Field) var3).getName());
        } else {
            append(var3.toString() + " ");
        }
        append(order.toString());
        return this;
    }

    @Override
    public Cdt asc(String... columns) {
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
        return this;
    }

    @Override
    public Cdt desc(String... columns) {
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
        return this;
    }

    private CdtImpl append(Object var) {
        sql.append(var + " ");
        return this;
    }
}
