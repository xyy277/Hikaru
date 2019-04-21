package savvy.wit.framework.core.base.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.GetConnectionTimeoutException;
import savvy.wit.framework.core.algorithm.model.key.KeyStore;
import savvy.wit.framework.core.algorithm.model.key.RSAKeyFactory;
import savvy.wit.framework.core.pattern.factory.ConfigFactory;

import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

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

    private static final String PRIVATE_KEY = KeyStore.RSA_PRIVATE_KEY;

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
        RSAPrivateKey privateKey = null;
        try {
            privateKey = RSAUtil.getPrivateKey(PRIVATE_KEY);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        Properties config = ConfigFactory.me().getProperties();
        driver = config.getProperty("driver");
        url = config.getProperty("url");
        user = config.getProperty("user");
        password = config.getProperty("password");
        password = RSAUtil.privateDecrypt(password, privateKey);
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
        try {
            Class.forName(driver);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
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
