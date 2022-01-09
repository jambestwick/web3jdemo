package com.we3j.demo.utils;

import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author jambestwick
 * @create 2022/1/10 0010  1:17
 * @email jambestwick@126.com
 * 定时器写法
 */
public class ScheduleTask {


    /****
     *
     * 定时器cron 原理规则 https://cron.qqe2.com/
     * ***/
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //表示每2秒执行一次
    @Scheduled(cron = "0/2 * * * * *")
    public void timer(){
        //获取当前时间
        LocalDateTime localDateTime =LocalDateTime.now();
        System.out.println("当前时间为:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    //每天3：05执行
    @Scheduled(cron = "0 05 03 ? * *")
    public void testTasks() {
        System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
    }

}
