package com.gsafety.hikaru.model.system.spider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.gsafety.hikaru.model.BaseModel;
import savvy.wit.framework.core.base.service.dao.annotation.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ContentText
 * Author : zhoujiajun
 * Date : 2019/3/1 16:32
 * Version : 1.0
 * Description : 
 ******************************/
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentText extends BaseModel {

    @Column
    @Comment("内容id")
    @Type(type = CType.VARCHAR, width = 32)
    private String content_id;
    @Column
    @Comment("内容")
    @Type(type = CType.TEXT, vacancy = true)
    private String txt;
}
