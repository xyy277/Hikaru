package zjj;

import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.base.util.RSAUtil;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : RSATest
 * Author : zhoujiajun
 * Date : 2019/1/29 15:22
 * Version : 1.0
 * Description : 
 ******************************/
public class RSATest {


    private Log log = LogFactory.getLog();

    @org.junit.Test
    public void test() {
        Map<String, String> key = RSAUtil.createKeys(1024);

        log.log(key.get("privateKey"));
        log.log(key.get("publicKey"));
        RSAPrivateKey privateKey = null;
        RSAPublicKey publicKey = null;
        try {
            privateKey = RSAUtil.getPrivateKey(key.get("privateKey"));
            publicKey = RSAUtil.getPublicKey(key.get("publicKey"));
            log.log(privateKey);
            log.log(publicKey);
        }catch (Exception e) {
            log.error(e);
        }
        String password = RSAUtil.privateEncrypt("root", privateKey);
        log.log(password);
        password = RSAUtil.publicDecrypt(password, publicKey);
        log.log(password);
        password = RSAUtil.publicEncrypt(password, publicKey);
        log.log(password);
        password = RSAUtil.privateDecrypt(password, privateKey);
        log.log(password);

        // ------

    }
}
