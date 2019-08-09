package savvy.wit.framework.core.base.callback;

import java.util.List;
import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ExcelDataCallBack
 * Author : zhoujiajun
 * Date : 2019/7/26 10:31
 * Version : 1.0
 * Description : 
 ******************************/
public interface ExcelDataCallBack<T> {


    String SIGN_K_V = "-";

    /**
     * 获取一行中所有列的数据
     * 一列数据为某个对象的所有属性值或可自定义属性
     * @param sheetNum  sheet number - sheet 下标，一个excel 多个sheet
     * @param tableNum  table number - table 下标，一个sheet 多个table
     * @param data      对象集合
     * @param clazz     对象类型
     * @param result    数据
     * @return 一张表中所有数据
     */
    Map<String, Object> getData(int sheetNum, int tableNum, List<T> data, Class<T> clazz, Map<String, Object> result);
}
