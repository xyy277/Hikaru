package savvy.wit.framework.core.base.callback;

import java.io.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : FileCallBack
 * Author : zhoujiajun
 * Date : 2018/8/21 11:11
 * Version : 1.0
 * Description : 
 ******************************/
public interface FileCallBack {

    void fileReaderHelper(BufferedReader bufferedReader, BufferedWriter bufferedWriter) throws IOException;
}
