package com.gsafety.hikaru.service.impl;

import com.gsafety.hikaru.model.test.Sunday;
import com.gsafety.hikaru.service.SundayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import savvy.wit.framework.core.base.service.dao.Dao;
import savvy.wit.framework.core.service.impl.BaseServiceImpl;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : SundayServiceImpl
 * Author : zhoujiajun
 * Date : 2019/2/25 9:22
 * Version : 1.0
 * Description : 
 ******************************/
@Service
public class SundayServiceImpl extends BaseServiceImpl<Sunday> implements SundayService {

    @Autowired
    private SundayServiceImpl (Dao dao) {
        super(dao);
    }


}
