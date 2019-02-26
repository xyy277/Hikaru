package savvy.wit.framework.application.model.log;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import savvy.wit.framework.application.model.BaseModel;
import savvy.wit.framework.core.base.service.dao.annotation.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Log
 * Author : zhoujiajun
 * Date : 2019/2/25 11:19
 * Version : 1.0
 * Description : 
 ******************************/
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log extends BaseModel {
    @Id
    @Column
    @Comment("编号")
    @Type(type = CType.VARCHAR, width = 32)
    @ApiModelProperty(value = "编号", required = true)
    private String id;

    @Column
    @Comment("操作事件")
    @Type(type = CType.VARCHAR, width = 512)
    @ApiModelProperty(value = "操作事件", required = true)
    private String event;

    @Column
    @Comment("报错信息")
    @Type(type = CType.VARCHAR, vacancy = true)
    @ApiModelProperty(value = "报错信息", required = true)
    private String message;


}
