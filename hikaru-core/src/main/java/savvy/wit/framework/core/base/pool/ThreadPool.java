package savvy.wit.framework.core.base.pool;

import savvy.wit.framework.core.base.callback.ThreadCallBack;
import savvy.wit.framework.core.base.interfaces.Log;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.util.*;
import java.util.concurrent.ThreadFactory;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ThreadPool
 * Author : zhoujiajun
 * Date : 2018/8/30 12:07
 * Version : 1.0
 * Description : 
 ******************************/
public class ThreadPool implements ThreadFactory {
    private Log log = LogFactory.getLog();

    private static final class LazyInit {
        static final ThreadPool $this = new ThreadPool();
    }

    private ThreadPool() {

    }

    private static ThreadPool thisPool;

    private List<Thread> list;

    private Map<String, Thread> map;

    public List<Thread> getPoolList() {
        return list;
    }

    public Map<String, Thread> getPoolMap() {
        return map;
    }

    public static ThreadPool me() {
        thisPool = LazyInit.$this;
        return thisPool;
    }

    public static ThreadPool NEW() {
        thisPool = new ThreadPool();
        return thisPool;
    }

    @Override
    public Thread newThread(Runnable r) {
        if (list == null) {
            list = new ArrayList<>();
        }
        Thread thread = new Thread(r);
        if (list.size() <= 1000) {
            list.add(thread);
        } else {
            log.warn("Exception in thread \"main\" java.lang.OutOfMemoryError: unable to create new native thread");
            throw new OutOfMemoryError("pool is full");
        }
        return thread;
    }

    public Thread newThread(Runnable r, ThreadCallBack callBack) {
        if (list == null) {
            list = new ArrayList<>();
        }
        Thread thread = new Thread(r);
        try {
            callBack.doThread(thisPool, thread);
        }catch (Exception e) {

        }
        if (list.size() <= 1000) {
            list.add(thread);
        } else {
            log.warn("Exception in thread \"main\" java.lang.OutOfMemoryError: unable to create new native thread");
            throw new OutOfMemoryError("pool is full");
        }
        return thread;
    }

    public ThreadPool newThread(String key, Runnable r) {
        Thread thread = new Thread(r);
        if (map == null) {
            map = new HashMap<>();
        }
        if (map.size() <= 1000) {
            map.put(key, thread);
        } else {
            log.warn("Exception in thread \"main\" java.lang.OutOfMemoryError: unable to create new native thread");
            throw new OutOfMemoryError("pool is full");
        }
        return thisPool;
    }

    public ThreadPool newThread(String key, Runnable r, ThreadCallBack callBack) {
        Thread thread = new Thread(r);
        try {
            callBack.doThread(thisPool, thread);
        }catch (Exception e) {

        }
        if (map == null) {
            map = new HashMap<>();
        }
        if (map.size() <= 1000) {
            map.put(key, thread);
        } else {
            log.warn("Exception in thread \"main\" java.lang.OutOfMemoryError: unable to create new native thread");
            throw new OutOfMemoryError("pool is full");
        }
        return thisPool;
    }

    public void start() {
        if(list != null && list.size() > 0) {
            list.forEach(thread -> thread.start());
        }
        if(map != null && map.size() > 0) {
            map.forEach((s, thread) -> thread.start());
        }
    }

    public ThreadPool start(int index) {
        list.get(index).start();
        return thisPool;
    }

    public ThreadPool start(String key) {
        map.get(key).start();
        return thisPool;
    }

    public void sleep(String key, long time) {
            try {
                if (map.get(key).isAlive()) {
                    Thread.sleep(time);
                }
            }catch (IllegalMonitorStateException e) {
                log.error(e);
            } catch (InterruptedException e) {
                log.error(e);
            }finally {
                log.log("重新启动......");
                map.get(key).interrupt();
                Thread.interrupted();
            }
    }

    public void wait(int index, long time) {
            try {
                list.get(index).wait(time);
            }catch (InterruptedException e) {
                log.error(e);
            }
    }

    public ThreadPool clean() {
        if (thisPool.list != null && thisPool.list.size() > 0) {
            thisPool.list.forEach(thread -> thread.interrupt());
        }
        if (null != thisPool.map && map.size() > 0) {
            thisPool.map.forEach((s, thread) -> thread.interrupt());
        }
        thisPool.list = new ArrayList<>();
        thisPool.map = new HashMap<>();
        System.gc();
        return thisPool;
    }

    public List<Thread> getPool() {
        List<Thread> threads = new ArrayList<>();
        if (thisPool.list != null && thisPool.list.size() > 0) {
            threads.addAll(thisPool.list);
        }
        if (null != thisPool.map && map.size() > 0) {
           threads.addAll(map.values());
        }
        return threads;
    }

    public List<Thread> getActivePool() {
        List<Thread> threads = new ArrayList<>();
        if (thisPool.list != null && thisPool.list.size() > 0) {
            thisPool.list.forEach(thread -> {
                if (!thread.isInterrupted())
                    threads.add(thread);
            });
        }
        if (null != thisPool.map && map.size() > 0) {
            thisPool.map.forEach((s, thread)  -> {
                if (!thread.isInterrupted())
                    threads.add(thread);
            });
        }
        return threads;
    }

}
