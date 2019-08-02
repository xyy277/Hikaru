package savvy.wit.framework.core.pattern.builder;

import savvy.wit.framework.core.base.callback.ExcelDataCallBack;
import savvy.wit.framework.core.base.model.Excel;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : Excel构造接口
 * File name : ExcelBuilder
 * Author : zhoujiajun
 * Date : 2019/7/31 15:36
 * Version : 1.0
 * Description : 
 ******************************/
public interface ExcelBuilder {

    /**
     * 初始化sheet
     * @param sheetNum sheet总数
     * @return 建造者
     */
    ExcelBuilder initSheet(int sheetNum);

    /**
     * 初始化表
     * @param tableNum 数组
     *                 index - 对应 sheet页 下标
     *                 value - 对应 table 数量
     * @return 建造者
     */
    ExcelBuilder initTable(int... tableNum);

    /**
     * 组装数据
     * @param sheetNum sheet 页下标
     * @param tableNum table 表下标
     * @param data     数据
     * @return 建造者
     */
    ExcelBuilder packing(int sheetNum, int tableNum, Map<String, Object> data);

    /**
     * 组装数据
     * @param sheetNum sheet 页下标
     * @param tableNum table 表下标
     * @param data     数据
     * @param <T>      数据类型
     * @return
     */
    <T> ExcelBuilder packing(int sheetNum, int tableNum, List<T> data, ExcelDataCallBack<T>dataCallBack);

    /**
     * 创建Sheet页
     * @param sheetNum sheet 页下标
     * @param name     sheet 页名称
     * @param images   sheet 页中所有图片集
     * @return 建造者
     */
    ExcelBuilder createSheet(int sheetNum, String name, BufferedImage... images);

    /**
     * 创建table 表
     * @param sheetNum  sheet 页下标
     * @param tableNum  table 表下标
     * @param row       起始  号下标
     * @param cell      起始  列下标
     * @param title     table 表附表头
     * @return 建造者
     */
    ExcelBuilder createTable(int sheetNum, int tableNum, int row, int cell, String... title);

    /**
     * 构建
     * @return Excel
     */
    Excel builder();
}
