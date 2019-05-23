package savvy.wit.framework.core.pattern.adapter;

import java.util.List;
import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : excel 转 properties 模板
 * File name : Excel2Properties
 * Author : zhoujiajun
 * Date : 2018/12/11 10:52
 * Version : 1.0
 * Description : 
 ******************************/
public interface Excel2Properties {

    /**
     * 对excel读取的内容进行加工
     * @param mapList 读取到的excel原始内容
     * @param adapter 适配器类
     * @return
     */
    Object use(List<Map<String, String>> mapList, ScExcelAdapter adapter);

    /**
     * excel转properties
     * @param targetPath 目标properties 路径
     * @param contentPath excel 路径
     * @param compareJsonPath 比较的文件路径
     */
    void excel2Properties(String targetPath, String contentPath, String compareJsonPath);

    /**
     * 检查当前行是否为键值对形式
     * @param line 读取的每一行数据
     * @return
     */
    boolean checkLine(String line);

    /**
     * 检查末尾是否为，
     * 即配置文件末尾是不带，
     * @param line 读取的每一行数据
     * @return
     */
    boolean checkLast(String line);

    /**
     * 去除末尾
     * @param line 当前读取的行数据
     * @param last 末尾是否有，
     * @return
     */
    String decorate(String line, boolean last);

    /**
     * 将行数据解析成数组 [key, value]
     * @param line 读取行数据
     * @return
     */
    String[] getResult(String line);

    /**
     * 将解析过来的数组拼接成一行信息
     * @param result 数组[key, value]
     * @return
     */
    String joint(String[] result);

    /**
     * 解析的一行数据[key, value] 与excel读取的内容做比较替换
     * @param result 数组[key, value]
     * @param maps excel读取的 List<Map<String, String>>
     * @return
     */
    String[] compareMap2Replace(String[] result, List<Map<String, String>> maps);
}
