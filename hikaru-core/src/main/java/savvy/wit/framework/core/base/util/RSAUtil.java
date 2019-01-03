package savvy.wit.framework.core.base.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by aj907 on 2018/4/27.
 */
public class RSAUtil {


    public static final String RSA = "RSA";


    /**
     * 生成RSA密钥
     *
     * @return
     * @throws Exception
     */
    public static RSADTO generateRSA() throws Exception {
        RSADTO rsa = null;


        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
            keyPairGenerator.initialize(1024);


            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            rsa = new RSADTO();
            String publicString = keyToString(publicKey);
            String privateString = keyToString(privateKey);
            rsa.setPublicKey(publicString);
            rsa.setPrivateKey(privateString);


        } catch (NoSuchAlgorithmException e) {
            throw new Exception(e.getMessage());
        }


        return rsa;
    }


    /**
     * BASE64 String 转换为 PublicKey
     *
     * @param publicKeyString
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String publicKeyString) throws Exception {
        PublicKey publicKey = null;
        BASE64Decoder base64Decoder = new BASE64Decoder();
        try {
            byte[] keyByteArray = base64Decoder.decodeBuffer(publicKeyString);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyByteArray);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            publicKey = keyFactory.generatePublic(x509KeySpec);
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        }


        return publicKey;
    }


    /**
     * BASE64 String 转换为 PrivateKey
     *
     * @param privateKeyString
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String privateKeyString) throws Exception {
        PrivateKey privateKey = null;
        BASE64Decoder base64Decoder = new BASE64Decoder();


        try {
            byte[] keyByteArray = base64Decoder.decodeBuffer(privateKeyString);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyByteArray);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(e.getMessage());
        } catch (InvalidKeySpecException e) {
            throw new Exception(e.getMessage());
        }


        return privateKey;


    }


    /**
     * RSA 加密返回byte[]
     *
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] encodeBytePrivate(byte[] content, PrivateKey privateKey) throws Exception {
        byte[] encodeContent = null;
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            encodeContent = cipher.doFinal(content);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(e.getMessage());
        } catch (NoSuchPaddingException e) {
            throw new Exception(e.getMessage());
        } catch (InvalidKeyException e) {
            throw new Exception(e.getMessage());
        } catch (IllegalBlockSizeException e) {
            throw new Exception(e.getMessage());
        } catch (BadPaddingException e) {
            throw new Exception(e.getMessage());
        }
        return encodeContent;
    }

    /**
     * 解密返回byte[]
     *
     * @param content
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] decodeBytePublic(byte[] content, PublicKey publicKey) throws Exception {
        byte[] decodeContent = null;
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            decodeContent = cipher.doFinal(content);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(e.getMessage());
        } catch (NoSuchPaddingException e) {
            throw new Exception(e.getMessage());
        } catch (InvalidKeyException e) {
            throw new Exception(e.getMessage());
        } catch (IllegalBlockSizeException e) {
            throw new Exception(e.getMessage());
        } catch (BadPaddingException e) {
            throw new Exception(e.getMessage());
        }
        return decodeContent;
    }

    /**
     * 将Key转为String
     *
     * @param key
     * @return
     */
    private static String keyToString(Key key) {
        byte[] keyByteArray = key.getEncoded();
        BASE64Encoder base64 = new BASE64Encoder();
        String keyString = base64.encode(keyByteArray);
        return keyString;
    }

    public static class RSADTO {
        /**
         * 公钥
         */
        private String publicKey;


        /**
         * 私钥
         */
        private String privateKey;


        public String getPublicKey() {
            return publicKey;
        }


        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }
        public String getPrivateKey() {
            return privateKey;
        }


        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }
    }
}
