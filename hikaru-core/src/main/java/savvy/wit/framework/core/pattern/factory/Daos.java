package savvy.wit.framework.core.pattern.factory;


import savvy.wit.framework.core.base.service.dao.Dao;
import savvy.wit.framework.core.base.service.dao.impl.msql.DaoExcutor;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Daos
 * Author : zhoujiajun
 * Date : 2018/10/16 11:11
 * Version : 1.0
 * Description :
 ******************************/
public class Daos {

    public static Dao get() {
        return DaoExcutor.init();
    }

    public static Dao acquire() {
        return DaoExcutor.NEW();
    }

}
