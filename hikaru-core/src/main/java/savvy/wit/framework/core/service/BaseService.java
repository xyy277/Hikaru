package savvy.wit.framework.core.service;/**
 * Created by zhoujiajun on 2018/6/28.
 */

import savvy.wit.framework.core.base.callback.DaoCallBack;
import savvy.wit.framework.core.base.service.cdt.Cdt;
import savvy.wit.framework.core.base.service.dao.Dao;

import java.util.List;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title :
 * File name : Sql
 * Author : zhoujiajun
 * Date : 2018/6/28 11:11
 * Version : 1.0
 * Description :
 ******************************/
public interface BaseService<T> {

    Dao dao();

    T fetch(Cdt cdt);

    boolean delete(Cdt cdt);

    boolean update(T t);

    boolean update(T t, Cdt cdt);

    T insert(T t);

    int insertBath(List<T> list);

    List<T> query(Cdt cdt);

    List<T> query(Cdt cdt, DaoCallBack<T> callBack);

    List<T> query();

    long count();

    long count(Cdt cdt);
}
