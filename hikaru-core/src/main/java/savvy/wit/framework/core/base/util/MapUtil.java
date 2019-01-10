package savvy.wit.framework.core.base.util;

import java.util.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : MapUtil
 * Author : zhoujiajun
 * Date : 2019/1/7 15:08
 * Version : 1.0
 * Description : 
 ******************************/
public class MapUtil {

    public static Map<String, Object> sortMapKey (Map<String, Object> map, Comparator<String> comparator) {
        Map<String, Object>  sortMap = new TreeMap<> (comparator);
        sortMap.putAll(map);
        return sortMap;
    }

    public static Map<String, Object> sortMapValue(Map<String, Object> map, Comparator<Map.Entry<String, Object>> comparator) {
        Map<String, Object> sortMap = new LinkedHashMap<>();
        List<Map.Entry<String, Object>> entryList = new ArrayList<>(map.entrySet());
        Collections.sort(entryList, comparator);
        Iterator<Map.Entry<String, Object>> iterator = entryList.iterator();
        Map.Entry<String, Object> entry = null;
        while (iterator.hasNext()) {
            entry = iterator.next();
            sortMap.put(entry.getKey(), entry.getValue());
        }
        return sortMap;
    }

    public static <K, V> Map<K, V> sortByKey (Map<K, V> map, Comparator<K> comparator) {
        Map<K, V>  sortMap = new TreeMap<> (comparator);
        sortMap.putAll(map);
        return sortMap;
    }

    public static <K, V> Map<K, V> sortByValue(Map<K, V> map, Comparator<Map.Entry<K, V>> comparator) {
        Map <K, V> sortMap = new LinkedHashMap<>();
        List<Map.Entry <K, V>> entryList = new ArrayList<>(map.entrySet());
        Collections.sort(entryList, comparator);
        Iterator<Map.Entry <K, V>> iterator = entryList.iterator();
        Map.Entry <K, V> entry = null;
        while (iterator.hasNext()) {
            entry = iterator.next();
            sortMap.put(entry.getKey(), entry.getValue());
        }
        return sortMap;
    }

}
