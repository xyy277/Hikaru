package savvy.wit.framework.core.base.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by aj907 on 2018/4/27.
 */
public class Md5Util {

    /**
     * 获取MD5值
     * @param str
     * @return
     */
    public static String getMD5(String str){
        String mdStr = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[]input = str.getBytes();//字符串转byte数组
            byte[]buff = md.digest(input);//转128位
            mdStr = arrayToString(buff);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return mdStr;
    }

    /**
     * 2进制的byte数组转16位的字符串
     * @param buff
     * @return
     */
    private static String arrayToString(byte[] buff) {
        StringBuffer mdStr = new StringBuffer();//多次修改的字符串用StringBuffer
        int digital = 0;
        for(int i = 0;i < buff.length; i++){
            digital = buff[i];
            if(digital < 0){
                digital += 256;//-256-》255 转正
            }
            if(digital < 16){
                mdStr.append("0");//不足16位在字符串前补0
            }
            mdStr.append(Integer.toHexString(digital));
        }
        return mdStr.toString().toUpperCase();//StringBuffer转大写String
    }

}
