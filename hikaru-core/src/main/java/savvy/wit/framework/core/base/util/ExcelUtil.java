package savvy.wit.framework.core.base.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import savvy.wit.framework.core.base.callback.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 快速生成Excel
 * File name : ExcelUtil
 * Author : zhoujiajun
 * Date : 2019/7/29 17:08
 * Version : 1.1
 * Description : 解析或导出excel
 * v1.单线程模式导出excel表格，自定义表格结构及单元格样式
 * 导出excel时，传入数据集数据结构为List<List<Map<String, Object>>> 外层list index为 sheet number（单个excel多个sheet）
 * 数据模式都已满足单个表格多个sheet的形式，几个回调函数中都带有num（sheet index）用于判断当前处理sheet 方便不同sheet特殊处理
 * 同时，针对excel插入图片满足每个sheet 自定义插入位置，图片传入类型为[]...二维数组形式，当不需要插入图片时，该参数略省，此时图片位置回调可以返回null
 *
 * v1.1 对传入数据格式进行升级，同时为了得到控制，对该工具中间添加了新的代理类
 * @see savvy.wit.framework.core.pattern.proxy.ExcelProxy
 * 该代理类可以让创建和生成一个复杂excel变得直观可控
 *
 * v2.为了缩短导出时间，将采用多线程模式对该类进行重构，以满足多个sheet的同时构建
 *
 * 相关测试
 * @see excel
 ******************************/
public class ExcelUtil {

    /**
     * 获取excel
     * 满足生成一个excel包含多个sheet
     * 一个sheet 包含多个表格 及插图
     * 1、自定义表头设置格式（合并 单元格，宽高样式等）
     * 2、正文数据回调处理及样式
     * 3、每一个单元格的样式处理
     * 4、图片回调处理（存在图片的情况下）
     * @param response                  response
     * @param fileName                  文件名
     * @param sheetNames                sheet 数组，一个excel可有多个sheet
     * @param titleList                 title 数组集合，外层集合多个sheet  -  内层数组对应多个table  -  里层每列的附表头数据value
     * @param mergedRegionCallBack      自定义表头（数据及样式），合并单元格
     * @param arrayList                 传入导出数据 List<List<Map<String,Object>>> lists， 数据
     * @param startRowList              开始行号 List<[]> - num   每个sheet的正文开始row number
     * @param startCellList             开始列号 List<[]> - num   每个sheet的正文开始cell number
     * remove  dataCallBack(v1.1弃用)    数据回调，T - cellValues[]
     * @param styleCallBack             样式 - 每一个单元格的样式回调 （行号，列号）
     * @param imageCallBack             图片位置设置回调
     * @param bufferedImages            图片数组
     *                                   传入对象类型
     * @return file
     */
    public static File getExcel(HttpServletResponse response, String fileName, String[] sheetNames, List<List<String[]>> titleList,
                                ExcelMergedRegionCallBack mergedRegionCallBack, List<List<Map<String,Object>>> arrayList,
                                List<int[]> startRowList, List<int[]> startCellList,
                                HSSFCellStyleInitCallBack hssfCellStyleInitCallBack, ExcelStyleCallBack styleCallBack,
                                ExcelImageCallBack imageCallBack, BufferedImage[]... bufferedImages) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet; HSSFRow row; Cell cell;
        for (int x = 0; x < sheetNames.length; x++) { // x - sheet number
            int[] startRows = startRowList.get(x);
            int[] startCells = startCellList.get(x);
            String sheetName = sheetNames[x];
            List<Map<String, Object>> lists = arrayList.get(x); // 当前sheet 中多个表格的data
            sheet = workbook.createSheet(sheetName);
            /*
             * 通过sheet 自定义表头及表格式
             * 自定义表头包括：
             * 自定义行、列、值、样式等
             * 格式包括：
             * 合并单元格设置宽度
             */
            List<String[]> titles = titleList.get(x);  // 多表
            for (int j = 0; j < titles.size(); j++) { // 单表
                int size = rowIndex(lists.get(j));
                String[] title = titles.get(j);
                List<CellRangeAddress> cellRangeAddressList = new ArrayList<>();
                if (mergedRegionCallBack != null)
                    cellRangeAddressList = mergedRegionCallBack.addMergedRegion(workbook, sheet, title, x, j, size, cellRangeAddressList);
                else {
                    row = sheet.createRow(startRows[0] - 1);
                    for (int i = 0; i < title.length; i++) {
                        cell = row.createCell(i);
                        cell.setCellStyle(createStyle(workbook));
                        cell.setCellValue(title[i]);
                    }
                }
                if (cellRangeAddressList != null) {
                    for (CellRangeAddress cellRangeAddress : cellRangeAddressList) {
                        sheet.addMergedRegion(cellRangeAddress);
                    }
                }
            }
            /*
             * 正文主要通过自定义模板时预留的正文其实row、cell 的值
             * 来确定正文左上角由坐标(cell, row)固定
             * 正文内容由传入List<List<T>> 中数据类型T确定
             * List<T>为一个sheet中要导出的数据
             * T为一行数据，含多列信息
             * 每列信息通过回调的方式确定由正文起始（起始行列从0 开始计算）第一列到正文最后一列
             * 随后对每一个单元格中的样式进行回调处理，回调参数 （HSSFCellStyle，行号，列号，数据容量）
             */
            List<HSSFCellStyle> styles = new ArrayList<>();
            if (hssfCellStyleInitCallBack != null)
                styles = hssfCellStyleInitCallBack.initStyle(workbook, styles);
            for (int y = 0; y < lists.size(); y++) {
                Map<String, Object> map = lists.get(y); // 整张表数据
                if (map == null || map.size() <1) {
                    continue;
                }
                Integer rowIndex = rowIndex(map);
                Integer cellIndex = cellIndex(map);
                // 将整张表的数据转为2dArray
                Object[][] tableValues = new Object[rowIndex + 1][cellIndex + 1]; // 初始化 行列
                map.keySet().forEach(key -> {
                    String[] keys = key.split(ExcelDataCallBack.SIGN_K_V);
                    int rowNum = Integer.parseInt(keys[0]);
                    int cellNum = Integer.parseInt(keys[1]);
                    tableValues[rowNum][cellNum] = map.get(key);
                });
                for (int i = 0; i < tableValues.length; i++) { // 行
                    row = sheet.createRow(i + startRows[y]);
                    Object[] rowValues = tableValues[i];
                    for (int j = 0; j < rowValues.length; j++) { // 列
                        cell = row.createCell(j + startCells[y]);
                        HSSFCellStyle style = createStyle(workbook);
                        if (styleCallBack != null)
                            style = styleCallBack.getCellStyle(row, styles, i + startRows[y], j + startCells[y], rowIndex + 1, x, y);
                        cell.setCellStyle(style);
                        setValue(cell, rowValues[j]);
                    }
                }
            }
            // 图片
            if (x < bufferedImages.length) {
                setImages(x, bufferedImages[x], sheet, imageCallBack, workbook);
            }
            // 获取stream
            OutputStream out = getOutPutStream(response, fileName);
            // 导出stream
            writeStream(workbook, out);
        }
        String time = DateUtil.getNow("ddMMyyyy");
        return response == null ? new File(fileName + "-" + time + ".xls") : null;
    }

    private static int rowIndex(Map<String, Object> map) {
        return map != null && map.size() > 0 ? map.keySet().stream()
                .map(key -> Integer.parseInt(key.split(ExcelDataCallBack.SIGN_K_V)[0]))
                .max(Comparator.naturalOrder())
                .get() : 0;
    }

    private static int cellIndex(Map<String, Object> map) {
        return map != null && map.size() > 0 ? map.keySet().stream()
                .map(key -> Integer.parseInt(key.split(ExcelDataCallBack.SIGN_K_V)[1]))
                .max(Integer::compareTo)
                .get(): 0;
    }


    /**
     * 单元格填充数据
     * @param cell cell
     * @param value data
     */
    private static void setValue(Cell cell, Object value) {
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Double) {
            cell.setCellValue(String.valueOf(value));
        } else if (value instanceof Date) {
            cell.setCellValue(String.valueOf(value));
        } else if (value instanceof Calendar) {
            cell.setCellValue((Calendar) value);
        } else if (value instanceof RichTextString) {
            cell.setCellValue((RichTextString) value);
        } else {
            cell.setCellValue(String.valueOf(value));
        }
    }

    /**
     * 写出流
     * @param workbook book
     * @param out      stream
     */
    private static void writeStream(Workbook workbook, OutputStream out) {
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
        }
    }

    /**
     * 插入图片
     * @param num               sheetNum
     * @param images            images
     * @param sheet             sheet
     * @param imageCallBack     callback
     * @param workbook          book
     */
    private static void setImages(int num, BufferedImage[] images, HSSFSheet sheet, ExcelImageCallBack imageCallBack, Workbook workbook) {
        // 图片处理
        if (images != null && images.length > 0) {
            try {
                ByteArrayOutputStream[] byteArrayOutputStreams = new ByteArrayOutputStream[images.length];
                for (int i = 0; i < byteArrayOutputStreams.length; i++) {
                    byteArrayOutputStreams[i] = new ByteArrayOutputStream();
                    if (images[i] != null) {
                        ImageIO.write(images[i],"png", byteArrayOutputStreams[i]);
                    }
                }
                HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
                // 图片导出到单元格
                HSSFClientAnchor[] anchors = new HSSFClientAnchor[byteArrayOutputStreams.length];
                anchors = imageCallBack.processingImage(num, anchors);
                if (anchors != null && anchors.length > 0) {
                    for (int i = 0; i < anchors.length; i++) {
                        // 插入图片
                        patriarch.createPicture(anchors[i], workbook.addPicture(byteArrayOutputStreams[i].toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取response输出流
     * @param response         response
     * @param fileName         fileName
     * @return OutputStream
     */
    private static OutputStream getOutPutStream(HttpServletResponse response, String fileName) {
        OutputStream out = null;
        try {
            if (response != null) {
                response.addHeader("content-type", "application/octet-stream;");
                String name =  URLEncoder.encode(fileName + ".xls", "utf-8").replaceAll("\\+", " ");
                response.addHeader("content-disposition", "attachment; filename=" + name);
                out = response.getOutputStream();
            }else {
                out = new FileOutputStream(fileName + ".xls");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public static HSSFCellStyle createStyle(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();
        border(style);
        return style;
    }

    public static void border(HSSFCellStyle style) {
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    }

}
