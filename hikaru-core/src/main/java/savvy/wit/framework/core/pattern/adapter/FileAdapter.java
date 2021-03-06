package savvy.wit.framework.core.pattern.adapter;

import org.springframework.web.multipart.MultipartFile;
import savvy.wit.framework.core.base.callback.FileCacheCallBack;
import savvy.wit.framework.core.base.callback.FileCallBack;
import savvy.wit.framework.core.base.callback.StringCallBack;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.base.util.StringUtil;
import savvy.wit.framework.core.pattern.factory.Files;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.io.*;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : FileAdapter
 * Author : zhoujiajun
 * Date : 2018/8/3 16:11
 * Version : 1.0
 * Description : 
 ******************************/
public class FileAdapter {
    private Log log = LogFactory.getLog();
    private static class LazyInit {
        private static FileAdapter INITIALIZATION = new FileAdapter();
    }

    private File[] files;

    public File[] getFiles() {
        return files;
    }

    public void setFiles(File[] files) {
        this.files = files;
    }

    public static FileAdapter me() {
        return LazyInit.INITIALIZATION;
    }

    public static FileAdapter NEW() {
        return new FileAdapter();
    }


    public OutputStream getOutputStream(String fileName) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(fileName);
        }catch (Exception e) {
            log.error(e);
        }
        return out;
    }

    public void readFile(File target, boolean append,
                         String inEnCoding, String outEnCoding, FileCallBack callBack, File... files) {
        for(File file1 : files) {
            BufferedReader br = null;
            BufferedWriter bw = null;
            try {
                br = getBuffReader(file1, inEnCoding);
                if (null != target)
                    bw = getBuffWriter(target, append, outEnCoding);
                callBack.fileReaderHelper(br,bw);
                if (br != null) br.close();
                if (bw != null) bw.close();
            } catch (Exception e) {
                log.error(e);
            }
        }
    }
    public void readFile(File target, boolean append, String outEnCoding, FileCallBack callBack, File... files) {
        for(File file1 : files) {
            try {
                String inEnCoding =  new InputStreamReader(new FileInputStream(file1)).getEncoding();
                BufferedReader br = getBuffReader(file1, inEnCoding);
                BufferedWriter bw = getBuffWriter(target, append, outEnCoding);
                callBack.fileReaderHelper(br,bw);
                if (br != null) br.close();
                if (bw != null) bw.close();
            } catch (Exception e) {
                log.error(e);
            }
        }
    }

    public void readFile(FileCallBack callBack, File... files) {
        for(File file1 : files) {
            try {
                String inEnCoding =  new InputStreamReader(new FileInputStream(file1)).getEncoding();
                BufferedReader br = getBuffReader(file1, inEnCoding);
                callBack.fileReaderHelper(br,null);
                if (br != null) br.close();
            } catch (Exception e) {
                log.error(e);
            }
        }
    }

    public void readLine(File file, StringCallBack callBack) {
        try {
            String inEnCoding =  Files.getEncoding(file);
            BufferedReader br = getBuffReader(file, inEnCoding);
            String line;
            while ((line = br.readLine()) != null) {
                callBack.getParamValue(line);
            }
            if (br != null) br.close();
        } catch (Exception e) {
            log.error(e);
        }

    }

    public void readLine(InputStream inputStream, String encoding, StringCallBack callBack) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, encoding));
            String line;
            while ((line = br.readLine()) != null) {
                callBack.getParamValue(line);
            }
            if (br != null) br.close();
        } catch (Exception e) {
            log.error(e);
        }
    }

    public void readLine(String name, StringCallBack callBack) {
        try {
            File file = new File(name);
            String inEnCoding =  Files.getEncoding(file);
            BufferedReader br = getBuffReader(file, inEnCoding);
            String line;
            while ((line = br.readLine()) != null) {
                callBack.getParamValue(line);
            }
            if (br != null) br.close();
        } catch (Exception e) {
            log.error(e);
        }

    }

    public void readLine(String name, String inEnCoding, StringCallBack callBack) {
        try {
            File file = new File(name);
            BufferedReader br = getBuffReader(file, inEnCoding);
            String line;
            while ((line = br.readLine()) != null) {
                callBack.getParamValue(line);
            }
            if (br != null) br.close();
        } catch (Exception e) {
            log.error(e);
        }

    }

    public void readCacheFile(FileCacheCallBack callBack, File... files) {
        setFiles(files);
        for(File file1 : files) {
            try {
                String inEnCoding =  new InputStreamReader(new FileInputStream(file1)).getEncoding();
                BufferedReader br = getBuffReader(file1, inEnCoding);
//                BufferedWriter bw = getBuffWriter(target, true, inEnCoding);
                callBack.readCache(br, null, file1, LazyInit.INITIALIZATION);
                if (br != null) br.close();
//                if (bw != null) bw.close();
            } catch (Exception e) {
                log.error(e);
            }
        }
    }

    public void readFile(FileCallBack callBack, File[]... files) {
        for(File[] file : files) {
            readFile(callBack, file);
        }
    }

    public void readFile(String target, boolean append,
                         String inEncoding, String outEnCoding, FileCallBack callBack, String... path) {
        if(path.length > 1) {
            File[] files = new File[path.length];
            for(int var = 0; var < path.length; var++) {
                files[var] = new File(path[var]);
            }
            readFile(StringUtil.isNotBlank(target) ?
                    new File(target) :
                    null, append,inEncoding,outEnCoding, callBack, files);
        } else {
            if(new File(path[0]).isDirectory()) {
                File[] files = new File(path[0]).listFiles();
                readFile(StringUtil.isNotBlank(target) ?
                        new File(target) :
                        null, append,inEncoding,outEnCoding, callBack, files);
            } else {
                readFile(StringUtil.isNotBlank(target) ?
                        new File(target) :
                        null, append,inEncoding,outEnCoding, callBack, new File(path[0]));
            }
        }
    }

    public void readFile(String target, FileCallBack callBack, String... path) {
        if(path.length > 1) {
            File[] files = new File[path.length];
            for(int var = 0; var < path.length; var++) {
                files[var] = new File(path[var]);
            }
            readFile(StringUtil.isNotBlank(target) ?
                    new File(target) :
                    null, StringUtil.isBlank(target) ? false : true,
                    Files.getEncoding(files[0]),
                    Files.getEncoding(files[0]),
                    callBack, files);
        } else {
            if(new File(path[0]).isDirectory()) {
                File[] files = new File(path[0]).listFiles();
                readFile(StringUtil.isNotBlank(target) ?
                        new File(target) :
                        null, StringUtil.isBlank(target) ? false : true,
                        Files.getEncoding(files[0]),
                        Files.getEncoding(files[0]),
                        callBack, files);
            } else {
                readFile(StringUtil.isNotBlank(target) ?
                        new File(target) :
                        null, StringUtil.isBlank(target) ? false : true,
                        Files.getEncoding(new File(path[0])),
                        Files.getEncoding(new File(path[0])), callBack,
                        new File(path[0]));
            }
        }
    }

    public void writer(File target, String... content) {
        try {
            for (String s : content) {
                FileWriter fw = new FileWriter(target,true);
                fw.write(s + "\n");
                fw.flush();
                fw.close();
            }
        }catch (Exception e) {
            log.error(e);
        }
    }

    public void writer(String target, String... content) {
        try {
            for (String s : content) {
                FileWriter fw = new FileWriter(new File(target),true);
                fw.write(s + "\n");
                fw.flush();
                fw.close();
            }
        }catch (Exception e) {
            log.error(e);
        }
    }

    public void lazyWriter(File target, String... content) {
        try {
            FileWriter fw = new FileWriter(target,true);
            for (String s : content) {
                fw.write(s + "\n");
            }
            fw.flush();
            fw.close();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void lazyWriter(String target, String... content) {
        try {
            FileWriter fw = new FileWriter(new File(target),true);
            for (String s : content) {
                fw.write(s + "\n");
            }
            fw.flush();
            fw.close();
        }catch (Exception e) {
            log.error(e);
        }
    }

    public void writerBuffer(File target, int size, String... content) {
        try {
            FileWriter fw = new FileWriter(target,true);
            for (int var =0; var < content.length; var++) {
                fw.write(content[var] + "\n");
                if(var+1 % size == 0) {
                    fw.flush();
                }
            }
            fw.flush();
            fw.close();
        }catch (Exception e) {
            log.error(e);
        }
    }

    public void writerBuffer(String target, int size, String... content) {
        try {
            FileWriter fw = new FileWriter(new File(target),true);
            for (int var =0; var < content.length; var++) {
                fw.write(content[var] + "\n");
                if(var+1 % size == 0) {
                    fw.flush();
                }
            }
            fw.flush();
            fw.close();
        }catch (Exception e) {
            log.error(e);
        }
    }

    private BufferedReader getBuffReader(File file, String inEnCoding) {
        try {
            return StringUtil.isNotBlank(inEnCoding) ?
                    new BufferedReader(new InputStreamReader(new FileInputStream(file),inEnCoding)) :
                    new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        }catch (Exception e) {
            return null;
        }
    }

    private BufferedWriter getBuffWriter(File target, boolean append, String outEnCoding) {
        try {
            return target != null ? StringUtil.isNotBlank(outEnCoding) ?
                    new BufferedWriter(new OutputStreamWriter(new FileOutputStream(target, append),outEnCoding)) :
                    new BufferedWriter(new OutputStreamWriter(new FileOutputStream(target, append))) : null;
        }catch (Exception e) {
            return null;
        }
    }


    public static File multipartFileToFile(MultipartFile file) {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            try {
                ins = file.getInputStream();
                toFile = new File(file.getOriginalFilename());
                inputStreamToFile(ins, toFile);
                ins.close();
            } catch (IOException e) {

            } finally {
                if (ins != null)
                    try {
                        ins.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }

        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除本地临时文件
     * @param file
     */
    public static void deleteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }
    }
}
