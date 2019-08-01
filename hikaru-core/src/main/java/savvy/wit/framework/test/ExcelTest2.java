package savvy.wit.framework.test;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.base.util.DateUtil;
import savvy.wit.framework.core.base.util.ExcelUtil;
import savvy.wit.framework.core.base.util.ObjectUtil;
import savvy.wit.framework.core.pattern.decorate.Counter;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ExcelTest2
 * Author : zhoujiajun
 * Date : 2019/7/30 18:22
 * Version : 1.0
 * Description : 
 ******************************/
public class ExcelTest2 {

    private static Log log = LogFactory.getLog();

    public static void main(String[] args)throws Exception {
        /**
         * 数据准备
         */
        List arrayList = getData();
        String fileName = "Atendimento Diário às Emergências";
        String[] sheetNames = new String[] {"第一个表格", "第二个表格", "第三个表格","第四个表格"};
        List<int[]> startRows = new ArrayList<>();
        List<int[]> startCells = new ArrayList<>();
        startRows.add(new int[]{3, 13, 20}); // 第一页 第三行、 第13行、第 20行
        startCells.add(new int[]{1, 1, 1});
        List<List<String[]>>titleList = new ArrayList<>();
        List<String[]> titles = new ArrayList<>();
        String[] title_ = new String[]{null, "Válidas","Consultas", "Registo", "Inválidas", "Total"};
        String[] titles2 = new String [] {};
        titles.add(title_);titles.add(titles2);
        titleList.add(titles);
        // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        /**
         * 导出excel
         */
        Counter counter = Counter.create();
        ExcelUtil.getExcel(null, fileName, sheetNames,titleList, (workbook, sheet, title, sheetNum, tableNum, cellRangeAddressList) -> { // 多页sheet - num sheet number, sheet 自定义表头， 表头格式 - 自定义宽高， 合并单元格
                    return getRangeAddressList(workbook,sheet,title, sheetNum, tableNum,cellRangeAddressList);
                }, arrayList, startRows, startCells, null,(style, row, cell, size, sheetNum, tableNum) -> { // 正文单元格样式 - style 行、列，从0,0 开始计算
                    setBorder(style);
                    if (cell == 1) { // 第一列
                        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                        if (row > size) { // 最后两行
                            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                            style.setFillForegroundColor(HSSFColor.PINK.index);
                        }
                    } else { // 其他列
                        style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                    }
                    return style;
                }, (num, anchors) -> { // 图片位置大小
                    switch (num) {
                        case 0:
                            for (int i = 0; i < anchors.length; i++) {
                                anchors[i] = new HSSFClientAnchor(0,0,0,0, (short)0, 1+(i+1)*11, (short) 8, (i+1)*11+25);
                            }
                            break;
                        case 1:
                            for (int i = 0; i < anchors.length; i++) {
                                anchors[i] = new HSSFClientAnchor(0,0,0,0, (short)0, 1+(i+1)*11, (short) 7, (i+1)*11+25);
                            }
                            break;
                    }
                    return anchors;
                },new BufferedImage[]{ImageIO.read(new File("G:\\GitHub\\hikaru\\hikaru-server\\test.jpg"))},
                new BufferedImage[]{ImageIO.read(new File("G:\\GitHub\\hikaru\\hikaru-server\\test2.jpg"))});

        log.log(counter.destroy());
    }


    private static List<CellRangeAddress> getRangeAddressList(HSSFWorkbook workbook, Sheet sheet, String[] titles, int sheetNum, int tableNum, List<CellRangeAddress> cellRangeAddressList) {
        List<int[]> startCells = new ArrayList<>();
        startCells.add(new int[]{1, 1, 1});
        String[] headers = new String[] {"Data", DateUtil.getNow("dd/MM/yyyy")};
        cellRangeAddressList.add(new CellRangeAddress(1,2,1,1));
        cellRangeAddressList.add(new CellRangeAddress(0,0,2,6));
        cellRangeAddressList.add(new CellRangeAddress(1,1,2,6));
        Row row = sheet.createRow(1); // 第一行
        row.setHeight((short) 350);
        for (int i = 0; i < titles.length; i++) {
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            cellStyle.setFont(font);
            font.setBoldweight((short)64);
            Cell cell = row.createCell(i + startCells.get(sheetNum)[tableNum]);
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
            if (i < headers.length) {
                cell.setCellValue(headers[i]);
            }
            setBorder(cellStyle);
            cell.setCellStyle(cellStyle);
        }
        row = sheet.createRow(2); // 第二行附表头
        row.setHeight((short) 500);
        for (int i = 0; i < titles.length; i++) {
            Cell cell = row.createCell(i + startCells.get(sheetNum)[tableNum]);
            if (titles[i] != null) {
                cell.setCellValue(titles[i]);
            }
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            if (i == titles.length - 1) {
                cellStyle.setFillForegroundColor(HSSFColor.PINK.index);
            } else {
                cellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
            }
            setBorder(cellStyle);
            cell.setCellStyle(cellStyle);
        }
        // 设置宽
        for (int i = 0; i < titles.length + startCells.get(sheetNum)[tableNum]; i++) {
            sheet.setColumnWidth(i, i == 0 ? 5*256 : i == 1 ? 25*256 : i == 2 ? 10*256 : 18*256);
        }
        return cellRangeAddressList;
    }

    private static void setBorder(CellStyle style) {
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    }

    private static List getData() {
        List arrayList = new ArrayList(); // 一页
        for (int j = 0; j < 1; j++) { // 多页
            List<List<Object>> lists = new ArrayList<>();
            for (int x = 0; x < 3; x++) { // 多表
                if (x == 0) { // 第一张 表
                    BigDecimal total = new BigDecimal(1);
                    List<Object> list = new ArrayList<>();
                    int rowNumber = 5; // 基本数据量 5
                    for (int i = 0; i < rowNumber + 2; i++) { // 总数据量 7 = 基本数据量 + 总计 + 百分比
                        User user = new User(); // n 列 数据
                        if (i < rowNumber) {
                            user.setBirthday(DateUtil.getNow("dd/MM/yyyy"));
                            user.setName(new BigDecimal(DateUtil.random(10)));
                            user.setNickname(new BigDecimal(DateUtil.random(15)));
                            user.setUsername(new BigDecimal(DateUtil.random(10)));
                            user.setPassword(new BigDecimal(DateUtil.random(10)));
                            user.setTotal(user.getName().add(user.getNickname()).add(user.getUsername()).add(user.getPassword()));
                        } else if (i == rowNumber){
                            user.setBirthday("Total");
                            user.setName(list.stream().map(user1 -> ((User)user1).getName()).reduce(BigDecimal.ZERO, BigDecimal::add));
                            user.setNickname(list.stream().map(user1 -> ((User)user1).getNickname()).reduce(BigDecimal.ZERO, BigDecimal::add));
                            user.setUsername(list.stream().map(user1 -> ((User)user1).getUsername()).reduce(BigDecimal.ZERO, BigDecimal::add));
                            user.setPassword(list.stream().map(user1 -> ((User)user1).getPassword()).reduce(BigDecimal.ZERO, BigDecimal::add));
                            user.setTotal(list.stream().map(user1 -> ((User)user1).getTotal()).reduce(BigDecimal.ZERO, BigDecimal::add));
                            total = user.getTotal();
                        } else {
                            user.setBirthday("Proporção");
                            user.setName(list.stream().map(user1 -> ((User)user1).getName()).max((o1, o2) -> o1.compareTo(o2)).get().divide(total, 2, BigDecimal.ROUND_HALF_UP));
                            user.setNickname(list.stream().map(user1 -> ((User)user1).getNickname()).max((o1, o2) -> o1.compareTo(o2)).get().divide(total, 2, BigDecimal.ROUND_HALF_UP));
                            user.setUsername(list.stream().map(user1 -> ((User)user1).getUsername()).max((o1, o2) -> o1.compareTo(o2)).get().divide(total, 2, BigDecimal.ROUND_HALF_UP));
                            user.setPassword(list.stream().map(user1 -> ((User)user1).getPassword()).max((o1, o2) -> o1.compareTo(o2)).get().divide(total, 2, BigDecimal.ROUND_HALF_UP));
                            user.setTotal(list.stream().map(user1 -> ((User)user1).getTotal()).max((o1, o2) -> o1.compareTo(o2)).get().divide(total, 2, BigDecimal.ROUND_HALF_UP));
                        }
                        list.add(user);
                    }
                    lists.add(list);
                }
                if (x == 1) { // 第二张表
                    BigDecimal total = new BigDecimal(1);
                    int rowNum = 2;
                    List<Object> maps = new ArrayList<>();
                    for (int i = 0; i < rowNum + 2; i++) { // 行
                        Map<Map<Integer, Integer>, BigDecimal> map = new HashMap<>();
                        for (int k = 0; k < 4; k++) {   // 列
                            BigDecimal total_ = new BigDecimal(0);
                            Map<Integer, Integer> coordinate = new HashMap<>(); // 单元格坐标
                            coordinate.put(i,k); // 行 列
                            if (i < rowNum) {
                                map.put(coordinate, new BigDecimal(DateUtil.random(10)));
                            }
                            if (i == rowNum) { // 倒数第二行总计

                                map.put(coordinate, total_);
                            } else {    // 倒数第一行百分比
                                map.put(coordinate, total_.divide(total, 2, BigDecimal.ROUND_HALF_UP));
                            }
                            if (k == 3) { // 第四列 总计

                            }
                        }
                        maps.add(map);
                    }
                    lists.add(maps);
                }
            }
            arrayList.add(lists);
        }
        return arrayList;
    }

}
