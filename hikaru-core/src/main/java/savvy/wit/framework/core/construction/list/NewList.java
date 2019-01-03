package savvy.wit.framework.core.construction.list;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title :
 * File name : NewList
 * Author : zhoujiajun
 * Date : 2018/9/29 17:17
 * Version : 1.0
 * Description :
 ******************************/
public class NewList<E> extends ArrayList<E> implements List<E>, Serializable {


    public NewList() {

    }

    public int length() {
        return super.size();
    }
}
