package com.gsafety.hikaru.common.enumerate.business;

import savvy.wit.framework.core.base.service.enumerate.EnumValueContract;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 紫外线等级
 * File name : Ultraviolet
 * Author : zhoujiajun
 * Date : 2019/2/19 9:24
 * Version : 1.0
 * Description : 描述紫外线强度
 ******************************/
public enum Ultraviolet implements EnumValueContract<Ultraviolet> {

    // 强
    Powerful(1),

    // 普通
    Normal(2),

    // 弱
    Feeble(3);

    private int paramType;

    Ultraviolet(int paramType) {
        this.paramType = paramType;
    }

    @Override
    public int getParamType() {
        return this.paramType;
    }

}
