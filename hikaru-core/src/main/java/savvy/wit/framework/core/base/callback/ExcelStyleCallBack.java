package savvy.wit.framework.core.base.callback;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;
import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ExcelStyleCallBack
 * Author : zhoujiajun
 * Date : 2019/7/26 10:31
 * Version : 1.0
 * Description : 
 ******************************/
public interface ExcelStyleCallBack {

    /**
     * 回调设置任意行列单元格样式
     * 目前仅设计在正文处使用，表头处采用全自定方式
     * @param row       行
     * @param style     样式
     * @param rowNum    行       number
     * @param cellNum   列       number
     * @param size      总行数
     * @param sheetNum  页下标 number - sheet中多个表对应的下标
     * @param tableNum  表下标 number - sheet中多个表对应的下标
     * @return
     */
    HSSFCellStyle getCellStyle(HSSFRow row, List<HSSFCellStyle> style, int rowNum, int cellNum, int size, int sheetNum, int tableNum);
}
