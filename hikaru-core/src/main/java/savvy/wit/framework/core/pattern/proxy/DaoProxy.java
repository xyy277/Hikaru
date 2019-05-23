package savvy.wit.framework.core.pattern.proxy;

import savvy.wit.framework.core.base.callback.DaoCallBack;
import savvy.wit.framework.core.base.service.dao.Dao;

import java.sql.Connection;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : DaoProxy
 * Author : zhoujiajun
 * Date : 2019/3/22 14:47
 * Version : 1.0
 * Description : 
 ******************************/
public interface DaoProxy<T> extends Dao<T> {

    void execute(Connection connection, String sql);

    void execute(Connection connection, String sql, DaoCallBack callBack);



}
