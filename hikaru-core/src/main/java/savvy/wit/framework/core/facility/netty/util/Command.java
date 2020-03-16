package savvy.wit.framework.core.facility.netty.util;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : Command 命令
 * File name : Command
 * Author : zhoujiajun
 * Date : 2020/02/19 15:32
 * Version : 1.0
 * Description : 创建Command命令工具类
 * 是否所有命令都能组合使用要根据不同厂商来定制
 * function and(commands) 中 commands作为可执行的一次操作命令集
 * 然后根据不通厂商VENDOR提供的不同的组合方案
 * 维护该类，核心是维护MANUFACTURERS、ANDS两个数组，不同数组以及不同的拼接方式
 * 执行流程 init(初始化厂商) ——> and(命令集) ——> output(字符串)/outputHex(16进制) 输出命令结果
 * 单例
 * *****************************/
public class Command {

    /*
     * 厂家列表
     */
    private static final String[] MANUFACTURERS = new String[] {
            "BARIX", "SENSTAR", "IVS"
    };

    /*
     * 厂家提供接口的组合方式
     */
    private static final String[] ANDS =  new String[]{
            "&", "", ""
    };

    // OK
    public static final String OK = "OK"; // 4F 4B 0D 0A

    /*
     * Command 实例
     */
    private static Command instance;

    /*
     * 厂商
     */
    private String vendor = "";

    public String getVendor() {
        return vendor;
    }

    public Command setVendor(String vendor) {
        this.vendor = vendor;
        return instance;
    }

    /*
     * Command 命令
     */
    private String Command = "";

    /*
     * 换行
     */
    private static final String BR_HEX = "0a";

    private static final String BR = "\r";

    private Command() {

    }

    /**
     * 初始化
     * @param vendor 厂商
     * @return instance
     */
    public static Command init(String vendor) {
        instance = LazyInit.INITIALIZATION;
        return instance.reset().setVendor(vendor);
    }

    private static class LazyInit {

        private static final Command INITIALIZATION = new Command();
    }

    /**
     * 命令合成
     * @param commands 命令集
     * @return instance
     */
    public Command and(String... commands) {
        // 根据不同厂商提供的命令拼接方式进行拼接
        for (int i = 0; i < MANUFACTURERS.length; i++) {
            if (vendor.equals(MANUFACTURERS[i]))
                for (String command : commands)
                    Command += ("".equals(Command) ? Command : ANDS[i]) + command;
        }
//        // BARIx
//        if (vendor.equals(MANUFACTURERS[0])) {
//            for (String command : commands) {
//            }
//        }
//        // SENSTAR
//        if (vendor.equals(MANUFACTURERS[1])) {
//
//        }
//        // IVS
//        if (vendor.equals(MANUFACTURERS[2])) {
//
//        }
//        // ....
        return instance;
    }

    /**
     * reset重置
     * @return instance
     */
    public Command reset() {
        vendor = "";
        Command = "";
        return instance;
    }

    /**
     * Command产出
     * @return 16进制字符串
     */
    public String outputHex() {
        return MessageUtil.bytes2Hex(Command.getBytes()) + BR_HEX;
    }

    /**
     * Command产出
     * @return 普通字符串
     */
    public String output() {
        return Command + BR;
    }


}
