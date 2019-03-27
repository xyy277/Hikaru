package com.gsafety.hikaru.api;

import com.gsafety.hikaru.common.global.Error;
import com.gsafety.hikaru.common.global.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import savvy.wit.framework.core.base.service.cdt.Cdt;
import savvy.wit.framework.core.base.service.dao.Pagination;
import savvy.wit.framework.core.base.util.ClassUtil;
import savvy.wit.framework.core.base.util.DateUtil;
import savvy.wit.framework.core.base.util.ObjectUtil;
import savvy.wit.framework.core.pattern.factory.CDT;
import savvy.wit.framework.core.pattern.factory.Daos;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : CRUD
 * File name : BaseController
 * Author : zhoujiajun
 * Date : 2019/3/5 21:34
 * Version : 1.0
 * Description : 不完全通用，若有特定业务需要自定义，能解决基本CRUD
 * Model中参数基本类型需要使用包装类型
 * baseModel中有pagination作为分页条件
 * 查询条件都用model封装
 * 路径遵从
 * “/”              - GET
 * “/count”         - GET
 * “/add”           - POST
 * “/remove/{id}”   - GET
 * “/update”        - POST
 * “/fetch/{id}”    - GET
 ******************************/
@RestController
public class BaseController<T, PK> {

    @RequestMapping(value = "/count", method = RequestMethod.POST)
    public Result<Long> count(@RequestBody T t) {
        Long count = 0l;
        try {
            Cdt cdt = CDT.NEW();
            if (t != null) {
                Arrays.asList(t.getClass().getDeclaredFields()).forEach(field -> {
                    Object value = ObjectUtil.getValueByFiled(t, field);
                    if (value != null) {
                        cdt.and(field.getName(), "String".equals(field.getType().getSimpleName()) ? "like" : "=", value);
                    }
                });
            }
            count = Daos.get().count(getGenericSuperclass(), cdt);
        } catch (SQLException e) {
            Result.error(new Error(500, e.getMessage()));
        }
        return Result.success(count);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<T> add(@Validated @RequestBody T t) {
        try {
            if (t != null) {
                ObjectUtil.setValueByFieldName(t, "optTime", DateUtil.getNow());
            }
            t = (T) Daos.acquire().insert(t);
        } catch (SQLException e) {
            Result.error(new Error(500, e.getMessage()));
        }
        return Result.success(t);
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public Result<Boolean> remove(@PathVariable PK id) {
        Boolean status = false;
        try {
            status = Daos.get().delete(CDT.where("id", "=", id), getGenericSuperclass());
        } catch (SQLException e) {
            Result.error(new Error(500, e.getMessage()));
        }
        return Result.success(status);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<Boolean> update(@RequestBody T t) {
        Boolean status = false;
        try {
            if (t != null) {
                ObjectUtil.setValueByFieldName(t, "optTime", DateUtil.getNow());
            }
            status = Daos.get().update(t);
        } catch (SQLException e) {
            Result.error(new Error(500, e.getMessage()));
        }
        return Result.success(status);
    }

    @RequestMapping(value = "/fetch/{id}", method = RequestMethod.GET)
    public Result<T> fetch(@PathVariable PK id) {
        T t = null;
        try {
            t = (T) Daos.acquire().fetch(CDT.where("id", "=", id), getGenericSuperclass());
        } catch (Exception e) {
            Result.error(new Error(500, e.getMessage()));
        }
        return Result.success(t);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result<List<T>> query(@RequestBody T t) {
        List<T> list = new ArrayList<>();
        Cdt cdt = CDT.NEW();
        try {
            if (t != null) {
                if (t instanceof LinkedHashMap && !(((LinkedHashMap) t).size() > 0)) {
                    return Result.error(500, "NoClassFoundError");
                }
                Arrays.asList(t.getClass().getDeclaredFields()).forEach(field -> {
                    Object value = ObjectUtil.getValueByFiled(t, field);
                    if (value != null) {
                        cdt.and(field.getName(), "String".equals(field.getType().getSimpleName()) ? "like" : "=", value);
                    }
                });
                Object value = ObjectUtil.getValueByFiledName(t, "pagination");
                if (value != null && value instanceof Pagination) {
                    Pagination pagination = (Pagination) value;
                    cdt.page(pagination).order(pagination.getOrder(), pagination.getSorts());
                }
            }
            list = Daos.acquire().query(cdt, getGenericSuperclass());
        } catch (SQLException e) {
            Result.error(new Error(500, e.getMessage()));
        }
        return Result.success(list);
    }
    /**
     * 获取子类泛型T
     * @return
     */
    private Class<T> getGenericSuperclass() {
        return ClassUtil.me().getGenericSuperclass(this.getClass());
    }
}
