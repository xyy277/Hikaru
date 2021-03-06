package savvy.wit.framework.core.pattern.decorate;

import savvy.wit.framework.core.base.util.DateUtil;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 计数器
 * File name : Count
 * Author : zhoujiajun
 * Date : 2018/11/21 15:56
 * Version : 1.0
 * Description : 用于lambda内each计数
 ******************************/
public class Counter {

    private int count = 0;

    private int index = 0;

    private Counter counter;

    // 存值
    private Map<String, Object> value = new HashMap();

    private long time = System.currentTimeMillis();

    public static Counter me() {
        return LazyInit.INITIALIZATION;
    }

    private static class LazyInit {
        private static Counter INITIALIZATION = new Counter();
    }

    public static Counter create() {
        return new Counter();
    }

    public long destroy() {
        cleanAll();
        return System.currentTimeMillis() - time;
    }

    public static Counter create(Object object) {
        return object != null && object instanceof Counter ? (Counter) object : new Counter();
    }

    private Counter () {}

    public int getCount() {
        return count;
    }

    public int getIndex(int... excursion) {
        return index++ + (excursion.length > 0 ? (int)Arrays.asList(excursion).stream().count() : 0);
    }

    /**
     * 修正计数偏差
     * @param excursion 偏移
     * @return 计数值
     */
    public int getCount(int excursion) {
        return count + excursion;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCount(int count, int excursion) {
        this.count = count + excursion;
    }

    public Map<String, Object> getValue() {
        return value;
    }

    public Object getValue(String key) {
        return value.get(key);
    }

    public void setValue(Map<String, Object> value) {
        this.value = value;
    }

    public void setValue(String key, Object value) {
        if (counter == null) {
            counter = new Counter();
        }
        counter.counting();
        this.value.put(key, value);
    }

    public void remove(String key) {
        if (this.value.remove(key) != null) {
            counter.setCount(-1);
        }
    }

    public void remove(String key, Object o) {
        if (this.value.remove(key, o)) {
            counter.setCount(-1);
        }
    }

    public Counter getCounter() {
        return counter;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    /**
     * 计数
     */
    public void counting() {
        this.setCount(this.getCount() + 1);
    }

    public void cleanCount() {
        this.count = 0;
    }

    public void cleanIndex() {
        this.index = 0;
    }

    public void cleanValue() {
        this.value = new HashMap<>();
        this.counter = new Counter();
    }

    public void cleanAll() {
        this.cleanCount();
        this.cleanIndex();
        this.cleanValue();
    }

    public void counting(int step) {
        this.setCount(this.getCount() + step);
    }

    public boolean compareIndex(String regex, Integer... integers) {
        return false;
    }

    public boolean compareCount() {
        return false;
    }

}
