package savvy.wit.framework.core.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by aj907 on 2018/4/27.
 */
public class DateUtil {

    private static Random random = new Random();
    private static String pool = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890" ;

    /**
     * 根据系统时间随机获取一个编号
     * @param len
     * @return
     */
    public static Long createNumber(int len){
        StringBuilder sb = new StringBuilder(getNow("yyyyMMddHHmmss"));
        for(int i = 0 ; i++<len ;){
            sb.append(random(10));
        }
        return Long.parseLong(sb.toString());
    }

    /**
     * 根据系统时间随机获取一个编号
     * @param len
     * @return
     */
    public static String createCode(int len, boolean useTime){
        StringBuilder sb = new StringBuilder(useTime ? getNow("yyyyMMddHHmmss") : "");
        for(int i = 0 ; i++<len ;){
            sb.append( pool.charAt(random(pool.length()) ));
        }
        return sb.toString();
    }

    /**
     * 获取处理耗时
     * @param start
     * @param end
     * @return
     */
    public static String getTimeConsuming( long start,long end){
        long time=end-start;//计算插入耗时
        return "耗时:\t"+(time>10000?getTime(time, "mm分ss秒"):time+"毫秒");
    }

    /**
     * 获取当前系统时间
     * @param format 日期转化格式
     * 		       建议格式:yyyy-MM-dd HH:mm:ss
     * @return String 日期
     */
    public static String getNow(String format){
        return new SimpleDateFormat(format).format(new Date());
    }
    public static String getNow(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,S").format(new Date());
    }
    /**
     * 毫秒值转为 format类型的时间字符
     * @param time
     * @param format
     * @return
     */
    public static String getTime(long time,String format){
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return new SimpleDateFormat(format).format(calendar.getTime());
    }

    /**
     * 获取系统当前毫秒值
     * @return
     */
    public static long getTime(){
        return System.currentTimeMillis();
    }

    /**
     * 获取时间
     *
     * @param str 字符串日期格式 2017-06-26 13:21:12
     * 			          统一解析格式 yyyy-MM-dd HH:mm:ss
     * @return date
     */
    public static Date getDate(String str){
        String format="yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        Date date=null;
        try {
            date=sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取过去的小时
     *
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 60 * 1000);
    }

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 1000);
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     *
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60
                * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "."
                + sss;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    /**
     * 获取随机数
     * @param ran
     * @return
     */
    public static int random(int ran){
        return random.nextInt(ran);
    }
}
