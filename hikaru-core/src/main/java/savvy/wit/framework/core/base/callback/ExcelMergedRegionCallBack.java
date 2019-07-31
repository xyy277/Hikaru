package savvy.wit.framework.core.base.callback;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ExcelMergedRegionCallBack
 * Author : zhoujiajun
 * Date : 2019/7/29 14:17
 * Version : 1.0
 * Description : 
 ******************************/
public interface ExcelMergedRegionCallBack {

    /**
     *  合并单元格设置单元格宽度
     *  sheet.setColumnWidth(i, i == 0 ? 10 * 256 : 20 * 256); // 宽度设置
     *  sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, titles.length -1));
     * @param workbook              excel工作簿
     * @param title                 当前表头 - 当前sheet下 当前table中的表头
     * @param sheet                 当前sheet
     * @param sheetNum              sheet number
     * @param tableNum              table number
     * @param cellRangeAddressList  合并单元格数组
     * @return
     */
    List<CellRangeAddress> addMergedRegion(HSSFWorkbook workbook, Sheet sheet, String[] title, int sheetNum, int tableNum, List<CellRangeAddress> cellRangeAddressList);

}
