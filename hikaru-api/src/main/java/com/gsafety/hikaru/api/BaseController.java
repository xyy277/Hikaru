package com.gsafety.hikaru.api;

import com.gsafety.hikaru.common.global.Error;
import com.gsafety.hikaru.common.global.Result;
import com.gsafety.hikaru.model.system.User;
import org.springframework.web.bind.annotation.*;
import savvy.wit.framework.core.base.service.dao.Order;
import savvy.wit.framework.core.base.service.dao.Pagination;
import savvy.wit.framework.core.base.util.ObjectUtil;
import savvy.wit.framework.core.pattern.factory.CDT;
import savvy.wit.framework.core.pattern.factory.Daos;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : BaseController
 * Author : zhoujiajun
 * Date : 2019/3/5 21:34
 * Version : 1.0
 * Description : 
 ******************************/
@RestController
public class BaseController<T> {

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Result<Long> count() {
        Long count = 0l;
        try {
            count = Daos.get().count(getGenericSuperclass());
        } catch (SQLException e) {
            Result.error(new Error(500, e.getMessage()));
        }
        return Result.success(count);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<T> add(T t) {
        try {
            t = (T) Daos.acquire().insert(t);
        } catch (SQLException e) {
            Result.error(new Error(500, e.getMessage()));
        }
        return Result.success(t);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Result<Boolean> remove(T t) {
        Boolean status = false;
        try {
            status = Daos.get().delete(CDT.where("id", "=", ObjectUtil.getValueByFiledName(t, "id")), t.getClass());
        } catch (SQLException e) {
            Result.error(new Error(500, e.getMessage()));
        }
        return Result.success(status);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<Boolean> update(T t) {
        Boolean status = false;
        try {
            status = Daos.get().update(t);
        } catch (SQLException e) {
            Result.error(new Error(500, e.getMessage()));
        }
        return Result.success(status);
    }

    @RequestMapping(value = "/fetch/{id}", method = RequestMethod.GET)
    public Result<T> fetch(@PathVariable Object id) {
        T t = null;
        try {
            t = (T) Daos.acquire().fetch(CDT.where("id", "=", id), getGenericSuperclass());
        } catch (Exception e) {
            Result.error(new Error(500, e.getMessage()));
        }
        return Result.success(t);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<List<T>> query(@RequestBody Pagination pagination) {
        List<T> list = new ArrayList<>();
        try {
            list = Daos.acquire().query(CDT.page(pagination).order(pagination.getOrder(), pagination.getSorts()), getGenericSuperclass());
        } catch (SQLException e) {
            Result.error(new Error(500, e.getMessage()));
        }
        return Result.success(list);
    }
    /**
     * 获取子类泛型
     * @return
     */
    private Class<T> getGenericSuperclass() {
        Class<T> clazz = null;
        Type type = this.getClass().getGenericSuperclass();//拿到带类型参数的泛型父类
        if(type instanceof ParameterizedType){//这个Type对象根据泛型声明，就有可能是4中接口之一，如果它是BaseServiceImpl<User>这种形式
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();//获取泛型的类型参数数组
            if(actualTypeArguments != null && actualTypeArguments.length == 1){
                if(actualTypeArguments[0] instanceof Class){//类型参数也有可能不是Class类型
                    clazz = (Class<T>) actualTypeArguments[0];
                }
            }
        }
        return clazz;
    }
}
