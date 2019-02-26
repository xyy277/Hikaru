package savvy.wit.framework.core.base.util;/**
 * Created by zhoujiajun on 2018/7/2.
 */

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ScUtils
 * Author : zhoujiajun
 * Date : 2018/7/2 9:15
 * Version : 1.0
 * Description : 
 ******************************/
public class ScExcelUtils {

    private static Log log = LogFactory.getLog();

    public static File getExcel(HttpServletResponse res, String fname,
                                List<Map<String, String>> maplist, String[] titles, String[] keys) throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(fname);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 创建一个居中格式


        for (int n = 0; n < titles.length; n++) {
            sheet.setColumnWidth(n, 10000);
        }

        HSSFCell cell = row.createCell(0, HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(titles[0]);
        cell.setCellStyle(style);
        for (int i = 1; i < titles.length; i++) {
            cell = row.createCell(i, HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(style);
        }

        for (int i = 0; i < maplist.size(); i++) {
            HSSFRow row2 = sheet.createRow(i + 1);//index：第几行
            Map<String, String> map = maplist.get(i);
            for (int j = 0; j < titles.length; j++) {
                cell = row2.createCell(j);//第几列：从0开始
                cell.setCellValue(map.get(keys[j]));
                cell.setCellStyle(style);
            }
        }
        String time = DateUtil.getNow("yyyyMMddHHmmss");
        OutputStream out = getOutPutStream(res, fname, time);
        try {
            wb.write(out);
            out.flush();
            out.close();
        } finally {
            if (out != null) {
                out.close();
            }
            out = null;
        }
        return new File(fname + "-" + time + ".xls");
    }


    //注意模板的顺序
    public static List<Map<String, String>> parseExcel(InputStream fis) {
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        try {
            Workbook book = null;
            try {
                book = new XSSFWorkbook(fis);
            } catch (Exception ex) {
                book = new HSSFWorkbook(fis);
            }
            Sheet sheet = book.getSheetAt(0);
            int firstRow = sheet.getFirstRowNum();
            int lastRow = sheet.getLastRowNum();
            int index = 0;
            //除去第一行
            for (int i = firstRow + 1; i < lastRow + 1; i++) {
                Map<String, String> map = new HashMap<String, String>();

                Row row = sheet.getRow(i);
                int firstCell = row.getFirstCellNum();
                int lastCell = row.getLastCellNum();

                for (int j = firstCell; j <= lastCell; j++) {
                    if (j == lastCell) {
                        map.put("CELL0", String.valueOf(++index));
                        continue;
                    }
                    String key = "CELL" + (j + 1);//从1开始
                    Cell cell = row.getCell(j);
                    String val = "";
                    if (cell != null) {
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        val = cell.getStringCellValue();
                    }
                    if (i == firstRow) {
                        break;
                    } else {
                        map.put(key, val == null ? "" : val);
                    }
                }
                if (i != firstRow) {
                    data.add(map);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


    private static OutputStream getOutPutStream(HttpServletResponse res, String fname, String time) {
        OutputStream out = null;
        try {
            if (res != null) {
                res.addHeader("content-type", "application/x-msdownload;");
                res.addHeader("content-disposition", "attachment; filename=" +
                        URLEncoder.encode(fname + "-" + time + ".xls", "utf-8"));
                out = res.getOutputStream();
            }else {
                out = new FileOutputStream(fname + "-" + time + ".xls");
            }
        }catch (Exception e) {
            log.error(e);
        }
        return out;
    }

}