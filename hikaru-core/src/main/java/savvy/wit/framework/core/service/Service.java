package savvy.wit.framework.core.service;

import savvy.wit.framework.core.base.interfaces.Log;
import savvy.wit.framework.core.base.interfaces.dao.Dao;
import savvy.wit.framework.core.pattern.factory.LogFactory;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Service
 * Author : zhoujiajun
 * Date : 2018/7/7 23:07
 * Version : 1.0
 * Description : 
 ******************************/
public abstract class Service {
    protected Log log = LogFactory.getLog();

    private Dao dao;

    public Service() {

    }

    public Service(Dao dao) {
        this.dao = dao;
    }

    public Dao getDao() {
        return dao;
    }

    public void setDao(Dao dao) {
        this.dao = dao;
    }
}
