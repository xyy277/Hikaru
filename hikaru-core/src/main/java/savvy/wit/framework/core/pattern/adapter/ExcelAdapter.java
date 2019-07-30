package savvy.wit.framework.core.pattern.adapter;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ExcelAdapter
 * Author : zhoujiajun
 * Date : 2019/7/30 14:28
 * Version : 1.0
 * Description : 
 ******************************/
public class ExcelAdapter {

    private static ExcelAdapter adapter;

    private static class LazyInit {
        private static ExcelAdapter INITIALIZATION = new ExcelAdapter();
    }

    public static ExcelAdapter init() {
        adapter = LazyInit.INITIALIZATION;
        return adapter;
    }


}
