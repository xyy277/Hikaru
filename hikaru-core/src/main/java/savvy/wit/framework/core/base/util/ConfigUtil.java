package savvy.wit.framework.core.base.util;

import savvy.wit.framework.core.base.interfaces.Log;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ConfigUtil
 * Author : zhoujiajun
 * Date : 2018/6/28 10:59
 * Version : 1.0
 * Description : 
 ******************************/
public class ConfigUtil {

    private Log log = LogFactory.getLog();

    public static ConfigUtil me(String fileName ) {
        return new ConfigUtil(fileName);
    }

    public static ConfigUtil me (Properties properties) {
        return new ConfigUtil(properties);
    }


    Properties properties;
    private static ResourceBundle bundle=null;
    public ConfigUtil() {

    }

    public ConfigUtil(Properties properties) {
        this.properties = properties;
    }

    public ConfigUtil(String fileName) {
        try {
            /**
             * 获取配置文件value方式1
             * 因为ResourceBundle读取配置文件时不需要加后缀
             * 所以需要对文件名做一些处理才能使得两种方式都能很好的被使用
             */
            InputStream inputStream=ConfigUtil.class.getResourceAsStream(fileName);
            if (properties == null) {
                properties  = new Properties();
            }
            properties.load(inputStream);
//            bundle=ResourceBundle.getBundle(fileName.substring(fileName.lastIndexOf("/")+1, fileName.lastIndexOf(".")));
        } catch (Exception e) {

        }
    }
    /**
     * 用Properties获取配置文件内容
     * @param key
     * @return properties.getProperty(key)
     */
    public String getValue(String key){
        String value = properties.getProperty(key);
        return value;
    }
    /**
     * 用ResourceBundle获取配置文件内容
     * @param key
     * @return bundle.getString(key)
     */
    public String getBundleValue(String key){
        return bundle.getString(key);
    }
}
