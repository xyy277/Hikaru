package com.gsafety.hikaru.common.spider;

import com.gsafety.hikaru.common.global.Result;
import com.gsafety.hikaru.model.system.spider.Acquisition;
import com.gsafety.hikaru.model.system.spider.Content;
import com.gsafety.hikaru.model.system.spider.ContentText;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.base.util.DateUtil;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title :
 * File name : CollectorTest
 * Author : zhoujiajun
 * Date : 2019/3/1 16:21
 * Version : 1.0
 * Description :
 ******************************/
public class CollectorTest {

    private static final Log log = LogFactory.getLog();
    private Map<String,List<String>> collectMap = new HashMap<String,List<String>>();
    private Map<String,CollectThread> threadMap = new HashMap<String,CollectThread>();


    /**
     *  开始采集
     */
    public Result doStart(HttpServletRequest req){
        String id=req.getParameter("id");
        try {
            Acquisition acquisition = null;
            // status = 1 表示开始采集 , 0 停止 , 2 暂停  , 3 暂停后启动
            acquisition.setStatus(0);
            acquisition.setStart_time(DateUtil.getNow());
//            acquisitionService.updateIgnoreNull(acquisition);
            CollectThread collectThread = new CollectThread(acquisition);
            threadMap.put(id, collectThread);
            threadMap.get(id).start();
            return Result.success("globals.result.success");
        } catch (Exception e) {
            return Result.error();
        }
    }
    /**
     *  暂停采集
     */
    public Object pause(HttpServletRequest req){
        try{
            String id=req.getParameter("id");
            Acquisition acquisition= null;
            if(acquisition.getStatus()== 0){
                acquisition.setStatus(0);
                acquisition.setStart_time(DateUtil.getNow());
            }else{
                acquisition.setStatus(0);
                acquisition.setStart_time(DateUtil.getNow());
            }
//            acquisitionService.updateIgnoreNull(acquisition);
            threadMap.get(id).suspend();
            return Result.success("globals.result.success");
        } catch (Exception e) {
            return Result.error();
        }
    }
    /**
     *  停止采集
     */
    public Object doStop(HttpServletRequest req){
        try{
            String id=req.getParameter("id");
            Acquisition acquisition = null;
            acquisition.setStatus(0);
            acquisition.setEnd_time(DateUtil.getNow());
//            acquisitionService.updateIgnoreNull(acquisition);
            if(threadMap!=null && threadMap.size()>0){
                threadMap.get(id).stop();
                threadMap.put(id,null);
            }
            if(collectMap!=null && collectMap.size()>0){
                collectMap.remove(id);
            }
            return Result.success("globals.result.success");
        } catch (Exception e) {
            return Result.error();
        }
    }

    /**
     * 采集线程
     */
    private class CollectThread implements Runnable {
        //线程对象引用
        private Thread myThread;
        private volatile boolean threadSuspended = true;
        private boolean threadFlag = true;
        private Acquisition acquisition;
        private Thread thisThread = null;

        public Acquisition getAcquisition() {
            return this.acquisition;
        }

        public CollectThread(Acquisition acquisition) {
            this.acquisition = acquisition;
        }


        public void start() {
            myThread = new Thread(this, "myThread");
            myThread.start();
        }

        @Override
        public synchronized void run() {
            thisThread = Thread.currentThread();
            try {
                if (myThread == thisThread) {
                    synchronized (this) {
                        BufferedReader br = null;
                        List<String> addrList = new ArrayList<String>();
                        addrList.add(acquisition.getPlan_list());
                        if (acquisition.getDynamic_addr() != null) {
                            int start = acquisition.getDynamic_start();
                            int end = acquisition.getDynamic_end();
                            String addr = acquisition.getDynamic_addr();
                            for (int i = start, count = end; i <= count; i++) {
                                addrList.add(addr.replace("[page]", String.valueOf(i)));
                            }
                        }
                        List<String> urlList = new ArrayList<String>();
                        for (String addr : addrList) {
                            URL url = new URL(addr);
                            InputStreamReader isr = new InputStreamReader(url.openStream(), acquisition.getPage_encoding());
                            br = new BufferedReader(isr);
                            urlList.addAll(getUrlList(br, acquisition.getLinkset_start(), acquisition.getLinkset_end(), acquisition.getLink_start(), acquisition.getLink_end()));

                        }
                        Collections.reverse(urlList);
                        //循环获取url集合
                        int i = 1;
                        List<String> collectList = new ArrayList<String>();
                        collectMap.put(acquisition.getId(), collectList);
                        for (String urlStr : urlList) {
                            if (urlStr.indexOf("/") == 0 || urlStr.indexOf("http") == -1 || urlStr.indexOf("HTTP") == -1) {
                                if(urlStr.indexOf("/") != 0)
                                    urlStr = acquisition.getContent_prefix() + "/" + urlStr;
                                else
                                    urlStr = acquisition.getContent_prefix() + urlStr;
                            }
                            if (threadFlag) {
                                collectMap.put(acquisition.getId(), collectList);
                                while (true) {
                                    if (threadSuspended) {
                                        break;
                                    } else {
                                        Thread.sleep(1000 * 10);
                                    }
                                }
                            } else {
                                return;
                            }
                            i++;
                            Thread.sleep(5000);
                        }
                        br.close(); // 读取完成后关闭读取器
                        // status = 1 表示开始采集 , 0 停止 , 2 暂停  , 3 暂停后启动
//                        acquisition.setStatus(AcquisitionStatusEnum.STOP.getKey());
//                        acquisition.setEnd_time(DateUtil.getNow());
//                        cmsAcquisitionService.updateIgnoreNull(acq);
                        acquisition = null;
                    }
                }
            } catch (InterruptedException e) {
                log.error(e);
                return;
            } catch (IOException e1) {
                log.error(e1);
            }
        }
        /**
         * 停止线程:
         */
        public void stop(){
            threadFlag = false;
        }


        /**
         * 挂起线程：
         */
        public boolean suspend(){
            threadSuspended=!threadSuspended;
            return threadSuspended;
        }

        /**
         *  采集url文章页信息
         * @param urlStr
         */
        String nowUrl = "";
        public void getCollectMsg(String urlStr){
            try {
                nowUrl = urlStr.substring(0,urlStr.lastIndexOf("/"));
                URL url = new URL(urlStr);
                InputStreamReader isr = new InputStreamReader(url.openStream(), acquisition.getPage_encoding());
                BufferedReader br = new BufferedReader(isr);
                String strRead = "";
                Content content = new Content();
                ContentText content_txt = new ContentText();
                //获取组装后的数据
                this.getContent(content, acquisition);
                while ((strRead = br.readLine()) != null) {
                    strRead = strRead.trim();
                    if(strRead.indexOf(acquisition.getTitle_start()) != -1){//标题
                        String titleStr = "";
                        if(strRead.equals(acquisition.getTitle_start())){
                            titleStr = checkNoteHtml(br,acquisition.getTitle_end()).toString();
                        }else{
                            titleStr = strRead.replace(acquisition.getTitle_start(),"").replace(acquisition.getTitle_end(),"");
                        }
                        content.setTitle(titleStr);
                    }else if(strRead.indexOf(acquisition.getContent_start()) != -1){//内容
                        Map<String, StringBuffer> map = checkContentNoteHtml(br,acquisition.getContent_end());
                        content_txt.setTxt(map.get("content").toString());
                        String titlePicPath = map.get("img").toString(); // getFirstPic(brforpic,acquisition.getContent_end());
                        if(!"".equals(titlePicPath)) content.setHas_title_img(titlePicPath);
                    }else if(!"".equals("") && strRead.indexOf(acquisition.getReleasetime_start()) != -1){//发布时间
                        String dateStr = "";
                        if(strRead.equals(acquisition.getReleasetime_start())){
                            dateStr = checkNoteHtml(br,acquisition.getReleasetime_end().toString()).toString().replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
                                    "<[^>]*>", "").replaceAll("[(/>)<]", "");
                        }else{
                            int prepos = strRead.indexOf(acquisition.getReleasetime_start());
                            int sufpos = strRead.indexOf(acquisition.getReleasetime_end());
                            dateStr = strRead.substring(prepos,sufpos);

                            dateStr = dateStr.replace(acquisition.getReleasetime_start(),"").replace(acquisition.getReleasetime_end(),"").replace("&#160;&#160;&#160;&#160;&#160;","");
                        }
                        //content_ext.setAdd_time(dateStr);
                        content.setPub_time(dateStr.trim());

                    }else if(!"".equals("") && strRead.indexOf(acquisition.getAuthor_start()) != -1){//作者
                        String authorStr = "";
                        if(strRead.equals(acquisition.getAuthor_start())){
                            authorStr = checkNoteHtml(br,acquisition.getAuthor_end().toString()).toString().replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
                                    "<[^>]*>", "").replaceAll("[(/>)<]", "");
                        }else{
                            int prepos = strRead.indexOf(acquisition.getAuthor_start());
                            int sufpos = strRead.indexOf(acquisition.getAuthor_end());
                            authorStr = authorStr.substring(prepos,sufpos);
                            authorStr = authorStr.replace(acquisition.getAuthor_start(),"").replace(acquisition.getAuthor_end(),"").replace("&#160;&#160;&#160;&#160;&#160;","");
                        }
                        content.setAuthor(authorStr.trim());
                    }else if(!"".equals("") && strRead.indexOf(acquisition.getOrigin_start()) != -1){//来源
                        String originStr = "";
                        if(strRead.equals(acquisition.getOrigin_start())){
                            originStr = checkNoteHtml(br,acquisition.getOrigin_end().toString()).toString().replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
                                    "<[^>]*>", "").replaceAll("[(/>)<]", "");
                        }else{
                            originStr = strRead.replace(acquisition.getOrigin_start(),"").replace(acquisition.getOrigin_end(),"");
                        }
                        content.setOrigin(originStr.trim());
                    }
                }
//                AcquisitionService.saveCollectMsg(content,content_txt);

            } catch (Exception e) {
                log.error(e);
            }
        }

        private StringBuffer checkNoteHtml(BufferedReader br,String endHtml){
            StringBuffer sb = new StringBuffer();
            try {
                String strRead = "";
                endHtml = endHtml.trim();
                while((strRead = br.readLine()) != null){
                    strRead = strRead.trim();
                    if(strRead.indexOf(endHtml) == -1){
                        if(strRead.indexOf("src=\"/") != -1){
                            strRead = strRead.replace("src=\"/","src=\""+acquisition.getImg_prefix()+"/");
                        }else if(strRead.indexOf("src=\"./") != -1){
                            strRead = strRead.replace("src=\"./","src=\""+nowUrl+"/");
                        }else if(strRead.indexOf("SRC=\"") != -1){
                            strRead = strRead.replace("SRC=\"/","src=\""+acquisition.getImg_prefix()+"/");
                        }else if(strRead.toLowerCase().indexOf("href=\"/") != -1){
                            strRead = strRead.replace("href=\"/","href=\""+acquisition.getContent_prefix()+"/");
                        }else if(strRead.toLowerCase().indexOf("HREF=\"/") != -1){
                            strRead = strRead.replace("HREF=\"/","href=\""+acquisition.getContent_prefix()+"/");
                        }
                        sb.append(strRead);
                    }else{
                        return sb;
                    }
                }
            } catch (Exception e) {
                log.error(e);
            }
            return sb;
        }

        private Map<String, StringBuffer> checkContentNoteHtml(BufferedReader br,String endHtml){
            StringBuffer sb = new StringBuffer();
            StringBuffer sbImg = new StringBuffer();
            Map<String, StringBuffer> map = new HashMap<String, StringBuffer>();
            String strPicPath = "";
            try {
                String strRead = "";
                String IMGSRC_REG = "http:\"?(.*?)(\"|>|\\s+)";
                endHtml = endHtml.trim();
                int first = 0;
                while((strRead = br.readLine()) != null){
                    strRead = strRead.trim();
                    if(strRead.indexOf(endHtml) == -1){
                        if(strRead.indexOf("src=\"/") != -1){
                            strRead = strRead.replace("src=\"/","src=\""+acquisition.getImg_prefix()+"/");
                        }else if(strRead.indexOf("src=\"./") != -1){
                            strRead = strRead.replace("src=\"./","src=\""+nowUrl+"/");
                        }else if(strRead.indexOf("SRC=\"") != -1){
                            strRead = strRead.replace("SRC=\"/","src=\""+acquisition.getImg_prefix()+"/");
                        }else if(strRead.toLowerCase().indexOf("href=\"/") != -1){
                            strRead = strRead.replace("href=\"/","href=\""+acquisition.getContent_prefix()+"/");
                        }else if(strRead.toLowerCase().indexOf("HREF=\"/") != -1){
                            strRead = strRead.replace("HREF=\"/","href=\""+acquisition.getContent_prefix()+"/");
                        }
                        if(first<1 && strRead.indexOf("src=")>0){
                            Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(strRead);
                            while (matcher.find()) {
                                strPicPath = matcher.group().substring(0, matcher.group().length() - 1);
                                sbImg.append(strPicPath);
                                first = 1;
                                break;
                            }
                        }
                        sb.append(strRead);
                    }else{
                        map.put("content",sb);
                        map.put("img",sbImg);
                        return map;
                    }
                }
            } catch (Exception e) {
                log.error(e);
            }
            map.put("content",sb);
            map.put("img",sbImg);
            return map;
        }

        private String getFirstPic(BufferedReader br,String endHtml){
            String strPicPath = "";
            try {
                String strRead = "";
                endHtml = endHtml.trim();
                String IMGSRC_REG = "http:\"?(.*?)(\"|>|\\s+)";
                while((strRead = br.readLine()) != null){
                    strRead = strRead.trim();
                    if(strRead.indexOf(endHtml) == -1){
                        if(strRead.indexOf("src=\"/") != -1){
                            strRead = strRead.replace("src=\"/","src=\""+acquisition.getImg_prefix()+"/");
                            Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(strRead);
                            while (matcher.find()) {
                                strPicPath = matcher.group().substring(0, matcher.group().length() - 1);
                                break;
                            }
                            break;
                        }else if(strRead.indexOf("src=\"./") != -1){
                            strRead = strRead.replace("src=\"./","src=\""+nowUrl+"/");
                            Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(strRead);
                            while (matcher.find()) {
                                strPicPath = matcher.group().substring(0, matcher.group().length() - 1);
                                break;
                            }
                            break;
                        }else if(strRead.indexOf("SRC=\"") != -1){
                            strRead = strRead.replace("SRC=\"/","src=\""+acquisition.getImg_prefix()+"/");
                            Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(strRead);
                            while (matcher.find()) {
                                strPicPath = matcher.group().substring(0, matcher.group().length() - 1);
                                break;
                            }
                            break;
                        }
                    }else{
                        return strPicPath;
                    }
                }
            } catch (Exception e) {
                log.error(e);
            }
            return strPicPath;
        }

        /**
         * 组装文章信息
         * @param content 文章Model
         * @param acq 采集Model
         */
        private void getContent(Content content,Acquisition acq){
            /** 插入Content 记录**/
            try {
                content.setSite_id("");
                content.setChannel_id("");
                content.setModel_id(acquisition.getModel_id());
                content.setUser_id("");
                content.setUnit_id("");
                content.setSort_time(DateUtil.getNow());
                content.setStatus(3);// 0表示草稿，3表示已终审 ContentStatusEnum.DRAFTS.getKey()
                //content.setHas_title_img("0");
                /** 插入 Content_ext 记录 根据 Content.content_id **/
                content.setPub_time(DateUtil.getNow());
                content.setIs_bold("N");
                content.setAuthor("");
                content.setLink_("");
                content.setAdd_time(DateUtil.getNow());
                content.setIs_static(1); //CmsIsStaticEnum.RELEASED.getKey() java.lang.NoClassDefFoundError: Could not initialize class CmsIsStaticEnum
                content.setIs_index(0);
                String app_root = "";
                String tplPath = app_root + "/WEB-INF/cmstemplate/kebo/default/content/新闻内容_文章展示.html";  //"D:/WorkSpace/kebo/project/web/sc-app/sc-web/target/aebizcms/WEB-INF/cmstemplate/kebo/default/content/新闻内容_文章展示.html";//
                tplPath = tplPath.replaceAll("\\\\|//", "/");
                tplPath = tplPath.replaceAll("//", "/");
                content.setTpl_content(tplPath);
            }
            catch (Exception e) {
                log.error(e);
            }
        }
    }

    /**
     *  获取url列表
     * @param br
     * @param htmlStart
     * @param htmlEnd
     * @param urlStart
     * @param urlEnd
     * @return
     * @throws IOException
     */
    private List<String> getUrlList(BufferedReader br,String htmlStart,String htmlEnd,String urlStart,String urlEnd) throws  IOException{
        // 如果 BufferedReader 读到的内容不为空
        String strRead = ""; // 新增一个空字符串strRead来装载 BufferedReader 读取到的内容
        List<String> htmlList = new ArrayList<String>();
        while ((strRead = br.readLine()) != null) {
            if(strRead.trim().indexOf(htmlStart)!=-1){
                while((strRead = br.readLine()) != null){
                    if(!strRead.trim().equals(htmlEnd)){
                        htmlList.add(strRead.trim());
                    }else{
                        break;
                    }
                }
            }
        }
        List<String> urlList = new ArrayList<String>();
        for(String html : htmlList) {
            if(html.indexOf(urlStart)!=-1){
                if(html.indexOf(urlEnd)!=-1)
                {
                    String[] newlist = html.split("</li><li>");
                    for(String onenew : newlist)
                    {
                        onenew = onenew.substring(onenew.indexOf(urlStart),onenew.lastIndexOf(urlEnd));
                        urlList.add(onenew.replace(urlStart, "").replace(urlEnd, ""));
                    }
                }
                else{
                    html = html.substring(html.indexOf(urlStart),html.lastIndexOf(urlEnd));
                    urlList.add(html.replace(urlStart, "").replace(urlEnd, ""));
                }

            }
        }
        return urlList;
    }
}
