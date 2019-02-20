package savvy.wit.framework.core.pattern.adapter;

import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.util.List;
import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : AbstractExcel2Properties
 * Author : zhoujiajun
 * Date : 2018/12/11 10:52
 * Version : 1.0
 * Description : 
 ******************************/
public abstract class Excel2PropertiesAdapter implements Excel2Properties {

    private Log log = LogFactory.getLog();

    @Override
    public Object use(List<Map<String, String>> mapList, ScExcelAdapter adapter) {
        return mapList;
    }

    @Override
    public void excel2Properties(String targetPath, String contentPath, String compareJsonPath) {
        try {
            // excel解析
            List<Map<String,String>> maps = (List<Map<String,String>>) ScExcelAdapter.NEW()
                    .readExcel(contentPath, (list, adapter) -> use(list, adapter)).getResult();
            FileAdapter.NEW().readFile(targetPath, (br, bw) -> {
                String line;
                boolean last = false;
                while ((line = br.readLine()) != null) {
                    last = checkLast(line);
                    // 判断是否需要比较
                    if (checkLine(line)) {
                        line = decorate(line, last);
                        String [] result = getResult(line);
                        if (result == null) {
                            continue;
                        }
                        result = compareMap2Replace(result, maps);
                        line = joint(result);
                        line = last ? line : line + ",";
                    }
                    if (null != bw)
                        bw.write(line + "\n");
                }
            },compareJsonPath);
        }catch (Exception e) {
            log.error(e);
        }
    }

    @Override
    public boolean checkLine(String line) {
        return line.indexOf("{") == -1 && line.indexOf("}") == -1 && line.indexOf(":") != -1;
    }

    @Override
    public boolean checkLast(String line) {
        return !String.valueOf(line.charAt(line.length() - 1)).equals(",");
    }

    @Override
    public String decorate(String line, boolean last) {
        return !last ?
                line = line.substring(0, line.length() - 1) : line; // 去除末位的，
    }

    @Override
    public String[] getResult(String line) {
        String[] result = null;
        if (line.indexOf(":") > 1) {
            result = line.split(":");
            // 拼接值出现多个：情况
            for (int var = 2; var < result.length; var++) {
                result[1] += ":" + result[var];
            }
        } else if (line.indexOf(":") == 1) {
            result = line.split(":");
        }
        return result;
    }

    @Override
    public String joint(String[] result) {
        return result[0] + ": " + result[1] ;
    }

    @Override
    public String[] compareMap2Replace(String[] result, List<Map<String, String>> maps) {
        return result;
    }
}
