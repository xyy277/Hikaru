package savvy.wit.framework.core.pattern.adapter;

import java.util.List;
import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Excel2Properties
 * Author : zhoujiajun
 * Date : 2018/12/11 10:52
 * Version : 1.0
 * Description : 
 ******************************/
public interface Excel2Properties {

    Object use(List<Map<String, String>> mapList, ScExcelAdapter adapter);

    void excel2Properties(String targetPath, String contentPath, String compareJsonPath);

    boolean checkLine(String line);

    boolean checkLast(String line);

    String decorate(String line, boolean last);

    String[] getResult(String line);

    String joint(String[] result);

    String[] compareMap2Replace(String[] result, List<Map<String, String>> maps);
}
