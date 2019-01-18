package savvy.wit.framework.core.base.util;

/**
 * Created by aj907 on 2018/4/27.
 */
public class StringUtil extends DateUtil {

    private static final String POOL = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
    /**
     * 获取字符串子串t在母串中的下标
     * @param s	母串
     * @param t	子串
     * @return	存在则返回第一次查到的下标	查不到则返回-1
     */
    public static int getIndex(String s,String t){
        int length=t.length();
        char c=t.charAt(0);
        for(int i=0;i<s.length();){
            if(c==s.charAt(i)){
                for(int j=1;;j++){
                    char sc=s.charAt(++i);
                    char st=t.charAt(j);
                    if(sc==st){
                        if(j<length-1) continue;
                        else return i-j;
                    }else{
                        i-=j-1;
                        break;
                    }
                }
            }else{
                i++;
            }
        }
        return -1;
    }

    public static boolean isBlank(String string) {
        return string == null ? true : "".equals(string) ? true : false;
    }

    public static boolean isNotBlank(String string) {
        return !isBlank(string);
    }

    public static String createCode(int length) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<length; i++) {
            sb.append( POOL.charAt(random(POOL.length())) );
        }
        return sb.toString();
    }

    public static String createCode() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i< DateUtil.random(~(1<<31) / 1000) / 1000 / 1000; i++) {
            sb.append( POOL.charAt(random(POOL.length())) );
        }
        return sb.toString();
    }

}
