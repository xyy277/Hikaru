package savvy.wit.framework.core.base.util;/**
 * Created by zhoujiajun on 2018/7/2.
 */

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import savvy.wit.framework.core.base.callback.ExcelDataCallBack;
import savvy.wit.framework.core.base.callback.ExcelImageCallBack;
import savvy.wit.framework.core.base.callback.ExcelStyleCallBack;
import savvy.wit.framework.core.base.exception.runtime.CreateExcelException;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
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

    private static HSSFCellStyle getStyle(Map<Integer, HSSFCellStyle> styles, Integer position) {
        HSSFCellStyle style = null;
        if (styles != null && styles.containsKey(position) ) {
            style = styles.get(position);
        }
        return style;
    }

    private static void setStyle(Cell cell, HSSFCellStyle style) {
        if (cell != null && style != null) {
            cell.setCellStyle(style);
        }
    }

    /**
     *
     * @param response
     * @param fileName
     * @param list
     * @param titles
     * @param tails
     * @param complex
     * @param styleCallBack
     * @param dataCallBack
     * @param imageCallBack
     * @param images
     * @param <T>
     * @return
     */
    public static <T> File getExcel(HttpServletResponse response, String fileName, String[] headers,
                                    List<T> list, String[] titles, String[] tails, boolean complex,
                                    ExcelStyleCallBack styleCallBack, ExcelDataCallBack<T> dataCallBack, ExcelImageCallBack imageCallBack, BufferedImage... images) {
        if (StringUtil.isBlank(fileName)) {
            throw new CreateExcelException("export Excel file or stream must have this own name!");
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        if (headers == null) {
            headers = new String[0];
        }
        if (titles == null) {
            titles = new String[0];
        }
        if (tails == null) {
            tails = new String[0];
        }
        if (images == null) {
            images = new BufferedImage[0];
        }
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(fileName);
        Map<Integer, HSSFCellStyle> styles = new HashMap<>();
        styles = styleCallBack.getCellStyle(workbook, styles);
        HSSFRow row = null;
        Cell cell = null;
        // 表头
        if (complex) {
            for (int i = 0; i < headers.length; i++) {
                row = sheet.createRow(i);
                cell = row.createCell(0);
                cell.setCellValue(headers[i]);
                row.setHeight((short)600);
                setStyle(cell, getStyle(styles, ExcelStyleCallBack.HEADER));
            }
        }
        // 合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, titles.length -1));
        // 附表头
        row = sheet.createRow(complex ? headers.length : 0); // 第二行
        for (int i = 0; i < titles.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(titles[i]);
            setStyle(cell, getStyle(styles, ExcelStyleCallBack.HEADER_));
            sheet.setColumnWidth(i, i == 0 ? 10 * 256 : 20 * 256); // 宽度设置
        }
        row.setHeight((short)540);
        // 正文
        for (int i = 0; i < list.size(); i++) {
            String[] values = dataCallBack.getValues(list.get(i), i + 1);
            row = sheet.createRow(i + 1 + (complex ? headers.length: 0));
            for (int i1 = 0; i1 < values.length; i1++) {
                row.createCell(i1).setCellValue(values[i1]);
                setStyle(cell, getStyle(styles, ExcelStyleCallBack.BODY));
                row.setHeight((short) 500);
            }
        }
        // 表尾
        if (complex) {
            for (int i = 0; i < tails.length; i++) {
                row = sheet.createRow(2 + list.size() + i);
                cell = row.createCell(0);
                cell.setCellValue(tails[i]);
                setStyle(cell, getStyle(styles, ExcelStyleCallBack.FOOTER));
                row.setHeight((short)600);
                sheet.addMergedRegion(new CellRangeAddress(2 + i + list.size(), 2 + i + list.size(), 0, titles.length -1));
            }
        }

        // 图片处理
        if (images != null && images.length > 0) {
            try {
                ByteArrayOutputStream[] byteArrayOutputStreams = new ByteArrayOutputStream[images.length];
                for (int i = 0; i < byteArrayOutputStreams.length; i++) {
                    byteArrayOutputStreams[i] = new ByteArrayOutputStream();
                    ImageIO.write(images[i],"png", byteArrayOutputStreams[i]);
                }
                HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
                // 图片导出到单元格
                HSSFClientAnchor[] anchors = new HSSFClientAnchor[byteArrayOutputStreams.length];
                anchors = imageCallBack.ProcessingImage(anchors);
                for (int i = 0; i < anchors.length; i++) {
                    // 插入图片
                    patriarch.createPicture(anchors[i], workbook.addPicture(byteArrayOutputStreams[i].toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
                }
            } catch (Exception e) {
                log.error(e);
            }
        }

        String time = DateUtil.getNow("ddMMyyyy-HHmmss");
        OutputStream out = getOutPutStream(response, fileName, time);
        try {
            workbook.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            out = null;
        }
        return new File(fileName + "-" + time + ".xls");
    }


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
                res.addHeader("content-type", "application/force-download;");
                res.addHeader("content-disposition", "attachment; filename=" +
                        URLEncoder.encode(fname + "-" + time + ".xls", "utf-8").replaceAll("\\+", " "));
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