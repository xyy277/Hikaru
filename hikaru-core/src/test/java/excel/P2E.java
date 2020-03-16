package excel;

import org.junit.Before;
import org.junit.Test;
import savvy.wit.framework.core.base.callback.ExcelDataCallBack;
import savvy.wit.framework.core.pattern.adapter.FileAdapter;
import savvy.wit.framework.core.pattern.factory.ExcelBuilderFactory;
import savvy.wit.framework.core.pattern.proxy.ExcelProxy;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : P2E
 * File name : P2E
 * Author : zhoujj
 * Date : 2020/3/16 22:47
 * Version : 1.0
 * Description : 
 ******************************/
public class P2E {

    Map<String, Object> data = new HashMap<>();

    @Before
    public void before() {
        List<String> list = new ArrayList();
        FileAdapter.me().readFile( (br, bw) -> {
            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.equals("")) {
                    continue;
                }
                if ((!line.contains("{")) || (line.contains("{") && line.contains(":"))) {
                    list.add(line);
                }
            }
        }, new File("D:\\WorkingSpace\\company\\gsafety\\doc\\zh-CN.json"));
        String end = "}";
        int row = 0;
        List<String> cacheKey = new ArrayList<>();
        for (int index = 0; index < list.size(); index++) {

            // 每行字符串 是否添加 是否是树形结构---------------------
            String line = list.get(index);
            // start
            if (line.contains("{") && line.contains(":")) {
                cacheKey.add(line.split(":")[0]); // 添加到key
                continue; // 跳过
            }
            // end
            if (line.contains(end) && cacheKey.size() > 0) {
                cacheKey.remove(cacheKey.size() -1);
                continue;
            } else if (line.contains(end) && cacheKey.size() == 0) {
                continue;
            }

            // 计算单元格位置 ----------------------------------------
            String position = row + ExcelDataCallBack.SIGN_K_V;
            row ++;
            // 存数据
            String[] cells = new String[2];
            // 配置文件key的树形结构 test.ok.yes...
            StringBuilder data_key = new StringBuilder();
            for (int i = 0; i < cacheKey.size(); i++) {
                data_key.append(cacheKey.get(i));
                if (i <= cacheKey.size() - 1)
                    data_key.append(".");
            }
            // 存储数据
            cells[0] = data_key.append(line.split(":")[0]).toString();
            cells[1] = line.split(":")[1];
            for (int cell = 0; cell < cells.length; cell++) {
                data.put(position + cell, cells[cell].replaceAll("\"", "").replaceAll(" ", "").replaceAll(",", ""));
            }
        }
    }

    @Test
    public void test() {
        ExcelProxy.proxy(ExcelBuilderFactory.construct("test").simple("sheet1", data, "key", "zh-CN").builder()).produce();
    }
}
