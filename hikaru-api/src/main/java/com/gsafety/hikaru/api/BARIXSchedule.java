package com.gsafety.hikaru.api;

import com.gsafety.hikaru.service.BARIXScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledFuture;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : BARIXSchedule
 * File name : BARIXSchedule
 * Author : zhoujiajun
 * Date : 2020/3/2 15:34
 * Version : 1.0
 * Description :    定时规则
 * 字段	                允许值	                            允许的特殊字符
 * 秒（Seconds）	        0~59的整数	                        , - * /    四个字符
 * 分（Minutes）	        0~59的整数	                        , - * /    四个字符
 * 小时（Hours）	        0~23的整数	                        , - * /    四个字符
 * 日期（DayofMonth）	1~31的整数（但是你需要考虑你月的天数）	,- * ? / L W C     八个字符
 * 月份（Month）	        1~12的整数或者 JAN-DEC	            , - * /    四个字符
 * 星期（DayofWeek）	    1~7的整数或者 SUN-SAT （1=SUN）	    , - * ? / L C #     八个字符
 * 年(可选，留空)（Year）	1970~2099	                        , - * /    四个字符
 *      1）*：表示匹配该域的任意值。假如在Minutes域使用*, 即表示每分钟都会触发事件。
 *
 * 　　（2）?：只能用在DayofMonth和DayofWeek两个域。它也匹配域的任意值，但实际不会。因为DayofMonth和DayofWeek会相互影响。例如想在每月的20日触发调度，不管20日到底是星期几，则只能使用如下写法： 13 13 15 20 * ?, 其中最后一位只能用？，而不能使用*，如果使用*表示不管星期几都会触发，实际上并不是这样。
 *
 * 　　（3）-：表示范围。例如在Minutes域使用5-20，表示从5分到20分钟每分钟触发一次
 *
 * 　　（4）/：表示起始时间开始触发，然后每隔固定时间触发一次。例如在Minutes域使用5/20,则意味着5分钟触发一次，而25，45等分别触发一次.
 *
 * 　　（5）,：表示列出枚举值。例如：在Minutes域使用5,20，则意味着在5和20分每分钟触发一次。
 *
 * 　　（6）L：表示最后，只能出现在DayofWeek和DayofMonth域。如果在DayofWeek域使用5L,意味着在最后的一个星期四触发。
 *
 * 　　（7）W:表示有效工作日(周一到周五),只能出现在DayofMonth域，系统将在离指定日期的最近的有效工作日触发事件。例如：在 DayofMonth使用5W，如果5日是星期六，则将在最近的工作日：星期五，即4日触发。如果5日是星期天，则在6日(周一)触发；如果5日在星期一到星期五中的一天，则就在5日触发。另外一点，W的最近寻找不会跨过月份 。
 *
 * 　　（8）LW:这两个字符可以连用，表示在某个月最后一个工作日，即最后一个星期五。
 *
 * 　　（9）#:用于确定每个月第几个星期几，只能出现在DayofMonth域。例如在4#2，表示某月的第二个星期三。
 *********************************/
@Api("PA设备定时播放控制器")
@RestController
@RequestMapping("/pow/arms/v1/pa/barix/play")
public class BARIXSchedule {
    @Autowired
    BARIXScheduleService barixScheduleService;

    private Log print = LogFactory.getLog();
    /**
     * 定时播放
     * 需要按时执行的任务，如果有多种任务，可以创建多种任务类
     */
    class TaskPlaying implements Runnable {

        private String fileId;

        public TaskPlaying(String fileId) {
            this.fileId = fileId;
        }

        @Override
        public void run(){
            print.log("TaskPlaying is running... ...");
            barixScheduleService.play(fileId);
        }
    }


    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        return new ThreadPoolTaskScheduler();
    }

    private ScheduledFuture<?> future;

    @PostConstruct
    private void postConstruct(){
        startCron();
    }
    // 项目启动的时候就启动定时任务，初始的corn可以去数据库里读取
    private void startCron() {
        // 测试默认每5分钟
        String corn = "0 0/5 * * * *";
        String fileId = "test_file_id";
        // 查询历史corn 和 fileId
        print.log("start task playing BARIx by corn");
        future = threadPoolTaskScheduler.schedule(new TaskPlaying(fileId), new CronTrigger(corn));

    }


    private void stopCron(){

        if(future != null) {
            future.cancel(true);
        }

    }

    /**
     * 通过文件id与定时规则定时发送音频流
     * @param id    文件id
     * @param corn  定时规则 --> * /5 * * * * *
     * @return
     */
    @ApiOperation(value = "设置定时规则", notes = "设置定时规则")
    @RequestMapping(value = "/changeCorn/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String startPlayingByCorn(@RequestParam("id")@ApiParam("文件id") String id,
                                     @RequestParam("corn")@ApiParam("定时规则corn表达式") String corn){
        stopCron();
        // "*/12 * * * * *"
        future = threadPoolTaskScheduler.schedule(new TaskPlaying(id), new CronTrigger(corn));
        // 存 corn
        print.log("change success");
        return "OK";
    }

}
