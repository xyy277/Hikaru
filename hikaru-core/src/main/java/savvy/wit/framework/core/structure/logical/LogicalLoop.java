package savvy.wit.framework.core.structure.logical;

import java.util.Arrays;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 逻辑环
 * File name : LogicalLoop
 * Author : zhoujiajun
 * Date : 2019/2/19 14:24
 * Version : 1.0
 * Description : 1 -> 2 -> 3 -> 4 -> 5 -> 1
 ******************************/
public class LogicalLoop {

    // 手动指针
    private int cursor = -1;

    // 私有指针
    private int pointer = -1;

    private Object[] objects = new Object[0];

    public static LogicalLoop create() {
        return LazyInit.INITIALIZATION;
    }

    private static class LazyInit {
        private static LogicalLoop INITIALIZATION = new LogicalLoop();
    }

    public Object remove(int index) {
        Object object = null;
        if (index >= 0 && index <= objects.length - 1) {
            object = objects[index];
        }
        if (object != null) {
            Object[] objects1 = new Object[objects.length - 1];
            int i = 0;
            for (int var = 0; var < objects.length; var++) {
                if (var == index) {
                    continue;
                }
                objects1[i++] = objects[var];
            }
            objects = objects1;
        }
        return object;
    }

    /**
     * 推送对象到环中
     * @param object 参数对象
     */
    public void push(Object object) {
        objects = Arrays.copyOf(objects, objects.length + 1);
        objects[objects.length - 1] = object;
    }

    public void push(Object... objects) {
        for (Object object : objects) {
            push(object);
        }
    }

    /**
     * 复原指针，数组对象
     */
    public void restore() {
        pointer = -1;
        cursor = -1;
        objects = new Object[0];
    }

    /**
     * 偏移指针
     * @param offset 偏移量
     */
    public int excursion(int offset) {
        cursor += offset;
        while (cursor >= objects.length) {
            cursor -= objects.length;
        }
        return cursor;
    }

    /**
     * 当前指针指向的对象
     * 需要手动excursion指针
     * @return 指针指向的对象
     */
    public Object fetch() {
        return cursor != -1 ? objects[cursor] : null;
    }

    /**
     * 取环内后一位对象
     * @return
     */
    public Object next() {
        Object object = null;
        if(objects.length != 0) {
            pointer ++;
            if (pointer >= objects.length) {
                pointer = 0;
            }
            object = objects[pointer];
        }
        return object;
    }

    /**
     * 获取手动指针
     * @return
     */
    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
    }

    public Object[] getLoop() {
        return objects;
    }

    public int size() {
        return objects.length;
    }

    public void setLoop(Object[] objects) {
        this.objects = objects;
    }
}
