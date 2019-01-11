package zjj;

import savvy.wit.framework.core.base.interfaces.Log;
import savvy.wit.framework.core.base.util.MapUtil;
import savvy.wit.framework.core.pattern.adapter.FileAdapter;
import savvy.wit.framework.core.pattern.decorate.Counter;
import savvy.wit.framework.core.pattern.factory.Files;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.util.HashMap;
import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Test
 * Author : zhoujiajun
 * Date : 2019/1/7 12:54
 * Version : 1.0
 * Description : 
 ******************************/
public class Test {

    private static Log log = LogFactory.getLog();
    private static char[] chars = null; // 测试


    public static void test(String s) {
        s.indexOf("");
        s.lastIndexOf("");
        initPool();
        Counter counter = Counter.create();
        for (char t : chars) {
            for (char c : s.toCharArray()) {
                if (c == t) {
                    int count = counter.getValue(String.valueOf(t)) == null ? 0 : (int) counter.getValue(String.valueOf(t));
                    counter.setValue(String.valueOf(t), count + 1);
                }
            }
        }
        counter.setValue( MapUtil.sortMapValue( counter.getValue(), (o1, o2) -> ( (Integer)o1.getValue() ).compareTo( (Integer) o2.getValue() ) ) );
        log.log(counter.getValue());
    }

    private static void initPool() {
        chars = new char[26 * 2 + 10];
        int index = 0;
        for (int var = 65; var < 65 + 26; var ++) {
            if (index < 10) {
                chars[index] = ("" + index).charAt(0);
            }
            chars[index + 10] = (char) var;
            chars[index + 10 + 26] = (char) (var + 32);
            index++;
        }
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        MapUtil.sortByKey(map, (o1, o2) -> o1.compareTo(o2));
        MapUtil.sortByValue(map, (o1, o2) -> o1.getValue().toString().compareTo(o2.getValue().toString()));
        log.log(        Files.getEncoding("G:\\GitHub\\hikaru\\hikaru-server\\hikaru-common\\src\\main\\java\\com\\gsafety\\hikaru\\common\\application\\ApplicationBeanFactory.java"));
//        FileAdapter.me().readLine("G:\\GitHub\\hikaru\\hikaru-server\\hikaru-common\\src\\main\\java\\com\\gsafety\\hikaru\\common\\application\\ApplicationBeanFactory.java",
//                string -> log.log(string));
        FileAdapter.me().readFile(null, false, "utf8", "", (bufferedReader, bufferedWriter) -> {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                log.log(line + "\n");
            }
        }, "G:\\GitHub\\hikaru\\hikaru-server\\hikaru-common\\src\\main\\java\\com\\gsafety\\hikaru\\common\\application\\ApplicationBeanFactory.java");
    }
}
