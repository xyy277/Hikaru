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
public interface ExcelDataCallBack<T> {


    /**
     * 获取一行中所有列的数据
     * 一列数据为某个对象的所有属性值或可自定义属性
     * @param num sheet number
     * @param row 行
     * @param t   对象
     * @param values 返回值
     * @return
     */
    List<Object> getValues(int num, Row row, T t, List<Object> values);
}
