package com.github.qingquanlv.testflow_service_api.controller;

import com.github.qingquanlv.testflow_service_api.entity.job.queryjob.QueryJobRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.queryjob.QueryJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.taskresult.GetTaskResultRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.taskresult.GetTaskResultResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.taskstepresult.GetTaskStepResultRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.taskstepresult.GetTaskStepResultResponse;
import com.github.qingquanlv.testflow_service_api.service.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: qingquan.lv
 */
@RestController
@RequestMapping("task")
public class Task {

        @Autowired
        private TaskServiceImpl taskService;

        @RequestMapping("/getTask")
        public QueryJobResponse getTaskById(@RequestBody QueryJobRequest request){
                return taskService.getTask(request);
        }

        @PostMapping("/getResult")
        public GetTaskResultResponse taskResult(@RequestBody GetTaskResultRequest request){
                return taskService.getTaskResult(request);
        }

        @PostMapping("/getStepResult")
        public GetTaskStepResultResponse taskStepResult(@RequestBody GetTaskStepResultRequest request){
                return taskService.getTaskStepResult(request);
        }

}
