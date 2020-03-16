package savvy.wit.framework.core.facility.netty.util;

/***************************
 * Title: 报文转化工具
 * File name: MessageUtil
 * Author: zhoujiajun
 * Date: 2020/1/20 10:30
 * Description:
 ***************************/
public class MessageUtil {



    private static final char[] DIGITS_HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'};


    public static char[] encodeHex(byte[] bytes) {
        int l = bytes.length;
        char[] data = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            data[j++] = DIGITS_HEX[(0xFF & data[i]) >>> 4];
            data[j++] = DIGITS_HEX[0xFF & data[i]];
        }
        return data;
    }

    public static byte[] decodeHex(char[] chars) {
        int l = chars.length;
        if ((l & 0x01) != 0) {
            throw new RuntimeException("The number of characters should be an even number.");
        }
        byte[] data = new byte[l >> 1];
        for (int i = 0, j =0; i < l; i++) {
            int d = toDigit(chars[j], j) << 4;
            j ++;
            d |= toDigit(chars[j], j);
            j++;
            data[i] = (byte) (d & 0xFF);
        }
        return data;
    }

    private static int toDigit(char c, int i) {
        int digit = Character.digit(c, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + c + " at index " + i);
        }
        return digit;
    }


    /**
     * 16进制转byte数组
     * @param s 16进制
     * @return  byte[]
     */
    public static byte[] hexString2ByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len/2];
        for (int i = 0; i < len; i+= 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    /**
     * 字节数组转16进制
     * @param bytes bytes
     * @return      Hex字符串
     */
    public static String bytes2Hex(byte[] bytes) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2) {
                buffer.append(0);
            }
            buffer.append(hex);
        }
        return buffer.toString();
    }
}
