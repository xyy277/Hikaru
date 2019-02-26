package savvy.wit.framework.core.pattern.factory;

import savvy.wit.framework.core.base.util.Scanner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 数据源配置工厂
 * File name : DbFactory
 * Author : zhoujiajun
 * Date : 2019/1/10 9:20
 * Version : 1.0
 * Description : 动态可扩展配置数据源
 ******************************/
public class DbFactory {

    private Properties properties;

    private List<Class<?>> enumClassList;

    public List<Class<?>> getEnumClassList() {
        return enumClassList;
    }

    public void setEnumClassList(String... pack) {
        this.enumClassList = Scanner.scanning(pack);
    }

    public static DbFactory me() {
        return LazyInit.INITIALIZATION;
    }

    private static class LazyInit {
        private static final DbFactory  INITIALIZATION = new DbFactory();
    }

    private DbFactory (){}

    private DbFactory(Properties properties) {

    }

    public Properties getProperties() {
        return properties;
    }

    public void setSource(Properties properties) {
        this.properties = properties;
    }

    /**
     * 先入先加载的方式
     * 按顺序加载，加载成功则停止加载
     * 不会覆盖
     * @param paths
     */
    public void setSource(String... paths) {
        InputStream inputStream = null;
        Properties properties = new Properties();
        for (String path : paths) {
            try {
                inputStream =new BufferedInputStream(new FileInputStream(path));
            }catch (FileNotFoundException e) {

            }
            if (inputStream == null) {
                inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
                if (inputStream == null) {
                    continue;
                } else {
                    try {
                        properties.load(inputStream);
                    }catch (IOException e) {

                    }
                    break;
                }
            }else {
                try {
                    properties.load(inputStream);
                }catch (IOException e) {

                }
                break;
            }

        }
        this.properties = properties;
    }
}
