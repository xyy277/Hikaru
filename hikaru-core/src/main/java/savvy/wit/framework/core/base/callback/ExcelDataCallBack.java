package savvy.wit.framework.core.base.callback;

import org.apache.poi.ss.usermodel.Row;

import java.util.List;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ExcelDataCallBack
 * Author : zhoujiajun
 * Date : 2019/7/26 10:31
 * Version : 1.0
 * Description : 
 ******************************/
public interface ExcelDataCallBack {


    /**
     * 获取一行中所有列的数据
     * 一列数据为某个对象的所有属性值或可自定义属性
     * @param sheetNum  sheet number - sheet 下标，一个excel 多个sheet
     * @param tableNum  table number - table 下标，一个sheet 多个table
     * @param row       行
     * @param object    对象
     * @param values    返回值
     * @return
     */
    List<Object> getValues(int sheetNum, int tableNum, Row row, Object object, List<Object> values);
}
