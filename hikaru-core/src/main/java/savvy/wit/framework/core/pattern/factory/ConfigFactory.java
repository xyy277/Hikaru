package savvy.wit.framework.core.pattern.factory;

import savvy.wit.framework.core.base.util.Scanner;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 数据源配置工厂
 * File name : ConfigFactory
 * Author : zhoujiajun
 * Date : 2019/1/10 9:20
 * Version : 1.0
 * Description : 动态可扩展配置数据源
 ******************************/
public class ConfigFactory {

    private Properties properties;

    private List<Class<?>> enumClassList = new ArrayList<>();

    public static ConfigFactory me() {
        return LazyInit.INITIALIZATION;
    }

    private static class LazyInit {
        private static final ConfigFactory  INITIALIZATION = new ConfigFactory();
    }

    protected ConfigFactory (){}

    protected ConfigFactory(Properties properties) {

    }

    public Properties getProperties() {
        return properties;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public List<Class<?>> getEnumClassList() {
        return enumClassList;
    }

    public void setProperties(Properties properties) {
        if (this.properties == null)
            this.properties = new Properties();
        Enumeration enumeration = properties.propertyNames();//得到配置文件的名字
        while(enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            String value = properties.getProperty(key);
            this.properties.setProperty(key, value);
        }
    }

    /**
     * 可以不依赖配置文件直接在初始化时设置param参数
     * @param key
     * @param value
     * @return
     */
    public ConfigFactory setProperty(String key, String value) {
        if (this.properties == null) {
            this.properties = new Properties();
        }
        this.properties.setProperty(key, value);
        return LazyInit.INITIALIZATION;
    }

    /**
     * 扫描并设置Enum类，为dao初始化流程之一
     * @param pack
     * @return
     */
    public ConfigFactory setEnumClassList(String... pack) {
        this.enumClassList.addAll(Scanner.scanning(pack));
        return LazyInit.INITIALIZATION;
    }

    public ConfigFactory setSource(Properties... properties) {
        for (Properties property : properties) {
            setProperties(property);
        }
        return LazyInit.INITIALIZATION;
    }

    /**
     * 后者覆盖前者方式加载
     * @param paths
     */
    public ConfigFactory setSource(String... paths) {
        InputStream inputStream = null;
        properties = properties == null ? new Properties() : properties;
        for (String path : paths) {
            path = path.trim();
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
                }
            }else {
                try {
                    properties.load(inputStream);
                }catch (IOException e) {

                }
            }

        }
        return LazyInit.INITIALIZATION;
    }

}
