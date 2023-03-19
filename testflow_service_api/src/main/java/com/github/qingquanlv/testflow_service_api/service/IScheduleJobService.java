package com.github.qingquanlv.testflow_service_api.service;

import com.github.qingquanlv.testflow_service_api.entity.job.createjob.CreateJobRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.createjob.CreateJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.deletejob.DeleteJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.queryalljob.QueryAllJobRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.queryalljob.QueryAllJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.setstatus.SetStatusRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.setstatus.SetStatusResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.updatejob.UpdateJobRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.updatejob.UpdateJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Task;
import org.quartz.SchedulerConfigException;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * @Author Qingquan Lv
 * @Version 1.0
 */
public interface IScheduleJobService {

    List<Task> pendingJob(List<Long> jobIds);

    void execJob(Task taskId);

    CreateJobResponse createJob(CreateJobRequest request);

    DeleteJobResponse deleteJob(Long taskId);

    UpdateJobResponse updateJob(UpdateJobRequest request);

    QueryAllJobResponse queryAllJob(QueryAllJobRequest request);

    SetStatusResponse updateStatus(SetStatusRequest request);

    void startJob();

    void stopJob(String taskId) throws SchedulerException;

    void resumeJob(String taskId) throws SchedulerException;

    void loadJob(String taskId) throws SchedulerConfigException;

    void unloadJob(String taskId) throws SchedulerException;

    void reload(String taskId) throws Exception;

}
