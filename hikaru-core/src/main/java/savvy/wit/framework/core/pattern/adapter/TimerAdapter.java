package savvy.wit.framework.core.pattern.adapter;

import savvy.wit.framework.core.base.callback.TimerCallBack;
import savvy.wit.framework.core.base.interfaces.Log;
import savvy.wit.framework.core.base.pool.ThreadPool;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : TimerAdapter
 * Author : zhoujiajun
 * Date : 2018/10/22 11:10
 * Version : 1.0
 * Description : 
 ******************************/
public class TimerAdapter {

    private Log log = LogFactory.getLog();

    private static TimerAdapter $this;

    private List<Timer> timers = new ArrayList<>();

    public List<Timer> getTimers() {
        return timers;
    }

    public void setTimers(List<Timer> timers) {
        this.timers = timers;
    }

    private static class LazyInit {
        private static TimerAdapter INITIALIZATION = new TimerAdapter();
    }

    public static TimerAdapter me() {
        $this  = LazyInit.INITIALIZATION;
        return $this;
    }

    public TimerAdapter execute(TimerTask task, long time) {
        Timer timer = new Timer();
        timer.schedule(task, time);
        timers.add(timer);
        return $this;
    }

    public TimerAdapter execute(TimerTask task, long delay, long time) {
        Timer timer = new Timer();
        timer.schedule(task, delay, time);
        timers.add(timer);
        return $this;
    }

    public synchronized void cancel(int index) {
        Timer timer = timers.get(index);
        if (timer != null) {
            timer.cancel();
        }
    }

    public void cancel() {
        if (timers.size() > 0) {
            timers.forEach(timer -> timer.cancel());
        }
        timers = new ArrayList<>();
    }

    public void pause(TimerCallBack callBack, long delay) {
            if (delay <= 0) {
                try {
                    callBack.something();
                }catch (Exception e) {
                    log.error(e);
                }
            } else {
                log.log("等待中......");
                final Timer t = new Timer();
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            callBack.something();
                        }catch (Exception e) {
                            log.error(e);
                        }
                        t.cancel();
                    }
                }, delay);
            }
        ThreadPool.me().newThread("pause", () -> {
            log.log("主线程暂停中......");
        }).start("pause").sleep("pause", delay);
    }
}
