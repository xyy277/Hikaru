package zjj;

import org.junit.Before;
import org.junit.Test;
import savvy.wit.framework.core.base.interfaces.Log;
import savvy.wit.framework.core.pattern.adapter.ScExcelAdapter;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ExcelTets
 * Author : zhoujiajun
 * Date : 2018/9/27 15:27
 * Version : 1.0
 * Description : 
 ******************************/
public class ExcelTest {
    protected Log log = LogFactory.getLog();

    private List<Map<String,String>> list = new ArrayList<>();

    @Before
    public void before() {
        try {

//            int a =  (Integer) ScExcelAdapter.NEW().readExcel("/excel/test1.xlsx", (mapList) -> {
//                List<Map> maps = new ArrayList<>();
//                mapList.forEach(map -> {
//                    if ( !map.get("CELL3").equals(map.get("CELL5"))) {
//                        map.remove("CELL2");
//                        map.remove("CELL4");
//                        maps.add(map);
//                    }
//                });
//                return maps.size();
//            }).getResult();
//            log.log(a);


            list = (List<Map<String,String>>) ScExcelAdapter.NEW().readExcel(
                    "/excel/test.xlsx",
                    (mapList, adapter) -> {
                        List<Map> maps = new ArrayList<>();
                        mapList.forEach(map -> {
                            if ( !map.get("CELL3").equals(map.get("CELL5"))) {
                                map.remove("CELL2");
                                map.remove("CELL4");
                                maps.add(map);
                            }
                        });
                        return maps;
                    }).getResult();
//            log.log(list);
            log.log(list.size());


//            String [] titles = new String[] {"index","键","C","E"};
//            String [] keys = new String[] {"CELL0","CELL1","CELL3","CELL5"};
////            ScExcelAdapter.NEW().write2Excel("gis",titles,keys,list);
//            ScExcelAdapter.NEW().read2Write("/excel/test.xlsx","gis", titles, keys,(mapList) -> {
//                List<Map> maps = new ArrayList<>();
//                mapList.forEach(map -> {
//                    if ( !map.get("CELL3").equals(map.get("CELL5"))) {
//                        map.remove("CELL2");
//                        map.remove("CELL4");
//                        maps.add(map);
//                    }
//                });
//                return maps;
//            });
        }catch (Exception e) {
            log.log(e);
        }
    }

//    @Test
    public void test() {

//        FileAdapter.NEW().readFile("test/pt_PT-test.json", true, "UTF-8" , "UTF-8", (br, bw) -> {
//            String line;
//            boolean last = false;
//            while ((line = br.readLine()) != null) {
//                if (String.valueOf(line.charAt(line.length() - 1)).equals(",")) {
//                    last = true;
//                }
//                // 判断是否需要比较
//                if (line.indexOf("{") == -1 && line.indexOf("}") ==-1 && line.indexOf(":") != -1) {
//                    if (last) {
//                        line = line.substring(0, line.length() - 1); // 去除末位
//                    }
//                    String [] result;
//                    // value 限制
//                    if (line.indexOf(":") > 1) {
//                        result = line.split(":");
//                        for (int var = 2; var < result.length; var++) {
//                            result[1]  += result[var];
//                        }
//                    } else {
//                        result = line.split(":");
//                    }
//                    for (Map map : list) {
//                        String id = (String) map.get("CELL1");
//                        id = "\"" + id + "\"";
//                        // key比较
//                        if (id.trim().equals(result[0].trim())) {
//                            String value = (String) map.get("CELL3");
//                            result[1] = "\""+ value + "\"";
//                            break; // 减少循环次数，但出现极少数key相同清空下 需要人为处理
//                        }
//                    }
//                    // 拼接json
//                    line = result[0] + ": " + result[1] ;
//                    // 是否加 ,
//                    if (last) {
//                        last = !last;
//                        line = line + ",";
//                    }
//                }
//                bw.write(line + "\n");
//            }
//        },"D:\\WorkSpace\\Test\\Personal\\Java\\my-awesome-project\\src\\main\\resources\\excel\\pt_PT.json");



//        List<String> keys = new ArrayList<>();
//        FileAdapter.NEW().readFile(null, true, "UTF-8" , null, (br, bw) -> {
//            String line;
//            while ((line = br.readLine()) != null) {
//
//            }
//        },"D:\\WorkSpace\\Test\\Personal\\Java\\my-awesome-project\\src\\main\\resources\\excel\\pt_PT.json");

//        mapList.forEach(map -> {
//            String value = map.get("CELL1") + ":" + map.get("CELL3") + ",";
//            FileAdapter.me().writer("target.txt",value);
//        });
//        log.log(mapList.size());

//        Object o = ScExcelAdapter.NEW().readExcel("/excel/test.xlsx", list1 -> {
//            list1.parallelStream().filter( map -> map.get("CELL3").equals( map.get("CELL5") ) )
//                    .sorted((o1, o2) -> Integer.parseInt(o1.get("CELL0"))- Integer.parseInt(o2.get("CELL0")))
//                    .collect(Collectors.toList());
//            return list1;
//        }).getResult();
//        log.log(o);


//        list.parallelStream()
//                .filter( map ->{
//                 if ( !map.get("CELL3").equals(map.get("CELL5"))) {
//                     log.log(map);
//                     map.remove("CELL2");
//                     map.remove("CELL4");
//                     map.remove("CELL5");
//                     return true;
//                 }
//                 log.warn(map);
//                 return  false;
//                })
//                .sorted((o1, o2) -> Integer.parseInt(o1.get("CELL0"))- Integer.parseInt(o2.get("CELL0")))
//                .collect(Collectors.toList());
//        log.log(list.size());

    }
}
