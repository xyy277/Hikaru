package savvy.wit.framework.core.base.interfaces.impl;

import savvy.wit.framework.core.base.interfaces.Cdt;
import savvy.wit.framework.core.base.interfaces.Log;
import savvy.wit.framework.core.pattern.factory.LogFactory;

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

    private static Cdt cdt;

    public static Cdt init(){
        cdt = new CdtImpl();
        return cdt;
    }

    private CdtImpl(){}


    public String getCondition() {
        return " " + sql.toString();
    }

    @Override
    public Cdt where(String... var) {
        append("where");
        for (String param : var) {
            append(param);
        }
        return cdt;
    }

    @Override
    public Cdt where(String var1, String var2, Object var3) {
        append(" where").append(var1).append(var2);
        if (var3.getClass().getSimpleName().equals("String"))
            append("'" + var3 + "'");
        else
            append(var3);
        return cdt;
    }

    @Override
    public Cdt where(String var1, String var2, Object var3, Cdt cdt) {
        return cdt;
    }

    @Override
    public Cdt and(String var1, String var2, Object var3) {
        return cdt;
    }

    @Override
    public Cdt and(String var1, String var2, Object var3, Cdt cdt) {
        return cdt;
    }

    @Override
    public Cdt orderBy(Object var3) {
        return cdt;
    }

    private CdtImpl append(Object var) {
        sql.append(var + " ");
        return this;
    }
}
