package zjj;

import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.base.util.JsonUtil;
import savvy.wit.framework.core.base.util.MapUtil;
import savvy.wit.framework.core.pattern.decorate.Counter;
import savvy.wit.framework.core.pattern.factory.LogFactory;
import savvy.wit.framework.core.structure.physical.loopStructure.circle.Circle;
import savvy.wit.framework.core.structure.physical.shape.Point;

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
        initPool();
        Counter counter = Counter.create();
        for (char t : s.toCharArray()) {
            for (char c : chars) {
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
        Circle circle = new Circle();
        circle.setCenter(new Point(300,300));
        double arc = 555;
        double r = 300;
        double precision = 1;
        double radian = 60;
        // -------------------------------------------------------------
        circle.setArc(arc);
        circle.setR(r);
        circle.setPrecision(precision);
        circle.setRadian(radian);
        log.log(JsonUtil.toJson(circle));
        log.log(JsonUtil.toJson(circle.getCenter()));
//        String s = "consul agent -SERVER -bootstrap-expect 2 -DATA-dir C:\\Users\\Administrator\\Desktop\\consul -node=n4 -bind=192.168.67.241 -ui-dir C:\\Users\\Administrator\\Desktop\\consul\\dist -dc=dc1 -CLIENT=0.0.0.0 &";
//        s = "%E4%B8%BB%E7%AE%A1";
//        String[] strings = new String[1];
//        List<String> list = new ArrayList<>();
//        System.out.println(list.getClass().getSimpleName());
//        System.out.println(strings.getClass().getSimpleName());
//        log.log(Strings.transformEncoding(s, "ISO-8859-1", "utf-8"));
//        StringBuilder sql = new StringBuilder("create table if not exists `user` ( \n" +
//                " `id`  varchar(255)  NOT NULL COMMENT 'id' ,\n" +
//                " `name`  varchar(255)  NOT NULL COMMENT '姓名' ,\n" +
//                " `age`  int(255)  NOT NULL COMMENT '年龄' ,\n" +
//                " `opt_time`  varchar(255)  NOT NULL COMMENT '操作时间' ,\n" +
//                " `opt_user`  varchar(255)  NOT NULL COMMENT '操作人',\n");
//        sql.replace(sql.lastIndexOf(","), sql.lastIndexOf(",") + 1, " ");
//        log.log(        sql.charAt(sql.lastIndexOf(",")));
////        sql.delete(sql.lastIndexOf(","), sql.lastIndexOf(",") + 1);
//        log.log(sql.lastIndexOf(","));
//        log.log(sql);

//        Map<String, Object> map = new HashMap<>();
//        MapUtil.sortByKey(map, (o1, o2) -> o1.compareTo(o2));
//        MapUtil.sortByValue(map, (o1, o2) -> o1.getValue().toString().compareTo(o2.getValue().toString()));
//        log.log(        Files.getEncoding("G:\\GitHub\\hikaru\\hikaru-server\\hikaru-common\\src\\main\\java\\com\\gsafety\\hikaru\\common\\application\\ApplicationBeanFactory.java"));
//        FileAdapter.me().readLine("G:\\GitHub\\hikaru\\hikaru-server\\hikaru-common\\src\\main\\java\\com\\gsafety\\hikaru\\common\\application\\ApplicationBeanFactory.java",
//                string -> log.log(string));

//        FileAdapter.me().readFile(null, false, "utf8", "", (bufferedReader, bufferedWriter) -> {
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                log.log(line + "\n");
//            }
//        }, "G:\\GitHub\\hikaru\\hikaru-server\\hikaru-common\\src\\main\\java\\com\\gsafety\\hikaru\\common\\application\\ApplicationBeanFactory.java");

//        FileAdapter.me().readLine(
//                "G:\\GitHub\\hikaru\\hikaru-server\\hikaru-application\\src\\main\\resources\\datasource\\db.properties",
//                "utf-8", string -> System.out.println(string));


//        for (File file: Files.getFiles("G:\\GitHub\\hikaru\\hikaru-server\\install\\shell")) {
//            FileAdapter.me().readFile(
//                    FileUtil.me().create(file.getParent() + "/" + "1_" + file.getName()),
//                    true,
//                    "GBk",
//                    "utf-8",
//                    (bufferedReader, bufferedWriter) -> {
//                        log.log(file.getName());
//                        String line;
//                        while ((line = bufferedReader.readLine()) != null) {
//                            bufferedWriter.write(line + "\n");
//                        }
//                        bufferedWriter.flush();
//                    },
//                    file);
//            FileUtil.me().delete(file);
//        }
    }
}
