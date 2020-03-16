package savvy.wit.framework.core.base.callback;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : CmaCallBack
 * File name : CmaCallBack
 * Author : 方应辉
 * Date : 2020/2/28 15:40
 * Version : 
 * Description :
 *********************************/
public interface CmdCallBack {

    /**
     * 回调处理process的输出
     * @param process
     * @return
     */
    Object execute(Process process);
}
