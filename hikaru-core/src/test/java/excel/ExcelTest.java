package excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Before;
import org.junit.Test;
import savvy.wit.framework.core.base.callback.ExcelDataCallBack;
import savvy.wit.framework.core.base.model.Excel;
import savvy.wit.framework.core.base.util.DateUtil;
import savvy.wit.framework.core.base.util.ObjectUtil;
import savvy.wit.framework.core.pattern.factory.ExcelBuilderFactory;
import savvy.wit.framework.core.pattern.proxy.ExcelProxy;
import savvy.wit.framework.test.Statistic;
import savvy.wit.framework.test.User;

import javax.imageio.ImageIO;
import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ExcelTest
 * Author : zhoujiajun
 * Date : 2019/8/1 9:24
 * Version : 1.0
 * Description : 
 ******************************/
public class ExcelTest {

    private Excel excel;

    private List<User> list1 = new ArrayList<>();
    private List<Statistic> list2 = new ArrayList<>();
    private List<Statistic> list3 = new ArrayList<>();

    private void data() {
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setBirthday(DateUtil.getNow("dd/MM/yyyy"));
            user.setName(new BigDecimal(DateUtil.random(10)));
            user.setNickname(new BigDecimal(DateUtil.random(10)));
            user.setUsername(new BigDecimal(DateUtil.random(10)));
            user.setPassword(new BigDecimal(DateUtil.random(10)));
            user.setTotal(user.getName().add(user.getNickname()).add(user.getUsername()).add(user.getPassword()));
            list1.add(user);
            Statistic statistic = new Statistic();
            statistic.setDate(DateUtil.getNow("dd/MM/yyyy"));
            statistic.setT1(DateUtil.random(10));
            statistic.setT2(DateUtil.random(10));
            statistic.setT3(DateUtil.random(10));
            statistic.setT4(DateUtil.random(10));
            statistic.setT5(DateUtil.random(10));
            statistic.setT6(statistic.getT1() + statistic.getT2() + statistic.getT3() + statistic.getT4() + statistic.getT5());
            list2.add(statistic);
            Statistic statistic1 = new Statistic();
            statistic1.setDate(DateUtil.getNow("dd/MM/yyyy"));
            statistic1.setT1(DateUtil.random(10));
            statistic1.setT1(DateUtil.random(10));
            statistic1.setT2(DateUtil.random(10));
            list3.add(statistic1);
        }
    }

    @Before
    public void before() throws Exception {
        data();
        excel = ExcelBuilderFactory.construct("test Excel 1")
                .initSheet(1)
                .initTable(3)
                .createSheet(0, "test Sheet 1"
//                        , ImageIO.read(new File("G:\\GitHub\\hikaru\\hikaru-server\\test.jpg"))
//                        , ImageIO.read(new File("G:\\GitHub\\hikaru\\hikaru-server\\test2.jpg"))
                )
                .createTable(0,0, 7, 1,
                        null, "Validas", "Consultas", "Registo", "Invalidas", "Total")
                .packing(0, 0, list1, (sheetNum, tableNum, data, clazz, result) -> {
                    for (int row = 0; row < data.size(); row++) {
                        User user = data.get(row);
                        String key = row + ExcelDataCallBack.SIGN_K_V;
                        int cell = 0;
                        for (Field field : clazz.getDeclaredFields()) {
                            if (cell == clazz.getDeclaredFields().length - 1) {
                                // 计算一行总和
                                result.put(key + (cell+1), Stream.of(clazz.getDeclaredFields())
                                        .filter(field1 -> !field1.getName().equals("birthday"))
                                        .map(field1 -> ObjectUtil.getValueByFiled(user,field1))
                                        .collect(Collectors.summarizingInt(value -> Integer.parseInt(value.toString())))
                                        .getSum());
                            }
                            result.put(key + cell++, ObjectUtil.getValueByFiled(user, field));
                        }
                    }
                    return result;
                })
                .createTable(0, 1, 17, 1,
                        null, "Policia", "Transito", "Medico", "Bombeiro", "Municipal", "Total")
                .packing(0,1, list2, (sheetNum, tableNum, data, clazz, result) -> {
                    for (int row = 0; row < data.size(); row++) {
                        Statistic statistic = data.get(row);
                        String key = row + ExcelDataCallBack.SIGN_K_V;
                        result.put(key + 0, statistic.getDate());
                        result.put(key + 1, statistic.getT1());
                        result.put(key + 2, statistic.getT2());
                        result.put(key + 3, statistic.getT3());
                        result.put(key + 4, statistic.getT4());
                        result.put(key + 5, statistic.getT5());
                        result.put(key + 6, statistic.getT6());
                    }
                    return result;
                })
                .createTable(0, 2, 27, 1,
                        null, "Ocorrências em tratamento",null, null, "Ocorrências tratadas")
                .packing(0, 2, list3, (sheetNum, tableNum, data, clazz, result) -> {
                    for (int row = 0; row < data.size(); row++) {
                        Statistic statistic = data.get(row);
                        String key = row + ExcelDataCallBack.SIGN_K_V;
                        result.put(key + 0, statistic.getDate());
                        result.put(key + 1, statistic.getT1());
                        result.put(key + 4, statistic.getT2());
                        result.put(key + 6, statistic.getT6()); // 设置空数据(因为title关系不展示) v.1 设计缺陷补救， 合并单元格 与 设置样式 之间计算bug
                    }
                    return result;
                })
                .builder();
    }

    @Test
    public void test() {
        ExcelProxy.proxy(excel)
                .addMergeRegion((workbook, sheet, title, sheetNum, tableNum, tableSize, cellRangeAddressList) -> {
                    // 每列宽
                    for (int i = 0; i < 8; i++) {
                        sheet.setColumnWidth(i, i == 0 ? 5 * 256 : i == 1 ? 15 * 256 : 12 * 256);
                    }
                    HSSFCellStyle style;
                    Row row;
                    Cell cell;
                    // 行
                    row = sheet.createRow(0);
                    row.setHeight((short) 400);
                    cell = row.createCell(1);
                    cell.setCellValue("Anexo 2");

                    row = sheet.createRow(1);
                    row.setHeight((short) 880);
                    cell = row.createCell(1);
                    style = workbook.createCellStyle();
                    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                    cell.setCellValue("Estatísticas Semanais do Atendimento e Depacho às Emergências do CISP Nacional");
                    cell.setCellStyle(style);
                    cellRangeAddressList.add(new CellRangeAddress(1 ,1 ,1, 7));

                    row = sheet.createRow(2);
                    row.setHeight((short) 500);
                    cell = row.createCell(1);
                    cell.setCellValue("（22/07/2019-26/07/2019）");
                    cell.setCellStyle(style);
                    cellRangeAddressList.add(new CellRangeAddress(2, 2, 1, 7));

                    row = sheet.createRow(3);
                    row.setHeight((short) 500);
                    cell = row.createCell(1);
                    style = workbook.createCellStyle();
                    style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
                    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                    cell.setCellStyle(style);
                    cell.setCellValue("1.Dados do atendimento e despacho às Emergências ");
                    cellRangeAddressList.add(new CellRangeAddress(3, 3, 1, 7));

                    row = sheet.createRow(35);
                    row.setHeight((short) 500);
                    cell = row.createCell(1);
                    style = workbook.createCellStyle();
                    style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
                    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                    cell.setCellStyle(style);
                    cell.setCellValue("2. Estatísticas de atendimento e despacho às Emergências ");
                    cellRangeAddressList.add(new CellRangeAddress(35, 35, 1, 7));

                    row = sheet.createRow(4);
                    row.setHeight((short) 300);

                    // 多表
                    for (int x = 0; x < 3; x++) {

                        if (x != tableNum) { // 仅当前表
                            continue;
                        }

                        if (tableNum == 0) {
                            // 第一张表合并单元格
                            for (int i = 0; i <= list1.size(); i++) {
                                cellRangeAddressList.add(new CellRangeAddress(6 + i, 6 + i, 6, 7));
                            }
                        }

                        if (tableNum == 2) {
                            // 第三张表合并单元格
                            for (int i = 0; i <= list3.size(); i++) {
                                cellRangeAddressList.add(new CellRangeAddress(26 + i, 26 + i, 2, 4));
                                cellRangeAddressList.add(new CellRangeAddress(26 + i, 26 + i, 5, 7));
                            }
                        }
                        // 表之间的合并单元格
                        cellRangeAddressList.add(new CellRangeAddress(
                                x * 10 + 14, x * 10 + 14, 1, 7));

                        row = sheet.createRow(5 + x * 10);
                        style = createStyle(workbook);
                        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                        style.setFillForegroundColor(HSSFColor.GREEN.index);
                        row.setHeight((short) 400);
                        for (int i = 0; i <= 7; i++) {
                            cell = row.createCell(i);
                            if (i > 0) {
                                cell.setCellStyle(style);
                            }
                        }
                        cell = row.getCell(1);
                        cell.setCellValue("Data");
                        cellRangeAddressList.add(new CellRangeAddress(5 + 10 * x, 6 + 10 * x, 1, 1));

                        cell = row.getCell(2);
                        cell.setCellValue(x == 0 ? "Atendimento Diário às Emergências " :
                                x == 1 ? "Alarme de Emergência Válido" : "Despacho Diário às Emergências ");
                        cellRangeAddressList.add(new CellRangeAddress(5 + 10 * x, 5 + 10 * x, 2, 7));

                        row = sheet.createRow(6 + 10 * x);
                        style = createStyle(workbook);
                        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);
                        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                        style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
                        for (int i = 0; i < 7; i++) {
                            cell = row.createCell(i + 1);
                            if (i == title.length - 1 && x != 2) {
                                style = createStyle(workbook);
                                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                                style.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);
                                style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                                style.setFillForegroundColor(HSSFColor.PINK.index);
                            }
                            cell.setCellStyle(style);
                            if (i < title.length) {
                                cell.setCellValue(title[i]);
                            }
                        }
                        row.setHeight((short) 500);
                    }
                    return cellRangeAddressList;
        })
                .initStyle((workbook, styles) -> {
                    for (int i = 0; i < 3; i++) {
                        HSSFCellStyle style = createStyle(workbook);
                        if (i == 0) {
                            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                        } else if (i == 1) {
                            style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                        } else {
                            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                            style.setFillForegroundColor(HSSFColor.PINK.index);
                        }
                        styles.add(style);
                    }
                    return styles;
                })
                .getCellStyle((row, styles, rowNum, cellNum, size, sheetNum, tableNum) -> {
                    row.setHeight((short) 450);
                    if (cellNum == 0) {
                        if (rowNum <= size - (tableNum != 2 ? 2 : 1)) {
                            return styles.get(0);
                        } else {
                            return styles.get(2);
                        }
                    } else {
                        return styles.get(1);
                    }
                })
                .processingImg((num, anchors) -> {
                    anchors[0] = new HSSFClientAnchor(0,0,0,0, (short) 1, 37, (short)4, 49);
                    anchors[1] = new HSSFClientAnchor(0,0,0,0, (short) 4, 37, (short)8, 49);
                    return anchors;
                })
                .produce();
    }

    private HSSFCellStyle createStyle(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();
        border(style);
        return style;
    }

    private void border(HSSFCellStyle style) {
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    }

}
