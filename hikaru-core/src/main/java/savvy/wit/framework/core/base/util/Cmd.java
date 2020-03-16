package savvy.wit.framework.core.base.util;

import savvy.wit.framework.core.base.callback.CmdCallBack;
import savvy.wit.framework.core.base.callback.StringCallBack;
import savvy.wit.framework.core.base.global.Storage;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.io.*;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : Cmd
 * File name : Cmd
 * Author : zhoujiajun
 * Date : 2020/2/27 17:13
 * Version : 1.0
 * Description : 对Runtime做初步代理
 * 单例
 *********************************/
public class Cmd {

    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    private static Log print = LogFactory.getLog();

    private SuffixHandler suffixHandler;

    private PrefixHandler prefixHandler;

    private static Cmd instance;

    private final Runtime runtime;
    private Storage storage;

    private String encoding = "GBK";
    private Cmd () {
        runtime = Runtime.getRuntime();
        storage = Storage.getInstance();
    }

    /**
     * 初始化
     * @return instance
     */
    public static Cmd get() {
        if (instance == null)
            instance = LazyInit.INITIALIZATION;
        return instance.clean();
    }

    public static Cmd NEW() {
        return new Cmd();
    }

    private static class LazyInit {
        private static final Cmd INITIALIZATION = new Cmd();
    }

    /**
     * 执行command
     * @param key 键
     * @param command 命令
     * @return instance
     */
    public synchronized Cmd proxy(Object key, String command, CmdCallBack callBack) {
        if (storage.get(key) == null || !((Boolean) storage.get(key))) {
            try {
                final Process process = runtime.exec(commandAdapter(command));
                executorService.submit(() -> {
                    if (process != null && callBack != null)
                        callBack.execute(process);
                    else
                        getCmdCallBack().execute(process);
                });
                storage.set(key, process.isAlive());
            } catch (IOException e) {
                print.error(e);
            }
        }
        return this;
    }

    /**
     * 执行command
     * @param key 键
     * @param commands 命令
     * @return instance
     */
    public synchronized Cmd proxy(Object key, String... commands) {
        StringBuilder builder = new StringBuilder();
        for (String command : commands) {
            builder.append(command + " ");
        }
        return proxy(key, builder.toString(), null);
    }

    /**
     * 执行command
     * @param commands 命令
     * @return instance
     */
    public synchronized Cmd proxy(CmdCallBack callBack, String... commands) {
        try {
            StringBuilder builder = new StringBuilder();
            for (String command : commands) {
                builder.append(command + " ");
            }
            final Process process = runtime.exec(commandAdapter(builder.toString()));
            executorService.submit(() -> {
                if (process != null && callBack != null)
                    callBack.execute(process);
                else
                    getCmdCallBack().execute(process);
            });
        } catch (IOException e) {
            print.error(e);
        }
        return this;
    }

    /**
     * 执行command
     * @param commands 命令
     * @return instance
     */
    public synchronized Cmd proxy(String... commands) {
        return proxy(getCmdCallBack(), commands);
    }

    public synchronized void execute(String processKey, String command, boolean monitoring) {
        Process process;
        try {
            process = runtime.exec(commandAdapter(command));
            storage.set(processKey, process.isAlive());
            while (process.isAlive()) {
                if (!monitoring) {
                    break;
                }
                readLine(process.getInputStream(), encoding, string -> print.println(string).toString());
            }
            if (monitoring) {
                readLine(process.getErrorStream(), encoding, string -> print.println(string).toString());
            }
            print.print("80*=").print("["+ processKey +" ] ").print("<< process isAlive: " + process.isAlive() + " >>").println("80*=");
        } catch (Exception e) {
            print.error(e);
        }
    }
    
    public synchronized void execute(String command, boolean monitoring) {
        Process process = null;
        try {
            process = runtime.exec(commandAdapter(command));
            while (process != null && process.isAlive()) {
                if (!monitoring) {
                    break;
                }
                readLine(process.getInputStream(), encoding, string -> print.println(string).toString());
            }
            if (monitoring) {
                readLine(process.getErrorStream(), encoding, string -> print.println(string).toString());
            }
            print.print("80*=").print("<< process isAlive: " + process.isAlive() + " >>").println("80*=");
        } catch (Exception e) {
            print.error(e);
        }
    }

    public synchronized void execute(String... commands) {
        int i = 0;
        for (String command : commands) {
            print.println(command);
            try {
                Process process = runtime.exec(commandAdapter(command));
                if (process != null && process.isAlive())
                    print.print("80*=").print("["+ (i++) +" ] ").print("<< process isAlive: " + process.isAlive() + " >>").println("80*=");
            } catch (IOException e) {
                print.error(e);
            }
        }
    }

    /**
     * 设置encoding
     * @param encoding 编码
     * @return instance
     */
    public Cmd encoding(String encoding) {
        this.encoding = encoding;
        return LazyInit.INITIALIZATION;
    }
    /**
     * 销毁创建得process
     * @param key 键
     * @return instance
     */
    public Cmd close(String key) {
        storage.getStorage().remove(key);
        return this;
    }

    private Cmd clean() {
        this.prefixHandler = null;
        this.suffixHandler = null;
        return this;
    }

    public Cmd prefixHandler(PrefixHandler handler) {
        this.prefixHandler = handler;
        return this;
    }

    public Cmd suffixHandler(SuffixHandler handler) {
        this.suffixHandler = handler;
        return this;
    }

    /**
     * 命令适配不同曹组系统
     * @param command 预命令
     * @return 命令
     */
    private String commandAdapter(String command) {
        print.print(DateUtil.getNow("yyyy-MM-dd HH:mm:ss.SSS :"))
                .print("10* ", "[ ",command, " ]")
                .println();
        String system = System.getProperty("os.name");
        if (system.toUpperCase().indexOf("WINDOWS") != -1) {
            command = "cmd /c " + command;
        } else if (system.toUpperCase().indexOf("LINUX") != -1) {
            command = "sc -c " + command;
        }
        return command;
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
            print.error(e);
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

    public Runtime getRuntime() {
        return runtime;
    }

    public Storage getStorage() {
        return storage;
    }

    /**
     * 处理流防止死锁
     * @return back
     */
    private CmdCallBack getCmdCallBack() {
        return process -> {
            // 前置处理
            if (prefixHandler != null) {
                prefixHandler.handle();
            }
            try {
                //获取进程的标准输入流
                final InputStream is1 = process.getInputStream();
                //获取进程的错误流
                final InputStream is2 = process.getErrorStream();
                //启动两个线程，一个线程负责读标准输出流，另一个负责读标准错误流
                new Thread() {
                    public void run() {
                        BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));
                        try {
                            String line1 = null;
                            while ((line1 = br1.readLine()) != null) {
                                if (line1 != null){
                                    print.println("[stdout] " + line1);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        finally{
                            try {
                                is1.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();

                new Thread() {
                    public void  run() {
                        BufferedReader br2 = new  BufferedReader(new  InputStreamReader(is2));
                        try {
                            String line2 = null ;
                            while ((line2 = br2.readLine()) !=  null ) {
                                if (line2 != null){
                                    print.println("[IM] " + line2);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        finally{
                            try {
                                is2.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();

                //可能导致进程阻塞，甚至死锁
                int ret = process.waitFor();
                System.out.println("return value:"+ret);
                System.out.println(process.exitValue());
                print.print("event:{", "RunExeForWindows",String.valueOf(process.exitValue()), "}").println();
                byte[] bytes = new byte[process.getInputStream().available()];
                process.getInputStream().read(bytes);
                System.out.println(new String(bytes));
                print.print("event:{", "RunExeForWindows",new String(bytes), "}").println();
            } catch (Exception ex) {
                print.warn(ex.getMessage());
                try{
                    process.getErrorStream().close();
                    process.getInputStream().close();
                    process.getOutputStream().close();
                }
                catch(Exception ee){}
            } finally {
                // 后置处理
                if (suffixHandler != null) {
                    suffixHandler.handle();
                }
            }
            return null;
        };
    }
}
