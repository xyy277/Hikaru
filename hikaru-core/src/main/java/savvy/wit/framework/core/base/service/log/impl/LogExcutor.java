package savvy.wit.framework.core.base.service.log.impl;

import savvy.wit.framework.core.base.callback.LogCallBack;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.base.util.DateUtil;
import savvy.wit.framework.core.base.util.StringUtil;
import savvy.wit.framework.core.pattern.factory.Config;
import savvy.wit.framework.core.pattern.factory.Daos;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : LogUtil
 * Author : zhoujiajun
 * Date : 2018/6/27 14:42
 * Version : 1.0
 * Description : 
 ******************************/
public class LogExcutor implements Log {

    private String front = "";

    private String behind = "";
    private static String regex1;
    private static String regex2;
    private static String regex3;
    private static String regex4;
    private static String regex5;
    private static String regex6;
    private static Pattern pattern1;
    private static Pattern pattern2;
    private static Pattern pattern3;
    private static Pattern pattern4;
    private static Pattern pattern5;
    private static Pattern pattern6;
    static {
        regex1 = "[0-9]+(\\*){1}[^0-9]+";
        regex2 = "[0-9]+(\\*){1}[0-9]+";
        regex3 = "[^0-9]+(\\*){1}[0-9]+";
        regex4 = "[0-9]+";
        regex5 = "(?!(\\*))+[^0-9]+";
        regex6 = "[^0-9]+(?!(\\*))+";
        pattern1 = Pattern.compile(regex1);
        pattern2 = Pattern.compile(regex2);
        pattern3 = Pattern.compile(regex3);
        pattern4 = Pattern.compile(regex4);
        pattern5 = Pattern.compile(regex5);
        pattern6 = Pattern.compile(regex6);
    }
    private static final String FILENAME = "logs\\"+ DateUtil.getNow("yyyy") + "\\" + DateUtil.getNow("MM") + "\\" + DateUtil.getNow("yyyy-MM-dd") + ".log";

    private static  Config config = Config.init("/properties/log.properties");

    private LogExcutor() {
        level = config.getValue("level");
    }
    private enum Stage {
        LOG,
        WARN,
        ERROR,
        SQL
    }
    private static final String LOG_DEFAULT = Stage.LOG.toString()+ "  ";
    private static final String LOG_WARNING = Stage.WARN.toString() + " ";
    private static final String LOG_ERROR = Stage.ERROR.toString() + "";
    private static final String LOG_SQL = Stage.SQL.toString() + "  ";

    private static String level;
    private static Long index = 0l;
    public static LogExcutor me() {
        init();
        return lazyInit.INITIALIZATION;
    }

    private static class lazyInit {
        private static final LogExcutor INITIALIZATION = new LogExcutor();
    }

    private static void init() {
//        String address = config.getValue("address");
//        File file = new File(StringUtil.isBlank(address) ? FILENAME : address + "/" + FILENAME);
//        if(!file.getParentFile().exists()) {
//            file.getParentFile().mkdirs();
//        }
//        try {
//            file.createNewFile();
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void log(LogCallBack callBack) {
        try {
            callBack.toLog();
        }catch (Exception e) {
            this.error(e, 3);
        }
    }

    @Override
    public void sql(Object sql) {
        String logs = "["+LOG_SQL+"] "+getLog(3)+"\t"+String.valueOf(sql == null ? "" : sql);
        System.out.println(logs);
        write(logs);
    }

    @Override
    public void log(Object log) {
//        System.out.println(log.getClass().getName());
        String logs = "";
        if(log == null) {
            logs = "["+LOG_DEFAULT+"] "+getLog(2)+"\t"+String.valueOf("null");
            System.out.println(logs);
            return;
        }
        switch (log.getClass().getName()){
            case "java.lang.String":
                logs = "["+LOG_DEFAULT+"] "+getLog(2)+"\t"+String.valueOf(log);
                System.out.println(logs);
                        break;
            case "java.util.Arrays$ArrayList":
                for(Object o : (List)log) {
                   log(o,3);
                }
                break;
            case "java.util.ArrayList":
                for(Object o : (List)log) {
                    log(o,3);
                }
                break;
            case "[Ljava.lang.String;":
                for(Object o : (String[]) log) {
                    log(o,3);
                }
                break;
            case "[Ljava.lang.Object;":
                for(Object o : (Object[]) log) {
                    log(o,3);
                }
                break;
            case "[Ljava.lang.Class;":
                for(Object o : (Object[]) log) {
                    log(o,3);
                }
                break;
            case "java.lang.StackTraceElement":
                logs = "\t"+String.valueOf(log);
                System.out.println(logs);
                        break;
            default:
                logs = "["+LOG_DEFAULT+"] "+getLog(2)+"\t"+String.valueOf(log);
                System.out.println(logs);
                }
//        write(logs);
    }

    private void log(Object log, int strack) {
        String logs = "";
        if(log == null) {
            this.error(new NullPointerException("log is null"),3);
            return;
        }
        switch (log.getClass().getSimpleName()) {
            case "String":
                logs = "[" + LOG_DEFAULT + "] " + getLog(strack) + "\t" + String.valueOf(log);
                System.out.println(logs);
                        break;
            case "ArrayList":
                for (Object o : (List) log) {
                    log(o,strack+1);
                }
                break;
            case "String[]":
                logs = "[" + LOG_DEFAULT + "] " + getLog(strack) + "\t";
                if (log instanceof String[]) {
                    StringBuilder builder = new StringBuilder("| ");
                    for (int i = 0; i < ((String[]) log).length; i++) {
                        builder.append(((String[])log)[i] + " | ");
                    }
                    System.out.println(logs + builder.toString());
                } else {
                    System.out.println(logs + String.valueOf(log));
                }
                break;
            case "StackTraceElement":
                logs = "\t" + String.valueOf(log);
                System.out.println(logs);
                        break;
            case "Class":
                logs = "[" + LOG_DEFAULT + "] " + getLog(strack) + "\t" + String.valueOf(log);
                System.out.println(logs);
                        break;
            default:
                logs = "[" + LOG_DEFAULT + "] " + getLog(strack) + "\t" + String.valueOf( log);
                System.out.println(logs);
                }
//        write(logs);
    }

    @Override
    public void warn(Object log) {
        String logs = "["+LOG_WARNING+"] "+getLog(2)+"\t"+String.valueOf(log == null ? "" : log);
        System.out.println(logs);
//        save(logs);
        write(logs);
    }

    @Override
    public void error(Exception log) {
        if(log == null) {
            this.error(new NullPointerException("log is null"),3);
            return;
        }
        String logs = "["+LOG_ERROR+"] "+getLog(2)+"\t"+String.valueOf(log);
        System.out.println(logs);
        log(Arrays.asList(log.getStackTrace()));
//        save(logs);
        write(logs);
    }

    private void error(Exception log, int var) {
        if(log == null) {
            this.error(new NullPointerException("log is null"),var);
            return;
        }
        String logs = "["+LOG_ERROR+"] "+getLog(var)+"\t"+String.valueOf(log);
        System.out.println(logs);
        log(Arrays.asList(log.getStackTrace()));
//        save(logs);
        write(logs);
    }

    @Override
    public Log print(String... strings) {
        String string = "";
        if (strings != null && strings.length > 0) {
            for (String s : strings) {
                string += s;
            }
        }
        if (pattern1.matcher(string).matches()) {
            String str = find(pattern4.matcher(string));
            int num = Integer.parseInt(str);
            str = "";
            String symbol = find(pattern5.matcher(string));
            for (int var = 0; var < num; var++) {
                str += symbol;
            }
            System.out.print(str);
        } else if (pattern2.matcher(string).matches()) {
            System.out.print(Integer.parseInt(string.split("\\*")[0]) * Integer.parseInt(string.split("\\*")[1]));
        } else if (pattern3.matcher(string).matches()) {
            int num = Integer.parseInt(find(pattern4.matcher(string)));
            String str = "";
            String symbol = find(pattern6.matcher(string));
            for (int var = 0; var < num; var++) {
                str += symbol;
            }
            System.out.print(str);
        } else {
            System.out.print(string);
        }
        return me();
    }

    @Override
    public Log println(String... strings) {
        String string = "";
        if (strings != null && strings.length > 0) {
            for (String s : strings) {
                string += s;
            }
        }
        if (pattern1.matcher(string).matches()) {
            String str = find(pattern4.matcher(string));
            int num = Integer.parseInt(str);
            str = "";
            String symbol = find(pattern5.matcher(string));
            for (int var = 0; var < num; var++) {
                str += symbol;
            }
            System.out.println(str);
        } else if (pattern2.matcher(string).matches()) {
            System.out.println(Integer.parseInt(string.split("\\*")[0]) * Integer.parseInt(string.split("\\*")[1]));
        } else if (pattern3.matcher(string).matches()) {
            int num = Integer.parseInt(find(pattern4.matcher(string)));
            String str = "";
            String symbol = find(pattern6.matcher(string));
            for (int var = 0; var < num; var++) {
                str += symbol;
            }
            System.out.println(str);
        } else {
            System.out.println(string);
        }
        return me();
    }

    private String find(Matcher matcher) {
        String result = "";
        while (matcher.find()) {
            result = matcher.group();
        }
        return result;
    }

    private String className(int i) {
        return Thread.currentThread().getStackTrace()[++i].getClassName();
    }

    private String methodName(int i) {
        return Thread.currentThread().getStackTrace()[++i].getMethodName();
    }

    private int lineNumber(int i) { return Thread.currentThread().getStackTrace()[++i].getLineNumber();}

    private String getLog(int i) {
        return getFront()+ " " + DateUtil.getNow()+"\t|-\t"+className((i+1))+":("+methodName((i+1))+"."+lineNumber((i+1))+") -| " + getBehind();
    }

    public String getFront() {
        return front;
    }

    public void front(String front) {
        this.front = front;
    }

    public String getBehind() {
        return behind;
    }

    public void behind(String behind) {
        this.behind = behind;
    }

    private void save(String logs) {
        //Persistence
        try {
            savvy.wit.framework.core.pattern.decorate.log.bean.Log log = new savvy.wit.framework.core.pattern.decorate.log.bean.Log();
            log.setOptTime(DateUtil.getNow());
            log.setOptUser(UUID.randomUUID().toString().replaceAll("-", ""));
            log.setOptValue(logs);
            Daos.get().insert(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void write(String log) {
            index++;
//            FileAdapter.NEW().lazyWriter(FILENAME ,index + "\t" +log);
    }

    private void out(String logs) {
        System.out.println(logs);
    }

}
