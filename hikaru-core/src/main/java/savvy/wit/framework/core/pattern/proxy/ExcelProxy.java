package savvy.wit.framework.core.pattern.proxy;

import savvy.wit.framework.core.base.callback.*;
import savvy.wit.framework.core.base.model.Excel;
import savvy.wit.framework.core.base.util.ExcelUtil;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : Excel 代理
 * File name : ExcelProxy
 * Author : zhoujiajun
 * Date : 2019/7/31 15:57
 * Version : 1.0
 * Description : 通过Excel 对象生成一个Excel表格
 * Excel  表格 - 多个Sheet
 * Sheet  页面 - 多个Table
 * Sheet  页面 - 多个图片
 ******************************/
public class ExcelProxy implements AbstractExcelProxy {
    /**
     * 代理
     */
    private static ExcelProxy proxy;
    /**
     * 生成的excel文件
     */
    protected File file;
    /**
     * 需要代理的excel对象
     */
    protected Excel excel;
    /**
     * 响应对象
     */
    protected HttpServletResponse response;
    /**
     * sheet页名数组
     */
    protected String[] sheetNames;
    /**
     * 每个表的附表头数组
     */
    protected List<List<String[]>> titleList = new ArrayList<>();
    /**
     * 组装好的数据
     */
    protected List<List<Map<String, Object>>> dataList = new ArrayList<>();
    /**
     * 每个表起始行
     */
    protected List<int[]> startRowList = new ArrayList<>();
    /**
     * 每个表起始列
     */
    protected List<int[]> startCellList =  new ArrayList<>();
    /**
     * 所有sheet的图片集
     */
    protected BufferedImage[][] bufferedImages;
    /**
     * 回调设置 sheet结构 样式 自定义表头等
     */
    protected ExcelMergedRegionCallBack mergedRegionCallBack;

    /**
     * 获取一行中所有列数据 （已弃用）
     */
    protected ExcelDataCallBack dataCallBack;
    /**
     * 初始化单元格样式
     */
    protected HSSFCellStyleInitCallBack hssfCellStyleInitCallBack;
    /**
     * 回调处理任意单元格样式
     */
    protected ExcelStyleCallBack styleCallBack;
    /**
     * 回调处理图像插入位置
     */
    protected ExcelImageCallBack imageCallBack;

    /**
     * 代理Excel对象
     * @param excel 被代理对象
     * @return 代理
     */
    public static ExcelProxy proxy(Excel excel) {
        proxy = LazyInit.INITIALIZATION;
        proxy.excel = excel;
        proxy.parse();
        return proxy;
    }

    /**
     * 响应http
     * @param response 响应
     * @return 代理
     */
    public ExcelProxy http(HttpServletResponse response) {
        proxy.response = response;
        return proxy;
    }

    /**
     * 格式处理
     *
     * @param mergedRegionCallBack 回调函数
     * @return 代理
     */
    public ExcelProxy addMergeRegion(ExcelMergedRegionCallBack mergedRegionCallBack) {
        proxy.mergedRegionCallBack = mergedRegionCallBack;
        return proxy;
    }

    public ExcelProxy initStyle(HSSFCellStyleInitCallBack hssfCellStyleInitCallBack) {
        proxy.hssfCellStyleInitCallBack = hssfCellStyleInitCallBack;
        return proxy;
    }

    /**
     * 样式处理
     *
     * @param styleCallBack 回调函数
     * @return 代理
     */
    public ExcelProxy getCellStyle(ExcelStyleCallBack styleCallBack) {
        proxy.styleCallBack = styleCallBack;
        return proxy;
    }

    /**
     * 图片处理
     *
     * @param imageCallBack 回调函数
     * @return 代理
     */
    public ExcelProxy processingImg(ExcelImageCallBack imageCallBack) {
        proxy.imageCallBack = imageCallBack;
        return proxy;
    }

    /**
     * 生成excel
     *
     * @return 代理
     */
    public ExcelProxy produce() {
        proxy.file = ExcelUtil.getExcel(response, excel.getName(), sheetNames, titleList, mergedRegionCallBack, dataList,
                startRowList, startCellList, null,hssfCellStyleInitCallBack, styleCallBack,imageCallBack, bufferedImages);
        cleanAll();
        return proxy;
    }

    /**
     * 获取被代理的excel对象
     * @return excel 对象
     */
    public Excel getExcel() {
        return excel;
    }

    /**
     * 获取excel文件
     * @return 文件
     */
    public File getFile() {
        return file;
    }

    /**
     * 清理
     * 不包括上一次代理生成的excel文件
     * 同时新代理的excel文件会覆盖
     */
    private void cleanAll() {
        proxy.excel = null;
        proxy.response = null;
        proxy.sheetNames = null;
        proxy.titleList = new ArrayList<>();
        proxy.dataList = new ArrayList<>();
        proxy.startRowList = new ArrayList<>();
        proxy.startCellList = new ArrayList<>();
        proxy.bufferedImages = null;
        proxy.dataCallBack = null;
        proxy.mergedRegionCallBack = null;
        proxy.styleCallBack = null;
        proxy.imageCallBack = null;
    }

    /**
     * 将excel 对象解析成几个必要参数
     * 对应ExcelUtil中getExcel方法参数
     * @see savvy.wit.framework.core.base.util.ExcelUtil
     */
    private void parse() {
        // sheetNames
        sheetNames = new String[excel.getSheets().length];
        for (int i = 0; i < sheetNames.length; i++) {
            sheetNames[i] = excel.getSheets()[i].getName();
        }
        /**
         * init
         */
        titleList = new ArrayList<>();
        startRowList = new ArrayList<>();
        startCellList = new ArrayList<>();
        dataList = new ArrayList<>();
        bufferedImages = new BufferedImage[excel.getSheetNum()][];
        // titleList、 startRowList、 startCellList、 bufferImages、dataList
        for (int i = 0; i < excel.getSheetNum(); i++) {

            List<Map<String, Object>> list = new ArrayList<>(); // 一个sheet 中 多张表的数据汇总
            bufferedImages[i] = excel.getSheets()[i].getImages();
            List<String[]> titles = new ArrayList<>();
            int[] rows = new int[excel.getSheets()[i].getTables().length];
            int[] cells = new int[rows.length];

            // sheet 中循环 table
            for (int j = 0; j < excel.getSheets()[i].getTables().length; j++) {
                Map<String, Object> data = excel.getSheets()[i].getTables()[j].getData(); // 一张表中所有数据
                rows[j] = excel.getSheets()[i].getTables()[j].getStartRow();
                cells[j] = excel.getSheets()[i].getTables()[j].getStartCell();
                titles.add(excel.getSheets()[i].getTables()[j].getTitles());
                list.add(data);
            }
            startRowList.add(rows);     // row number
            startCellList.add(cells);   // cell number
            titleList.add(titles);      // title
            dataList.add(list);         // data
        }
    }

    private static class LazyInit {
        private static final ExcelProxy INITIALIZATION = new ExcelProxy();
    }

    private ExcelProxy() {
    }
}
