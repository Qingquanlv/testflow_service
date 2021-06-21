package com.github.qingquanlv.testflow_service_api.controller;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.job.createjob.CreateJobRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.createjob.CreateJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.deletejob.DeleteJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.exexjob.ExecJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.jobresult.GetResultResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.queryalljob.QueryAllJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.updatejob.UpdateJobRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.updatejob.UpdateJobResponse;
import com.github.qingquanlv.testflow_service_api.service.impl.ScheduleJobImpl;
import org.quartz.SchedulerConfigException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/execute")
    public ExecJobResponse executeJob(@RequestParam(name = "taskId") Long taskId){
        ExecJobResponse rsp = new ExecJobResponse();
        Status status = new Status();
        status.setSuccess(true);
        rsp.setStatus(status);
        jobService.execJob(taskId);
        return rsp;
    }

    @RequestMapping("/create")
    public CreateJobResponse createJob(@RequestBody CreateJobRequest request){
        CreateJobResponse rsp =  jobService.createJob(request);
        return rsp;
    }

    @RequestMapping("/delete")
    public DeleteJobResponse deleteJob(@RequestParam(name = "taskId") Long taskId){
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

    @RequestMapping("/getResult")
    public GetResultResponse taskResult(@RequestParam(name = "taskId") Long taskId){
        GetResultResponse rsp =  jobService.getResult(taskId);
        return rsp;
    }


    @GetMapping("/load/{taskId}")
    public String loadJob(@PathVariable("taskId") String taskId){
        try {
            jobService.loadJob(taskId);
        } catch (SchedulerConfigException e) {
            return "导入定时任务失败";
        }
        return "成功";
    }

    @GetMapping("/resume/{taskId}")
    public String resumeJob(@PathVariable("taskId") String taskId){
        try {
            jobService.resumeJob(taskId);
        }catch (SchedulerException e) {
            return "恢复定时任务失败";
        }
        return "成功";
    }

    @GetMapping("/stop/{taskId}")
    public String stopJob(@PathVariable("taskId") String taskId){
        try {
            jobService.stopJob(taskId);
        }catch (SchedulerException e) {
            return "暂停定时任务失败";
        }
        return "成功";
    }

    @GetMapping("/unload/{taskId}")
    public String unloadJob(@PathVariable("taskId") String taskId){
        try {
            jobService.unloadJob(taskId);
        }catch (SchedulerException e) {
            return "卸载定时任务失败";
        }
        return "成功";
    }
}
