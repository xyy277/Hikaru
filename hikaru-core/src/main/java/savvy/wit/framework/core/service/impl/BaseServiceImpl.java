package savvy.wit.framework.core.service.impl;

import savvy.wit.framework.core.base.callback.DaoCallBack;
import savvy.wit.framework.core.base.service.cdt.Cdt;
import savvy.wit.framework.core.base.service.dao.Dao;
import savvy.wit.framework.core.base.util.ClassUtil;
import savvy.wit.framework.core.pattern.factory.Daos;
import savvy.wit.framework.core.service.BaseService;
import savvy.wit.framework.core.service.Service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 接口实现基类
 * File name : BaseServiceImpl
 * Author : zhoujiajun
 * Date : 2018/7/7 22:54
 * Version : 1.0
 * Description : alt + shift + p 快速实现接口方法
 ******************************/
public class BaseServiceImpl<T> extends Service implements BaseService<T> {


    public BaseServiceImpl(Dao dao) {
        super(dao == null ? Daos.get() : dao);
    }

    public BaseServiceImpl () {
    }

    @Override
    public Dao dao() {
        return this.getDao();
    }

    @Override
    public boolean delete(Cdt cdt) {
        boolean status = false;
        try {
            status = dao().delete(cdt, getGenericSuperclass());
        }catch (Exception e){
            throw new RuntimeException(e);
        } finally {
            return status;
        }
    }

    @Override
    public boolean update(T t) {
        boolean status = false;
        try {
            status = dao().update(t);
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            return status;
        }
    }

    @Override
    public boolean update(T t, Cdt cdt) {
        boolean status = false;
        try {
            status = dao().update(t, cdt);
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            return status;
        }
    }

    @Override
    public T insert(T t) {
        T o = null;
        try {
            o =  (T) dao().insert(t);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return o;
    }

    @Override
    public int insertBath(List<T> list) {
        int num = 0;
        if (list.size() != 0) {
            try {
                num = dao().insertBath(list, list.get(0).getClass());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return num;
    }

    @Override
    public T fetch(Cdt cdt) {
        T t = null;
        try {
            t = (T) dao().fetch(cdt, getGenericSuperclass());
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return t;
    }

    @Override
    public List<T> query(Cdt cdt) {
        List<T> list = new ArrayList<>();
        try {
            list = dao().query(cdt, getGenericSuperclass());
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<T> query(Cdt cdt, DaoCallBack<T> callBack) {
        List<T> list = new ArrayList<>();
        try {
            list = dao().query(cdt, getGenericSuperclass(), callBack);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<T> query() {
        List<T> list = new ArrayList<>();
        try {
            list = dao().query(null, getGenericSuperclass());
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public long count() {
        long count;
        try {
            count = dao().count(getGenericSuperclass());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    @Override
    public long count(Cdt cdt) {
        long count;
        try {
            count = dao().count(getGenericSuperclass(), cdt);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    /**
     * 获取子类泛型
     * @return
     */
    private Class<T> getGenericSuperclass() {
        return ClassUtil.me().getGenericSuperclass(this.getClass());
    }

}
