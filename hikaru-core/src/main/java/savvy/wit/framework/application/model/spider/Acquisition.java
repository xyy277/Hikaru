package savvy.wit.framework.application.model.spider;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import savvy.wit.framework.application.model.BaseModel;
import savvy.wit.framework.core.base.service.dao.annotation.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Acquirement
 * Author : zhoujiajun
 * Date : 2019/2/21 17:20
 * Version : 1.0
 * Description : 
 ******************************/
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Acquisition extends BaseModel {

    @Id
    @Column
    @Comment("编号")
    @Type(type = CType.VARCHAR)
    @ApiModelProperty(value = "编号", required = true)
    private String id;

    @Column
    @Comment("站点id")
    @Type(type = CType.VARCHAR, width = 32)
    private String site_id;
    @Column
    @Comment("栏目id")
    @Type(type = CType.VARCHAR, width = 32)
    private String channel_id;

    @Column
    @Comment("模型id")
    @Type(type = CType.VARCHAR, width = 32)
    private String model_id;

    @Column
    @Comment("用户id")
    @Type(type = CType.VARCHAR, width = 32)
    private String user_id;

    @Column
    @Comment("采集名称")
    @Type(type = CType.VARCHAR, width = 150)
    private String acq_name;

    @Column
    @Comment("开始时间")
    @Type(type = CType.VARCHAR, width = 32)
    private String start_time;

    @Column
    @Comment("结束时间")
    @Type(type = CType.VARCHAR, width = 32)
    private String end_time;

    @Column
    @Comment("采集状态")
    @Type(type = CType.INT, width = 32)
    private Integer status;

    @Column
    @Comment("")
    @Type(type = CType.INT, width = 32)
    private Integer curr_num;

    @Column
    @Comment("")
    @Type(type = CType.INT, width = 32)
    private Integer curr_item;

    @Column
    @Comment("")
    @Type(type = CType.INT, width = 32)
    private Integer total_item;

    @Column
    @Comment("暂停时间：单位(毫秒)")
    @Type(type = CType.INT, width = 32)
    private Integer pause_time;

    @Column
    @Comment("页面编码")
    @Type(type = CType.VARCHAR, width = 10)
    private String page_encoding;

    @Column
    @Comment("采集地址")
    @Type(type = CType.VARCHAR)
    private String plan_list;

    @Column
    @Comment("动态地址")
    @Type(type = CType.VARCHAR)
    private String dynamic_addr;

    @Column
    @Comment("动态地址页码开始")
    @Type(type = CType.INT, width = 32)
    private Integer dynamic_start;

    @Column
    @Comment("动态地址页码结束")
    @Type(type = CType.INT, width = 32)
    private Integer dynamic_end;

    @Column
    @Comment("内容地址集开始HTML")
    @Type(type = CType.VARCHAR)
    private String linkset_start;
    @Column
    @Comment("内容地址集结束HTML")
    @Type(type = CType.VARCHAR)
    private String linkset_end;
    @Column
    @Comment("内容地址开始HTML")
    @Type(type = CType.VARCHAR)
    private String link_start;
    @Column
    @Comment("内容地址结束HTML")
    @Type(type = CType.VARCHAR)
    private String link_end;
    @Column
    @Comment("标题开始HTML")
    @Type(type = CType.VARCHAR)
    private String title_start;
    @Column
    @Comment("标题结束HTML")
    @Type(type = CType.VARCHAR)
    private String title_end;
    @Column
    @Comment("描述开始HTML")
    @Type(type = CType.VARCHAR)
    private String description_start;
    @Column
    @Comment("描述结束HTML")
    @Type(type = CType.VARCHAR)
    private String description_end;
    @Column
    @Comment("内容开始HTML")
    @Type(type = CType.VARCHAR)
    private String content_start;
    @Column
    @Comment("内容结束HTML")
    @Type(type = CType.VARCHAR)
    private String content_end;
    @Column
    @Comment("内容地址补全url")
    @Type(type = CType.VARCHAR)
    private String content_prefix;
    @Column
    @Comment("图片地址补全url")
    @Type(type = CType.VARCHAR)
    private String img_prefix;
    @Column
    @Comment("浏览量开始HTML")
    @Type(type = CType.VARCHAR)
    private String view_start;
    @Column
    @Comment("浏览量结束HTML")
    @Type(type = CType.VARCHAR)
    private String view_end;
    @Column
    @Comment("浏览量访问地址开始HTML")
    @Type(type = CType.VARCHAR)
    private String view_id_start;
    @Column
    @Comment("浏览量访问地址结束HTML")
    @Type(type = CType.VARCHAR)
    private String view_id_end;
    @Column
    @Comment("")
    @Type(type = CType.VARCHAR)
    private String view_link;
    @Column
    @Comment("发布时间开始HTML")
    @Type(type = CType.VARCHAR)
    private String releasetime_start;
    @Column
    @Comment("发布时间结束HTML")
    @Type(type = CType.VARCHAR)
    private String releasetime_end;
    @Column
    @Comment("作者开始HTML")
    @Type(type = CType.VARCHAR)
    private String author_start;
    @Column
    @Comment("作者结束HTML")
    @Type(type = CType.VARCHAR)
    private String author_end;
    @Column
    @Comment("来源开始HTML")
    @Type(type = CType.VARCHAR)
    private String origin_start;
    @Column
    @Comment("来源结束HTML")
    @Type(type = CType.VARCHAR)
    private String origin_end;
    @Column
    @Comment("发布时间格式")
    @Type(type = CType.VARCHAR)
    private String releasetime_format;
}
