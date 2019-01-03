package savvy.wit.framework.core.construction.string;

import savvy.wit.framework.core.base.util.StringUtil;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : NewString
 * Author : zhoujiajun
 * Date : 2018/10/15 17:23
 * Version : 1.0
 * Description : 
 ******************************/
public class NewString {

    private StringBuilder value;

    @Override
    public String toString() {
        return value.toString();
    }

    public NewString () {
        init();
    }

    public void add(Object... objects) {
        for (Object o : objects) {
            this.value.append(o.toString());
        }
    }

    private void init() {
        this.value = new StringBuilder();
    }

    public NewString (Object... objects) {
        if (null == this.value)
            init();
        add(objects);
    }

    public int count(String s) {
        return StringUtil.isNotBlank(s) ? toString().split(s).length - 1 : -1;
    }

    public boolean contains(String s) {
        return StringUtil.isNotBlank(s) ? -1 != value.indexOf(s) : false;
    }

    public int length() {
        return value.length();
    }

}
