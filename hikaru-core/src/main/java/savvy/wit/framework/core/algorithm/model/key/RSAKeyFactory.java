package savvy.wit.framework.core.algorithm.model.key;


import savvy.wit.framework.core.base.util.RSAUtil;

import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : RSAKeyFactory
 * Author : zhoujiajun
 * Date : 2019/3/26 14:44
 * Version : 1.0
 * Description : 
 ******************************/
public class RSAKeyFactory implements KeyStore {

    private String publicKey;

    private String privateKey;

    private RSAPrivateKey rsaPrivateKey;

    private RSAPublicKey rsaPublicKey;

    protected RSAKeyFactory() {
      Map map = RSAUtil.createKeys(1024);
      this.privateKey = (String) map.get(RSAUtil.PRIVATE_KEY);
      this.publicKey = (String) map.get(RSAUtil.PUBLIC_KEY);
        try {
            this.rsaPrivateKey = RSAUtil.getPrivateKey(this.privateKey);
            this.rsaPublicKey = RSAUtil.getPublicKey(this.publicKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    public static KeyStore me() {
        return LazyInit.INITIALIZATION;
    }

    private static class LazyInit {
        private static RSAKeyFactory INITIALIZATION = new RSAKeyFactory();
    }

    public String getPublicKey() {
        return publicKey == null ? RSA_PUBLIC_KEY : publicKey;
    }

    public String getPrivateKey() {
        return privateKey == null ? RSA_PRIVATE_KEY : privateKey;
    }

    public RSAPublicKey getRsaPublicKey() {
        return rsaPublicKey;
    }

    public RSAPrivateKey getRsaPrivateKey() {
        return rsaPrivateKey;
    }

}
