package savvy.wit.framework.core.base.util;/**
 * Created by zhoujiajun on 2018/6/27.
 */

import savvy.wit.framework.core.base.interfaces.Log;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.io.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 文件工具类
 * File name : FileUtil
 * Author : zhoujiajun
 * Date : 2018/6/27 12:46
 * Version : 1.0
 * Description : 
 ******************************/
public class FileUtil {
    private Log log = LogFactory.getLog();

    public static FileUtil me() {
        return new FileUtil();
    }

    public File create(String path) throws Exception {
        File file = null;
        file = new File(path);
        if (file.exists()) {
            throw new RuntimeException("file is exist");
        }
        if(file.isDirectory()) {
            throw new RuntimeException(("file is directory"));
        }
        if (null == file) {
            log.warn("file create fail");
        }
        return file;
    }

    public File NEW(String path) {
        File file = null;
        file = new File(path);
        return file;
    }

    public String getPackage(String path) {
        return null;
    }

}
