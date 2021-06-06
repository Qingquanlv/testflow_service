package com.github.qingquanlv.testflow_service_api.listener;

/**
 * @Author Qingquan Lv
 * @Date 2021/6/6 1:15
 * @Version 1.0
 */

import com.github.qingquanlv.testflow_service_api.service.impl.ScheduleJobImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 监听容器启动,并开始从数据库加载定时任务
 */
@Component
public class ScheduleJobInitListener implements CommandLineRunner {

    @Autowired
    private ScheduleJobImpl jobService;

    @Override
    public void run(String... strings) throws Exception {
        jobService.startJob();
    }
}
