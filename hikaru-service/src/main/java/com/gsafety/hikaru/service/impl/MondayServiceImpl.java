package com.gsafety.hikaru.service.impl;

import com.gsafety.hikaru.model.test.Monday;
import com.gsafety.hikaru.service.MondayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import savvy.wit.framework.core.base.service.dao.Dao;
import savvy.wit.framework.core.service.impl.BaseServiceImpl;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : MondayServiceImpl
 * Author : zhoujiajun
 * Date : 2019/2/25 16:07
 * Version : 1.0
 * Description : 
 ******************************/
@Service
public class MondayServiceImpl extends BaseServiceImpl<Monday> implements MondayService {

    @Autowired
    private MondayServiceImpl(Dao dao) {
        super(dao);
    }
}
