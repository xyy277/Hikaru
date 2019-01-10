package savvy.wit.framework.core.pattern.factory;

import java.util.Properties;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : DbFactory
 * Author : zhoujiajun
 * Date : 2019/1/10 9:20
 * Version : 1.0
 * Description : 
 ******************************/
public class DbFactory {

    private Properties properties;

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

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
