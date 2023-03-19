package com.github.qingquanlv.testflow_service_api.controller;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.job.createjob.CreateJobRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.createjob.CreateJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.deletejob.DeleteJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.exexjob.ExecJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.queryalljob.QueryAllJobRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.queryalljob.QueryAllJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.setstatus.SetStatusRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.setstatus.SetStatusResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.updatejob.UpdateJobRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.updatejob.UpdateJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Task;
import com.github.qingquanlv.testflow_service_api.service.impl.ScheduleJobImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author Qingquan Lv
 * @Version 1.0
 */
@RestController
@RequestMapping("job")
public class Job {

    @Autowired
    private ScheduleJobImpl jobService;

    @PostMapping("/queryAll")
    public QueryAllJobResponse queryAllJob(@RequestBody QueryAllJobRequest request){
        return jobService.queryAllJob(request);
    }

    @RequestMapping("/create")
    public CreateJobResponse createJob(@RequestBody CreateJobRequest request){
        return jobService.createJob(request);
    }

    @RequestMapping("/delete")
    public DeleteJobResponse deleteJob(@RequestParam(name = "jobId") Long taskId){
        return jobService.deleteJob(taskId);
    }

    @RequestMapping("/update")
    public UpdateJobResponse updateJob(@RequestBody @Validated UpdateJobRequest request){
        return jobService.updateJob(request);
    }

    @RequestMapping("/updateStatus")
    public SetStatusResponse setStatus(@RequestBody SetStatusRequest request){
        return jobService.updateStatus(request);
    }

    @RequestMapping("/execute")
    public ExecJobResponse executeJob(@RequestParam(name = "jobId") Long taskId){
        ExecJobResponse rsp = new ExecJobResponse();
        Status status = new Status();
        status.setSuccess(true);
        rsp.setStatus(status);
        return rsp;
    }

    @RequestMapping("/batchExecute")
    public ExecJobResponse executeJobList(
            @RequestParam(name = "jobIds")
            @NotNull(message = "please select at least one job id")
                    List<Long> taskIds){
        ExecJobResponse rsp = new ExecJobResponse();
        Status status = new Status();
        status.setSuccess(true);
        rsp.setStatus(status);
        List<Task> tasks = jobService.pendingJob(taskIds);
        if (!CollectionUtils.isEmpty(tasks)) {
            for (Task task : tasks) {
                jobService.execJob(task);
            }
        }
        return rsp;
    }
}
