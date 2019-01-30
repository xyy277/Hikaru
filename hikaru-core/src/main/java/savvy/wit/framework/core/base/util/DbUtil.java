package savvy.wit.framework.core.base.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.GetConnectionTimeoutException;
import savvy.wit.framework.core.pattern.factory.DbFactory;

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

    private static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKfp2t7kKSunGZTOH9Rw8QrElrDCHWENpHmlBLRblWyRJgL4CrpIxjPsoiYt63i4iSpVRV9f_TckxRnpgM28Qdzpkveywp7PTwscrNBs820C9xwMLP_Rymqnp0ulJgJ3QS-v4PajDkfGwObvSRf6qzMbwT8vczXE7bn7W68mz7CjAgMBAAECgYAWpDTsGy3mAfk0FHs8RSJrhkifev1FKfbEpFi3DSZf5k6O1e10Yv2-4KiYp1Z6B8APIkJhcusM9XtSXCcPFtR2gbJk7DPkUZvzJDW3uLMwJct9IGFGc20lwD-wQg8xGZEcapFmmiTBusfa1qfDP19pxV5RI9aiz3yRxhCLrdmvuQJBAPNamSsTOCTNjZHuVg33W5uauD5x54Ga92kRiUsdY28KEs8Mj6qxWIT6z7TJbJigvW_e5xc8IVk0h8kzbDwtv6cCQQCwo6cjZkSVd2qXb8VcJfYzxwcE9po4JxKt0EqDiItPpaFkaaVpa0cpoCb5Ea00QCKfkdpsDujn2FSQWwXPKMalAkEA1Q-2WOtb1YUOdP0J5PJ0BzmgJDQP7_5grWIIJXbWjupv601hH55kFRGO9wb_iGX7Vc3_3-zqebKvS-40zj9zswJAcS3rAfudJkgFvFKZmpmYT0TPcpav6hrfFQ_JLs5mtPnjl3s5yXo7dqhvUFfLKxeNF8FUurgy85rhVD1-U2ZFIQJAG4RRuIE9mx4NscEiDOmrm8-U_dGwbJq1KUr-IexPT-icr4YqUAu0sCr_f42ao47mHK_bQjHC7PMzw99EhzD9eA";

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
        Properties config = DbFactory.me().getProperties();
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
