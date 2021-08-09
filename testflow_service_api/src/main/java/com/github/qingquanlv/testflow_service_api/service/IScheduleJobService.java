package com.github.qingquanlv.testflow_service_api.service;

import com.github.qingquanlv.testflow_service_api.entity.job.createjob.CreateJobRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.createjob.CreateJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.deletejob.DeleteJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.queryalljob.QueryAllJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.setstatus.SetStatusRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.setstatus.SetStatusResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.updatejob.UpdateJobRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.updatejob.UpdateJobResponse;
import org.quartz.SchedulerConfigException;
import org.quartz.SchedulerException;

/**
 * @Author Qingquan Lv
 * @Date 2021/6/6 21:01
 * @Version 1.0
 */
public interface IScheduleJobService {

    void execJob(Long taskId);

    CreateJobResponse createJob(CreateJobRequest request);

    DeleteJobResponse deleteJob(Long taskId);

    UpdateJobResponse updateJob(UpdateJobRequest request);

    QueryAllJobResponse queryAllJob();

    SetStatusResponse updateStatus(SetStatusRequest request);

    void startJob();

    void stopJob(String taskId) throws SchedulerException;

    void resumeJob(String taskId) throws SchedulerException;

    void loadJob(String taskId) throws SchedulerConfigException;

    void unloadJob(String taskId) throws SchedulerException;

    void reload(String taskId) throws Exception;

}
