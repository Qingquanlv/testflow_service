package com.github.qingquanlv.testflow_service_api.controller;

import com.github.qingquanlv.testflow_service_api.entity.job.queryjob.QueryJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.taskresult.GetTaskResultResponse;
import com.github.qingquanlv.testflow_service_api.service.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: qingquan.lv
 * @Date: 2021/8/7 下午8:54
 */
public class Task {

        @Autowired
        private TaskServiceImpl taskService;

        @GetMapping("/getTaskByJobId/{id}")
        public QueryJobResponse getTaskById(@PathVariable("JobId") Long jobId){
                QueryJobResponse rsp =  taskService.getTaskByJobId(jobId);
                return rsp;
        }

        @RequestMapping("/getResult/{id}")
        public GetTaskResultResponse taskResult(@RequestParam(name = "taskId") Long taskId){
                GetTaskResultResponse rsp =  taskService.getTaskResult(taskId);
                return rsp;
        }

}
