package com.github.qingquanlv.testflow_service_api.service;

import org.quartz.SchedulerConfigException;
import org.quartz.SchedulerException;

/**
 * @Author Qingquan Lv
 * @Date 2021/6/6 21:01
 * @Version 1.0
 */
public interface IScheduleJobService {

    void startJob();

    void stopJob(String taskId) throws SchedulerException;

    void resumeJob(String taskId) throws SchedulerException;

    void loadJob(String taskId) throws SchedulerConfigException;

    void unloadJob(String taskId) throws SchedulerException;

    void reload(String taskId) throws Exception;

}
