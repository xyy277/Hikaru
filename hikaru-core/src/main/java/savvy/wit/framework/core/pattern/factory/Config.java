package savvy.wit.framework.core.pattern.factory;

import savvy.wit.framework.core.base.util.ConfigUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Config
 * Author : zhoujiajun
 * Date : 2018/10/24 9:13
 * Version : 1.0
 * Description : 
 ******************************/
public class Config {

    public Config () {

    }

    private static ConfigUtil config;

    private static String fileName;

    public static Config init (String name) {
        fileName = name;
        config = ConfigUtil.me(name);
        return new Config();
    }

    public String getValue(String key) {
        String value = config.getValue( key );
       return value == null ? "" : value.replaceAll(",", "");
    }

    public Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();

        return map;
    }

    public Properties getProperties() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = Config.class.getResourceAsStream(fileName);
            properties.load(inputStream);
        } catch (IOException e) {

        }
        return properties;
    }

}
