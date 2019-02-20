package zjj;

import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.adapter.Excel2PropertiesAdapter;
import savvy.wit.framework.core.pattern.adapter.ScExcelAdapter;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : FileTest
 * Author : zhoujiajun
 * Date : 2018/8/3 16:06
 * Version : 1.0
 * Description : 
 ******************************/
public class FileTest {
    protected Log log = LogFactory.getLog();


//    @Test
    public void write() {
        new Excel2PropertiesAdapter() {
            @Override
            public Object use(List<Map<String, String>> mapList, ScExcelAdapter adapter) {
                List<Map> list = new ArrayList<>();
                mapList.forEach(map -> {
                    map.remove(adapter.getCellNum(2));
//                    list.add(adapter.sortMapKey(map,(o1, o2) -> o1.compareTo(o2)));
//                    list.add(adapter.sortMapValue(map,(o1, o2) -> o1.getValue().compareTo(o2.getValue())));
                });
                log.log(list);
                return  list;
            }

            @Override
            public String[] compareMap2Replace(String[] result, List<Map<String, String>> maps) {
                for ( Map map : maps ) {
                    String zhCN = (String) map.get("CELL1");
                    zhCN = "\"" + zhCN.trim() + "\"";
                    // key比较相等
                    if (zhCN.equals(result[1].trim())) {
                        String value = (String) map.get("CELL3");
                        result[1] = "\""+ value.trim() + "\"";
                        break;
                    }
                }
                return result;
            }

//            @Override
//            public void excel2Properties(String targetPath, String contentPath, String compareJsonPath) {
//                log.log(compareJsonPath);
//            }
        }.excel2Properties("test1.json",
                "/excel/qqqq.xlsx",
                "D:\\WorkSpace\\Test\\Personal\\Java\\zhoujiajun\\src\\main\\resources\\excel\\en-US.json");



//        @Override
//        public String[] compareMap2Replace(String[] result, List<Map<String, String>> maps){
//            for (Map map : maps) {
//                String id = (String) map.get("CELL1");
//                id = "\"" + id.trim() + "\"";
//                // key比较相等
//                if (id.equals(result[0].trim())) {
//                    String value = (String) map.get("CELL3");
//                    result[1] = "\"" + value.trim() + "\"";
//                    if (null == counter.getValue(id))
//                        counter.setValue(id, result[1]);
//                    result[1] = result[1].length() < counter.getValue(id).toString().length() ?
//                            result[1] : counter.getValue(id).toString(); // 取最短值
//                    break;
//                }
//            }
//        }

//        }.excel2Properties("test3.json",
//                "/excel/test1.xlsx",
//                "D:\\WorkSpace\\Test\\Personal\\Java\\zhoujiajun\\src\\main\\resources\\excel\\pt_PT_client.json");



//        Dec_tenth.NEW().excel2Properties("test.json",
//                "/excel/yjpg.xlsx",
//                "D:\\WorkSpace\\Test\\Personal\\Java\\zhoujiajun\\src\\main\\resources\\excel\\en-US.json");
    }
}
