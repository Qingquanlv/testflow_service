package com.github.qingquanlv.testflow_service_api.controller;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.job.createjob.CreateJobRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.createjob.CreateJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.deletejob.DeleteJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.exexjob.ExecJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.queryalljob.QueryAllJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.setstatus.SetStatusRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.setstatus.SetStatusResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.updatejob.UpdateJobRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.updatejob.UpdateJobResponse;
import com.github.qingquanlv.testflow_service_api.service.impl.ScheduleJobImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Qingquan Lv
 * @Date 2021/6/6 10:40
 * @Version 1.0
 */
@RestController
@RequestMapping("job")
public class Job {

    @Autowired
    private ScheduleJobImpl jobService;

    @RequestMapping("/create")
    public CreateJobResponse createJob(@RequestBody CreateJobRequest request){
        CreateJobResponse rsp =  jobService.createJob(request);
        return rsp;
    }

    @RequestMapping("/delete")
    public DeleteJobResponse deleteJob(@RequestParam(name = "jobId") Long taskId){
        DeleteJobResponse rsp =  jobService.deleteJob(taskId);
        return rsp;
    }

    @RequestMapping("/update")
    public UpdateJobResponse updateJob(@RequestBody UpdateJobRequest request){
        UpdateJobResponse rsp =  jobService.updateJob(request);
        return rsp;
    }

    @RequestMapping("/queryAll")
    public QueryAllJobResponse queryAllJob(){
        QueryAllJobResponse rsp =  jobService.queryAllJob();
        return rsp;
    }

    @RequestMapping("/updateStatus")
    public SetStatusResponse setStatus(@RequestBody SetStatusRequest request){
        SetStatusResponse rsp = jobService.updateStatus(request);
        return rsp;
    }

    @RequestMapping("/execute")
    public ExecJobResponse executeJob(@RequestParam(name = "jobId") Long taskId){
        ExecJobResponse rsp = new ExecJobResponse();
        Status status = new Status();
        status.setSuccess(true);
        rsp.setStatus(status);
        jobService.execJob(taskId);
        return rsp;
    }
}
