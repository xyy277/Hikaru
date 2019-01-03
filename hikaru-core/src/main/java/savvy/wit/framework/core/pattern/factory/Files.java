package savvy.wit.framework.core.pattern.factory;

import savvy.wit.framework.core.base.interfaces.Log;
import savvy.wit.framework.core.pattern.decorate.Counter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : Files
 * Author : zhoujiajun
 * Date : 2018/8/3 16:13
 * Version : 1.0
 * Description : 
 ******************************/
public class Files {

    private static Log log = LogFactory.getLog();

    public static String getEncoding (String name) {
        File file = new File(name);
        return getEncoding(file);
    }

    public static String getEncoding (File file) {
        String enCoding = "";
        try {
            enCoding = new InputStreamReader(new FileInputStream(file)).getEncoding();
        }catch (FileNotFoundException e) {
            log.error(e);
        }
        return enCoding;
    }

    public static File[] create(String... name) {
        Counter counter = Counter.create();
        File[] files = new File[name.length];
        for (String f : name) {
            files[counter.getIndex()] = new File(f);
        }
        return files;
    }

    public static File[] getFiles(String name) {
        return new File(name).listFiles();
    }

    public static File[] getFiles(String... name) {
        List<File> files = new ArrayList<>();
        for (String f : name) {
            Arrays.asList(getFiles(f)).forEach(file -> files.add(file));
        }
        Counter counter = Counter.create();
        File[] files1 = new File[files.size()];
        files.forEach(file -> files1[counter.getIndex()] = file);
        return files1;
    }

}
