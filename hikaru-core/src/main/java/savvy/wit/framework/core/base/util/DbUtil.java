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

    private static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANgcE4lZjwXJd_Ab1_zJKGnhQap" +
            "JhLHWZyrpTHDOyVC70x2XacTERVgr41iFzT5NEfBdEVUFuOCrEvKSnZgxc3_bgI05ihDVQGNmhjHbjN0CnbWjRIj61WPWX3h4mIZUbV" +
            "Z5c_URG71ZCAYB7QvyefUiC5g4SJrtHKawbfDd7pzRAgMBAAECgYB-1SOGJcCx1ccFbWH64xhL478FMPam5L1RyvBo-IPgVQuXP2j86" +
            "XAFSmS4bHbDjLnthsGIU75oZR7OrMu_Ka6tk5L_PrkKcBFPnhvZ9I0h-C7X460T2105i6KI_iqh5EA177Vn0ZOzpxBJfhiAkCAS-z9N" +
            "GukCtT1fOQJ9NXSXTQJBAP0KvoFj1tZbIaI6UskPS9PFXAlADPiXIOfhO1KbxZ79Oc-cK1zcOmlGIBwzDNCI63hG3CD5u7OAv_uDXFj" +
            "cv2sCQQDaos7moZORaQC7DhlVfkoEhAw-vaaAtWFE-jRif0juxRWrNjPsW_aYU_KzUpaqnPLFm4vvcHxs6uiTDcY3-I-zAkEAzOj5cn" +
            "A2Fmm_585RIc5IOlq0-CytFFXV6NDEqFXKh8BKI2w01ULl7kBCWnOgqfJmtIS1V-LD9E2LqhIrIy7HtwJAGcZDufCmYugMDAE3f1qqA" +
            "ypF_0eAT4ASfX05m7sBsBRP36U-LckRoxeSbw13cZmbDSyvLIUIRhbTYDKpgYOt_QJARF0HIdfroIKi3veQyB9yoZwEiGYPUP_38Y4E" +
            "K_uNo8Vl4-InbbqZlWkkZvbgklvdRbrjht3Gw3cJbyr2K-6dMg";

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
        password = RSAUtil.privateEncrypt(password, privateKey);
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
