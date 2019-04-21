package com.gsafety.hikaru.service.impl;

import com.gsafety.hikaru.model.test.Sunday;
import com.gsafety.hikaru.service.SundayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import savvy.wit.framework.core.base.service.dao.Dao;
import savvy.wit.framework.core.service.impl.BaseServiceImpl;

import java.util.List;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : SundayServiceImpl
 * Author : zhoujiajun
 * Date : 2019/4/1 10:48
 * Version : 1.0
 * Description : 
 ******************************/
@Service
public class SundayServiceImpl extends BaseServiceImpl<Sunday> implements SundayService {

    @Autowired
    private SundayServiceImpl(Dao dao) {
        super(dao);
    }

    @Override
    public List<Sunday> select(String[] var1, String var2, String var3) {
        return null;
    }
}
