package savvy.wit.framework.core.pattern.adapter;

import savvy.wit.framework.core.base.interfaces.Log;
import savvy.wit.framework.core.pattern.factory.LogFactory;
import savvy.wit.framework.core.base.callback.ExcelCallback;
import savvy.wit.framework.core.base.util.ScExcelUtils;

import java.io.File;
import java.util.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ScExcelAdapter
 * Author : zhoujiajun
 * Date : 2018/9/27 18:44
 * Version : 1.0
 * Description : 
 ******************************/
public class ScExcelAdapter {

    private Log log = LogFactory.getLog();

    private Object result;

    private File file;

    private  List<Map<String,String>> list = new ArrayList<>();

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    private static ScExcelAdapter $this;

    public static ScExcelAdapter NEW() {
        $this = new ScExcelAdapter();
        return $this;
    }

    public ScExcelAdapter readExcel(String excelName, ExcelCallback callback) {
        try {
            list = ScExcelUtils.parseExcel($this.getClass().getResourceAsStream(excelName));
            $this.setResult(callback.use(list, $this));
        }catch (Exception e) {
            log.error(e);
        }
        return $this;
    }

    public ScExcelAdapter write2Excel(String fname, String[] titles,String[]keys, List<Map<String,String>> mapList) {
        File file = null;
        try {
            file = ScExcelUtils.getExcel(null,fname,mapList,titles,keys);
        }catch (Exception e) {
            log.error(e);
        }
        $this.setFile(file);
        return $this;
    }

    public ScExcelAdapter read2Write(String excelName,String fname, String[] titles,String[]keys, ExcelCallback callback) {
        try {
            write2Excel(fname,titles,keys,(List<Map<String,String>>) readExcel(excelName,callback).getResult());
        }catch (Exception e) {
            log.error(e);
        }
        return $this;
    }

    /**
     * 获取一纵列数据
     * @param num
     * @return
     */
    public List<String> getCellValues(int num) {
        List<String> cellValues = new ArrayList<>();
        for (Map map : list) {
            cellValues.add((String) map.get(getCellNum(num)));
        }
        return cellValues;
    }

    /**
     * 获取列
     * @param num  0 - n
     * @return
     */
    public String getCellNum(int num) {
        return num >= 0  ? "CELL" + num : null;
    }

    public Map sortMapKey (Map map,Comparator<String> comparator) {
        Map<String, String> sortMap = new TreeMap<>(comparator);
        sortMap.putAll(map);
        return sortMap;
    }

    public Map<String, String> sortMapValue(Map<String, String> map, Comparator<Map.Entry<String, String>> comparator) {
        Map<String, String> sortMap = new LinkedHashMap<>();
        List<Map.Entry<String, String>> entryList = new ArrayList<>(map.entrySet());
        Collections.sort(entryList, comparator);
        Iterator<Map.Entry<String, String>> iterator = entryList.iterator();
        Map.Entry<String, String> entry = null;
        while (iterator.hasNext()) {
            entry = iterator.next();
            sortMap.put(entry.getKey(), entry.getValue());
        }
        return sortMap;
    }

    public List<Map> getListBySortKey(Comparator<String> comparator) {
        List<Map> list = new ArrayList<>();
        $this.list.forEach(stringStringMap -> {
            list.add(sortMapKey(stringStringMap, comparator));
        });
        return list;
    }

    public List<Map> getListBySortValue(Comparator<Map.Entry<String, String>> comparator) {
        List<Map> list = new ArrayList<>();
        $this.list.forEach(stringStringMap -> {
            list.add(sortMapValue(stringStringMap, comparator));
        });
        return list;
    }

    public List<Map> getListBySortKey(List<Map> mapList, Comparator<String> comparator) {
        List<Map> list = new ArrayList<>();
        mapList.forEach(stringStringMap -> {
            list.add(sortMapKey(stringStringMap, comparator));
        });
        return list;
    }

    public List<Map> getListBySortValue(List<Map> mapList, Comparator<Map.Entry<String, String>> comparator) {
        List<Map> list = new ArrayList<>();
        mapList.forEach(stringStringMap -> {
            list.add(sortMapValue(stringStringMap, comparator));
        });
        return list;
    }

}
