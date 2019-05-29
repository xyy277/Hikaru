package com.gsafety.hikaru.model.enumerate;

import savvy.wit.framework.core.base.service.enumerate.EnumValue;
import savvy.wit.framework.core.base.service.enumerate.EnumValueContract;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Test
 * Author : zhoujiajun
 * Date : 2019/2/26 14:43
 * Version : 1.0
 * Description : 
 ******************************/
@EnumValue
public enum Test implements EnumValueContract<Test> {

    OK(1),

    NO(2);

    private int paramType;

    Test(int paramType) {
        this.paramType = paramType;
    }

    @Override
    public int getParamType() {
        return this.paramType;
    }
}
