package savvy.wit.framework.core.base.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import savvy.wit.framework.core.base.callback.ExcelDataCallBack;
import savvy.wit.framework.core.base.callback.ExcelImageCallBack;
import savvy.wit.framework.core.base.callback.ExcelMergedRegionCallBack;
import savvy.wit.framework.core.base.callback.ExcelStyleCallBack;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 快速生成Excel
 * File name : ExcelUtil
 * Author : zhoujiajun
 * Date : 2019/7/29 17:08
 * Version : 1.0
 * Description : 解析或导出excel
 * v1.单线程模式导出excel表格，自定义表格结构及单元格样式
 * 导出excel时，传入数据集数据结构为List<List<T>> 外层list index为 sheet number（单个excel多个sheet）
 * 数据模式都已满足单个表格多个sheet的形式，几个回调函数中都带有num（sheet index）用于判断当前处理sheet 方便不同sheet特殊处理
 * 同时，针对excel插入图片满足每个sheet 自定义插入位置，图片传入类型为[]...二维数组形式，当不需要插入图片时，该参数略省，此时图片位置回调可以返回null
 * v2.为了缩短导出时间，将采用多线程模式对该类进行重构，以满足多个sheet的同时构建
 * @see savvy.wit.framework.test.ExcelTest
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
     * @param arrayList                 传入导出数据 List<List<T>> lists， 数据
     * @param startRowList              开始行号 List<[]> - num   每个sheet的正文开始row number
     * @param startCellList             开始列号 List<[]> - num   每个sheet的正文开始cell number
     * @param dataCallBack              数据回调，T - cellValues[]
     * @param styleCallBack             样式 - 每一个单元格的样式回调 （行号，列号）
     * @param imageCallBack             图片位置设置回调
     * @param bufferedImages            图片数组
     * @param <T>                       传入对象类型
     * @return
     */
    public static <T> File getExcel(HttpServletResponse response, String fileName, String[] sheetNames, List<List<String[]>> titleList,
                                ExcelMergedRegionCallBack mergedRegionCallBack, List<List<T>>[] arrayList,
                                List<int[]> startRowList, List<int[]> startCellList, ExcelDataCallBack<T> dataCallBack, ExcelStyleCallBack styleCallBack,
                                ExcelImageCallBack imageCallBack, BufferedImage[]... bufferedImages) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = null; HSSFRow row = null; Cell cell = null;
        for (int x = 0; x < sheetNames.length; x++) { // x - sheet number
            int[] startRows = startRowList.get(x);
            int[] startCells = startCellList.get(x);
            String sheetName = sheetNames[x];
            List<List<T>> lists = arrayList[x];
            sheet = workbook.createSheet(sheetName);
            /**
             * 通过sheet 自定义表头及表格式
             * 自定义表头包括：
             * 自定义行、列、值、样式等
             * 格式包括：
             * 合并单元格设置宽度
             */
            for (int i = 0; i < titleList.size(); i++) {
                List<String[]> titles = titleList.get(i);
                for (int j = 0; j < titles.size(); j++) {
                    String[] title = titles.get(j);
                    List<CellRangeAddress> cellRangeAddressList = new ArrayList<>();
                    cellRangeAddressList = mergedRegionCallBack.addMergedRegion(workbook, sheet, title, x, j, cellRangeAddressList);
                    if (cellRangeAddressList != null) {
                        for (CellRangeAddress cellRangeAddress : cellRangeAddressList) {
                            sheet.addMergedRegion(cellRangeAddress);
                        }
                    }
                }
            }
            /**
             * 正文主要通过自定义模板时预留的正文其实row、cell 的值
             * 来确定正文左上角由坐标(cell, row)固定
             * 正文内容由传入List<List<T>> 中数据类型T确定
             * List<T>为一个sheet中要导出的数据
             * T为一行数据，含多列信息
             * 每列信息通过回调的方式确定由正文起始（起始行列从0 开始计算）第一列到正文最后一列
             * 随后对每一个单元格中的样式进行回调处理，回调参数 （HSSFCellStyle，行号，列号，数据容量）
             */
            for (int y = 0; y < lists.size(); y++) {
                List<T> list = lists.get(y);
                for (int i = 0; i < list.size(); i++) { // 行
                    row = sheet.createRow(i + startRows[y]);
                    List<Object> values = new ArrayList<>();
                    values = dataCallBack.getValues(x, y, row, list.get(i), values);
                    for (int j = 0; j < values.size(); j++) { // 列
                        cell = row.createCell(j + startCells[y]);
                        HSSFCellStyle style = workbook.createCellStyle();
                        style = styleCallBack.getCellStyle(style, i + startRows[y], j + startCells[y], list.size(), y);
                        cell.setCellStyle(style);
                        setValue(cell, values.get(j));
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

    /**
     * 单元格填充数据
     * @param cell
     * @param value
     */
    private static void setValue(Cell cell, Object value) {
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
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
     * @param workbook
     * @param out
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
            out = null;
        }
    }

    /**
     * 插入图片
     * @param num
     * @param images
     * @param sheet
     * @param imageCallBack
     * @param workbook
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
                anchors = imageCallBack.ProcessingImage(num, anchors);
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
     * @param res
     * @param fileName
     * @return
     */
    private static OutputStream getOutPutStream(HttpServletResponse res, String fileName) {
        OutputStream out = null;
        try {
            if (res != null) {
                res.addHeader("content-type", "application/force-download;");
                res.addHeader("content-disposition", "attachment; filename=" +
                        URLEncoder.encode(fileName + ".xls", "utf-8").replaceAll("\\+", " "));
                out = res.getOutputStream();
            }else {
                out = new FileOutputStream(fileName + ".xls");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

}
