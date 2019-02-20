package zjj;

import org.junit.Before;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.adapter.ScExcelAdapter;
import savvy.wit.framework.core.pattern.decorate.Counter;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ListTest
 * Author : zhoujiajun
 * Date : 2018/10/8 14:08
 * Version : 1.0
 * Description : 
 ******************************/
public class ListTest {


    private Log log = LogFactory.getLog();

    private static Object use(List<Map<String, String>> mapList, ScExcelAdapter adapter) {
        Map<String, Integer> map = new HashMap<>();
        for (int var = 0; var < mapList.size(); var++) {
            String key = mapList.get(var).get(adapter.getCellNum(1));
            map.put(key, !map.containsKey(key) ? 1 : map.get(key) + 1);
        }
        return map;
    }

    @Before
    public void before() {

    }

//    @Test
    public void test() {
        Map<String, Integer> keys = (Map<String, Integer>)
                ScExcelAdapter.NEW().readExcel("/excel/test.xlsx", ListTest::use).getResult();
        List<String> list = new ArrayList<>();
        Counter counter = Counter.create();
        keys.forEach((s, integer) -> {
            if (integer > 1) {
                counter.setCount(counter.getCount() + integer);
                list.add(s + ":::\t" + integer);
            }
        });
//        list.forEach(s -> counter.setCount(counter.getCount(), Integer.parseInt(s.split(":::")[1].trim())));
        log.log(keys.size());
        log.log(list.size());
        log.log(counter.getCount());
        log.log(list.stream()
                .sorted((o1, o2) -> Integer.parseInt(o1.split(":::")[1].trim()) - Integer.parseInt(o2.split(":::")[1].trim()))
                .collect(Collectors.toList()));
    }
}
