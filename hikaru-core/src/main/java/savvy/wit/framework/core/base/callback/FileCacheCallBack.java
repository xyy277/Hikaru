package savvy.wit.framework.core.base.callback;

import savvy.wit.framework.core.pattern.adapter.FileAdapter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : FileCacheCallBack
 * Author : zhoujiajun
 * Date : 2018/11/30 10:27
 * Version : 1.0
 * Description : 
 ******************************/
public interface FileCacheCallBack {

    void readCache(BufferedReader reader, BufferedWriter writer, File file, FileAdapter adapter) throws IOException;
}
