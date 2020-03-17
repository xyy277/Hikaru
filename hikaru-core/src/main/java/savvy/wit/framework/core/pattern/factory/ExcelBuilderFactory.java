package savvy.wit.framework.core.pattern.factory;

import savvy.wit.framework.core.base.callback.ExcelDataCallBack;
import savvy.wit.framework.core.base.model.Excel;
import savvy.wit.framework.core.base.model.Sheet;
import savvy.wit.framework.core.base.model.Table;
import savvy.wit.framework.core.base.util.ObjectUtil;
import savvy.wit.framework.core.pattern.builder.ExcelBuilder;

import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ExcelBuilderFactory
 * Author : zhoujiajun
 * Date : 2019/7/31 15:45
 * Version : 1.0
 * Description :
 * 1、初始化 sheet、table
 * 2、创建   sheet、table
 * 3、组装数据
 * 4、构建excel
 ******************************/
public class ExcelBuilderFactory implements ExcelBuilder {

    private static ExcelBuilderFactory factory;

    private Excel excel;

    private Sheet[] sheets;

    private Table[][] tables;

    /**
     * 初始化sheet
     *
     * @param sheetNum sheet总数
     * @return 建造者
     */
    @Override
    public ExcelBuilder initSheet(int sheetNum) {
        this.sheets = new Sheet[sheetNum];
        for (int i = 0; i < this.sheets.length; i++) {
            sheets[i] = new Sheet();
        }
        this.excel.setSheetNum(sheetNum);
        return factory;
    }

    /**
     * 初始化表
     *
     * @param tableNum 数组
     *                 index - 对应 sheet页 下标
     *                 value - 对应 table 数量
     * @return 建造者
     */
    @Override
    public ExcelBuilder initTable(int... tableNum) {
        this.tables = new Table[tableNum.length][];
        for (int i = 0; i < tableNum.length; i++) {
            this.tables[i] = new Table[tableNum[i]];
            for (int j = 0; j < this.tables[i].length; j++) {
                this.tables[i][j] = new Table();
            }
            this.sheets[i].setTables(tables[i]);
            this.excel.setTableNum(this.excel.getTableNum() + tableNum[i]);
        }
        return factory;
    }

    /**
     * 组装数据
     *
     * @param sheetNum sheet 页下标
     * @param tableNum table 表下标
     * @param data     数据
     * @return 建造者
     */
    @Override
    public ExcelBuilder packing(int sheetNum, int tableNum, Map<String, Object> data) {
        Table table = this.tables[sheetNum][tableNum];
        table.setData(data);
        return factory;
    }

    /**
     * 组装数据
     * @param sheetNum      sheet 页下标
     * @param tableNum      table 页下标
     * @param data          数据 (当data为null或者空的时候，获取泛型T会失败)
     * @param dataCallBack  数据处理
     * @param <T>           数据类型 - 转换为Map<String, Object>
     * @return 代理
     */
    public <T> ExcelBuilder packing(int sheetNum, int tableNum, List<T> data, ExcelDataCallBack<T> dataCallBack) {
        Table table = this.tables[sheetNum][tableNum];
        Map<String, Object> result = new HashMap<>();
        Class<T> clazz = null;
        if (data != null && data.size() > 0) {
            clazz = (Class<T>) data.get(0).getClass();
        }
        table.setData(dataCallBack.getData(sheetNum, tableNum, data, clazz, result));
        return factory;
    }

    /**
     * 创建Sheet页
     *
     * @param sheetNum sheet 页下标
     * @param name     sheet 页名称
     * @param images   sheet 页中所有图片集
     * @return 建造者
     */
    @Override
    public ExcelBuilder createSheet(int sheetNum, String name, BufferedImage... images) {
        this.sheets[sheetNum].setName(name);
        if (images != null && images.length > 0) {
            this.sheets[sheetNum].setImages(images);
        }
        return factory;
    }

    /**
     * 创建table 表
     *
     * @param sheetNum sheet 页下标
     * @param tableNum table 表下标
     * @param row      起始  号下标
     * @param cell     起始  列下标
     * @param title    table 表附表头
     * @return 建造者
     */
    @Override
    public ExcelBuilder createTable(int sheetNum, int tableNum, int row, int cell, String[] title) {
        Table table = this.tables[sheetNum][tableNum];
        table.setTitles(title);
        table.setStartRow(row);
        table.setStartCell(cell);
        return factory;
    }

    /**
     * 构建
     *
     * @return Excel
     */
    @Override
    public Excel builder() {
        excel.setSheets(sheets);
        return excel;
    }

    /**
     * 获取工厂对象
     * 初始化excel
     * @param name excel名
     * @return 工厂
     */
    public static ExcelBuilderFactory construct(String name) {
        factory = LazyInit.INITIALIZATION;
        factory.excel = new Excel();
        factory.excel.setName(name);
        return factory;
    }


    public static ExcelBuilderFactory simple(String sheetName, Map<String, Object> data, String... titles) {
        factory = LazyInit.INITIALIZATION;
        factory.initSheet(1);
        factory.initTable(1);
        factory.createSheet(0, sheetName);
        factory.createTable(0,0, 1, 0, titles);
        factory.packing(0,0, data);
        return factory;
    }

    public static <T> ExcelBuilderFactory simple(String sheetName, String[] titles,  List<T> data, ExcelDataCallBack<T> dataCallBack) {
        factory = LazyInit.INITIALIZATION;
        factory.initSheet(1);
        factory.initTable(1);
        factory.createSheet(0, sheetName);
        factory.createTable(0,0, 1, 0, titles);
        factory.packing(0,0, data, dataCallBack);
        return factory;
    }

    public static <T> ExcelBuilderFactory simple(String sheetName, List<T> data, String... titles) {
        factory = LazyInit.INITIALIZATION;
        factory.initSheet(1);
        factory.initTable(1);
        factory.createSheet(0, sheetName);
        factory.createTable(0,0, 1, 0, titles);
        ExcelDataCallBack<T> dataCallBack = (sheetNum, tableNum, list, clazz, result) -> {
            List<String> cells = new ArrayList<>();
            if (list!= null && list.size() > 0) {
                T t = list.get(0);
                for (Field field : t.getClass().getDeclaredFields()) {
                    cells.add(field.getName());
                }
            } else
                return result;
            for (int row = 0; row < list.size(); row++) {
                for (int cell = 0; cell < cells.size(); cell++) {
                    result.put(row+ExcelDataCallBack.SIGN_K_V+cell, ObjectUtil.getValueByFiledName(list.get(row), cells.get(cell)));
                }
            }
            return result;
        };
        factory.packing(0,0, data, dataCallBack);
        return factory;
    }

    private static class LazyInit {
        private static final ExcelBuilderFactory INITIALIZATION = new ExcelBuilderFactory();
    }

    private ExcelBuilderFactory() {
    }

}
