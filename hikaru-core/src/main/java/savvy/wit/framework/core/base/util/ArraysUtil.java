package savvy.wit.framework.core.base.util;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ArraysUtil
 * Author : zhoujiajun
 * Date : 2018/8/30 11:09
 * Version : 1.0
 * Description : 
 ******************************/
public class ArraysUtil {


    private static class LazyInit{
        private static ArraysUtil INITIALIZATION = new ArraysUtil();
    }


    public static ArraysUtil me() {
        return LazyInit.INITIALIZATION;
    }

    public Object getOneOfIt(Object... objects) {
        return objects != null ? objects.length > 0 ? objects[DateUtil.random(objects.length)] : objects.getClass().getSimpleName() + " is empty" : null;
    }
}
