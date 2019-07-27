package test;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.util.HSSFColor;
import savvy.wit.framework.core.base.callback.ExcelStyleCallBack;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.base.util.DateUtil;
import savvy.wit.framework.core.base.util.ImageUtil;
import savvy.wit.framework.core.base.util.ScExcelUtils;
import savvy.wit.framework.core.base.util.StringUtil;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ExcelTest
 * Author : zhoujiajun
 * Date : 2019/7/27 8:48
 * Version : 1.0
 * Description : 
 ******************************/
public class ExcelTest {

    private static Log log = LogFactory.getLog();


    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        String[] titles = new String[]{"序号","名称"};
        list.add(StringUtil.createCode());
        String[] headers = new String[] {DateUtil.getNow("dd/MM/yyyy") + "-" + DateUtil.getNow("dd/MM/yyyy")};
        ScExcelUtils.getExcel(null, "导出报表测试", headers ,list, titles, new String[]{"制表人：zhoujj"}, true, (workbook, styles) -> {
            HSSFCellStyle header = workbook.createCellStyle();
            header.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            header.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            header.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
            header.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            styles.put(ExcelStyleCallBack.HEADER,header);
            HSSFCellStyle footer = workbook.createCellStyle();
            footer.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            footer.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            footer.setFillForegroundColor(HSSFColor.DARK_YELLOW.index);
            footer.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            styles.put(ExcelStyleCallBack.FOOTER,footer);
            return styles;
        }, (string, count) -> {
            String[] strings = new String[2];
            strings[0] = String.valueOf(count);
            strings[1] = string;
            return strings;
        }, anchors -> {
            for (int i = 0; i < anchors.length; i++) {
                anchors[i] = new HSSFClientAnchor(0, 0, 0, 0,
                        (short) 0, 1 + 2*i, (short) 2, 3 + 2*i);
            }
            return anchors;
        }, ImageUtil.me().draw(400, 400, 1, (graphics, image) -> {
            image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
            //整个大的格子为白色
            graphics.setColor(Color.white);
            graphics.fillRect(0, 0, 10, 10);
            //将大的格子拆分成一个田字。第一个口填充为红色，第二个口填充为绿色
            graphics.setColor(Color.red);
            graphics.fillRect(0, 0, 5, 5);
            graphics.setColor(Color.green);
            graphics.fillRect(5, 5, 5, 5);
            Rectangle2D r1= new Rectangle2D.Double(0,0,10,10);
            Ellipse2D e2 =new Ellipse2D.Double(150, 60, 90, 90);
            // bi 是绘图的样式 ， r1 是绘图的小矩形
            TexturePaint tp = new TexturePaint(image, r1);
            Graphics2D g2 = (Graphics2D) graphics;
            g2.setPaint(tp);
            g2.fill(e2);
        }).getImage(), ImageUtil.me().draw(400, 400, 1, (graphics, image) -> {

        }).getImage());
    }

}
