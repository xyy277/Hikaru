package savvy.wit.framework.core.base.callback;

import savvy.wit.framework.core.base.pool.ThreadPool;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ThreadCallBack
 * Author : zhoujiajun
 * Date : 2018/12/19 10:29
 * Version : 1.0
 * Description : 
 ******************************/
public interface ThreadCallBack {


    void doThread(ThreadPool pool, Thread thread) throws Exception;

}
