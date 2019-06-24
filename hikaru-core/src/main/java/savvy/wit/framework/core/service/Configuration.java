package savvy.wit.framework.core.service;

import java.util.Properties;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Configuration
 * Author : zhoujiajun
 * Date : 2019/6/20 17:11
 * Version : 1.0
 * Description : 
 ******************************/
public interface Configuration extends DbConfiguration {

    Properties getProperties();

    String getProperty(String key);

    Configuration setProperties(Properties properties);

    Configuration setProperty(String key, String value);

    Configuration setSource(Properties... properties);

    Configuration setSource(String... paths);

}
