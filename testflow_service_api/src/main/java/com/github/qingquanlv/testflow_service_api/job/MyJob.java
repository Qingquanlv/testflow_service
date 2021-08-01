package com.github.qingquanlv.testflow_service_api.job;

import com.github.qingquanlv.testflow_service_api.service.impl.ScheduleJobImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author Qingquan Lv
 * @Date 2021/6/6 1:16
 * @Version 1.0
 */
public class MyJob implements Job {

    @Autowired
    private ScheduleJobImpl jobService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("定时任务开始"+now.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        System.out.println(jobExecutionContext.getJobDetail().getKey());
        System.out.println("执行task" + jobExecutionContext.getJobDetail().getKey());
        jobService.execJob(Long.valueOf(jobExecutionContext.getJobDetail().getKey().getName()));
    }
}
