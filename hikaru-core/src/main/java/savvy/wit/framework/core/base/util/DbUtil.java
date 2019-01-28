package savvy.wit.framework.core.base.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.GetConnectionTimeoutException;
import savvy.wit.framework.core.base.interfaces.Log;
import savvy.wit.framework.core.pattern.factory.DbFactory;
import savvy.wit.framework.core.pattern.factory.LogFactory;
import savvy.wit.framework.core.pattern.adapter.TimerAdapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.TimerTask;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title :
 * File name : DbUtil
 * Author : zhoujiajun
 * Date : 2018/6/29 21:34
 * Version : 1.0
 * Description :
 ******************************/
public class DbUtil {

    public DbUtil() {
        init();
    }

    private static class LazyInit{
        private static final DbUtil INITIALIZATION = new DbUtil();
    }

    public static DbUtil me() {
        return LazyInit.INITIALIZATION;
    }

    private DruidDataSource dataSource = null;
    private String driver = "";
    private String url = "";
    private String user = "";
    private String password = "";
    private int initialSize = 0;//初始化连接数
    private int maxIdle = 0;//最大空闲连接数
    private int minIdle = 0;//最小空闲连接数
    private int maxTotal = 0;//最大连接数
    private int maxWaitMillis = 0;//最大等待时间

    private void init() {
        try {
            Properties config = DbFactory.me().getProperties();
            driver = config.getProperty("driver");
            url = config.getProperty("url");
            user = config.getProperty("user");
            password = config.getProperty("password");
            initialSize = Integer.parseInt(config.getProperty("initialSize"));
            maxIdle = Integer.parseInt(config.getProperty("maxIdle"));
            minIdle = Integer.parseInt(config.getProperty("minIdle"));
            maxTotal = Integer.parseInt(config.getProperty("maxTotal"));
            maxWaitMillis = Integer.parseInt(config.getProperty("maxWaitMillis"));
            dataSource = new DruidDataSource();
            dataSource.setUrl(url);
            dataSource.setUsername(user);
            dataSource.setPassword(password);
            dataSource.setInitialSize(initialSize);
            dataSource.setMaxWait(maxIdle);
            dataSource.setMinIdle(minIdle);
            dataSource.setMaxActive(maxTotal);
            dataSource.setLoginTimeout(maxWaitMillis);
            Class.forName(driver);
        }catch (ClassNotFoundException e){

        }
    }


    public synchronized Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        }catch (GetConnectionTimeoutException e) {
        }
        return connection;
    }

    public void close(Connection connection, PreparedStatement preparedStatement) throws SQLException {
        try {
            if(null != preparedStatement){
                preparedStatement.close();
            }
            if(null != connection) {
                connection.close();
            }
        }catch (SQLException e) {
        }
    }


}
