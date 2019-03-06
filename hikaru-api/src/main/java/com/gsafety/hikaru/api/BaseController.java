package com.gsafety.hikaru.api;

import com.gsafety.hikaru.common.global.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import savvy.wit.framework.core.pattern.factory.Daos;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;

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
            e.printStackTrace();
        }
        return Result.success(count);
    }

    /**
     * 获取子类泛型
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
