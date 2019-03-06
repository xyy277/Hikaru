package com.gsafety.hikaru.model.system.spider;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.gsafety.hikaru.model.BaseModel;
import savvy.wit.framework.core.base.service.dao.annotation.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : AcquisitionContent
 * Author : zhoujiajun
 * Date : 2019/3/1 16:24
 * Version : 1.0
 * Description : 
 ******************************/
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Content extends BaseModel {
    private static final long serialVersionUID = 1L;

    @Id
    @Column
    @Comment("编号")
    @Type(type = CType.VARCHAR)
    @ApiModelProperty(value = "编号", required = true)
    private String id;

    @Column
    @Comment("站点id")
    @Type(type = CType.VARCHAR, width = 32)
    @ApiModelProperty(value = "站点id", required = true)
    private String site_id;

    @Column
    @Comment("专题id")
    @Type(type = CType.VARCHAR, width = 32)
    @ApiModelProperty(value = "专题id", required = true)
    private String topic_id;

    @Column
    @Comment("栏目id")
    @Type(type = CType.VARCHAR, width = 32)
    @ApiModelProperty(value = "栏目id", required = true)
    private String channel_id;

    @Column
    @Comment("模型id")
    @Type(type = CType.VARCHAR, width = 32)
    @ApiModelProperty(value = "模型id", required = true)
    private String model_id;

    @Column
    @Comment("用户id")
    @Type(type = CType.VARCHAR, width = 32)
    @ApiModelProperty(value = "用户id", required = true)
    private String user_id;

    @Column
    @Comment("单位id")
    @Type(type = CType.VARCHAR, width = 32)
    @ApiModelProperty(value = "单位id", required = true)
    private String unit_id;

    @Column
    @Comment("审核状态 -1：回收站")
    @Type(type = CType.INT)
    @ApiModelProperty(value = "审核状态 -1：回收站", required = true)
    private Integer status;

    @Column
    @Comment("标题图片")
    @Type(type = CType.VARCHAR)
    private String has_title_img;

    @Column
    @Comment("排序时间")
    @Type(type = CType.VARCHAR, width = 50)
    private String sort_time;

    @Column
    @Comment("标题")
    @Type(type = CType.VARCHAR, width = 80)
    private String title;

    @Column
    @Comment("外部链接")
    @Type(type = CType.VARCHAR, width = 150)
    private String link_;

    @Column
    @Comment("短标题")
    @Type(type = CType.VARCHAR, width = 60)
    private String short_title;

    @Column
    @Comment("短标题颜色")
    @Type(type = CType.VARCHAR, width = 50)
    private String title_color;

    @Column
    @Comment("短标题是否加粗")
    @Type(type = CType.VARCHAR, width = 2)
    private String is_bold;

    @Column
    @Comment("发布者")
    @Type(type = CType.VARCHAR, width = 50)
    private String author;

    @Column
    @Comment("来源")
    @Type(type = CType.VARCHAR, width = 50)
    private String origin;

    @Column
    @Comment("来源URL")
    @Type(type = CType.VARCHAR, width = 150)
    private String origin_url;

    @Column
    @Comment("摘要")
    @Type(type = CType.VARCHAR)
    private String description;

    @Column
    @Comment("多媒体")
    @Type(type = CType.VARCHAR)
    private String media_path;

    @Column
    @Comment("播放器")
    @Type(type = CType.VARCHAR)
    private String media_type;

    @Column
    @Comment("固顶级别")
    @Type(type = CType.VARCHAR)
    private String top_level;

    @Column
    @Comment("新增时间")
    @Type(type = CType.VARCHAR)
    private String add_time;

    @Column
    @Comment("发布时间")
    @Type(type = CType.VARCHAR)
    private String pub_time;

    @Column
    @Comment("标题图片")
    @Type(type = CType.VARCHAR)
    private String title_img;

    @Column
    @Comment("")
    @Type(type = CType.VARCHAR)
    private String title_img_s;

    @Column
    @Comment("指定模板")
    @Type(type = CType.VARCHAR)
    private String tpl_content;
    
    @Column
    @Comment("发布状态")
    @Type(type = CType.INT)
    private Integer is_static;

    @Column
    @Comment("")
    @Type(type =CType.INT)
    private Integer is_index;

    private String filePath;//文件下载链接
    /**
     * 站点名称
     */
    private String site_name;
    /**
     * 栏目名称
     */
    private String channel_name;

    private String pub_time_date;//发布日期

    private String content_txt;//文章内容

    private String content_txt_cut;//精简后的文章内容



    @Column
    @Comment("招聘部门")
    @Type(type = CType.VARCHAR)
    private String department;

    @Column
    @Comment("招聘地区")
    @Type(type = CType.VARCHAR)
    private String area;
}
