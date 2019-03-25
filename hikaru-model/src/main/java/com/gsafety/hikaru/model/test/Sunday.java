package com.gsafety.hikaru.model.test;

import com.gsafety.hikaru.model.enumerate.Test;
import com.gsafety.hikaru.model.enumerate.Ultraviolet;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.gsafety.hikaru.model.BaseModel;
import savvy.wit.framework.core.base.service.dao.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Sunday
 * Author : zhoujiajun
 * Date : 2019/2/19 9:16
 * Version : 1.0
 * Description : 
 ******************************/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sunday extends BaseModel {

    @Id
    @Column
    @Type(type = CType.VARCHAR, width = 32)
    @Comment("编号")
    @ApiModelProperty(value = "编号")
    private String id;

    @Column
    @Comment("气温")
    @Type(type = CType.FLOAT, width = 10)
    @NotNull
    @ApiModelProperty(value = "气温")
    private double temperature;

    @Column
    @Comment("紫外线强度")
    @Type(type = CType.INT, width = 2, vacancy = true)
    @ApiModelProperty(value = "紫外线强度")
    private Ultraviolet ultraviolet;

    @Column
    @Comment("测试属性")
    @Type(type = CType.INT, width = 2, vacancy = true)
    @ApiModelProperty(value = "测试属性")
    private Test test;

    @Column
    @Comment("测试属性1")
    @Type(type = CType.VARCHAR, width = 1024, vacancy = true)
    @ApiModelProperty(value = "测试属性1")
    private String[] test1;

    @Column
    @Comment("测试属性2")
    @Type(type = CType.VARCHAR, width = 1024, vacancy = true)
    @ApiModelProperty(value = "测试属性2")
    private List<String> test2;

    @Column
    @Comment("测试属性3")
    @Type(type = CType.VARCHAR, width = 1024, vacancy = true)
    @ApiModelProperty(value = "测试属性3")
    private Map<String, Monday> test3;

    @Column
    @Comment("monday id")
    @Type(type = CType.VARCHAR, width = 32, vacancy = true)
    @ApiModelProperty(value = "monday id")
    private String mondayId;

    private Monday monday;

    public synchronized Monday monday() {
        if (this.monday == null) {
            this.monday = new Monday();
        }
        return this.monday;
    }

}
