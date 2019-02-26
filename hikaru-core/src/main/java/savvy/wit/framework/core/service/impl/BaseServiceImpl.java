package savvy.wit.framework.core.service.impl;

import savvy.wit.framework.core.base.callback.DaoCallBack;
import savvy.wit.framework.core.base.service.cdt.Cdt;
import savvy.wit.framework.core.base.service.dao.Dao;
import savvy.wit.framework.core.service.BaseService;
import savvy.wit.framework.core.service.Service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
        super(dao);
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
        log.log(getGenericSuperclass());
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
        log.log(getGenericSuperclass());
        try {
            list = dao().query(null, getGenericSuperclass());
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * 获取泛型
     * @return
     */
    private Class<T> getGenericSuperclass() {
        Type type = this.getClass().getGenericSuperclass();//拿到带类型参数的泛型父类
        if(type instanceof ParameterizedType){//这个Type对象根据泛型声明，就有可能是4中接口之一，如果它是BaseServiceImpl<User>这种形式
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();//获取泛型的类型参数数组
            if(actualTypeArguments != null && actualTypeArguments.length == 1){
                if(actualTypeArguments[0] instanceof Class){//类型参数也有可能不是Class类型
                    return (Class<T>) actualTypeArguments[0];
                }
            }
        }
        return null;
    }

}
