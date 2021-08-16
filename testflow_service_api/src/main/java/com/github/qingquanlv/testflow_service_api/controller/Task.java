package com.github.qingquanlv.testflow_service_api.controller;

import com.github.qingquanlv.testflow_service_api.entity.job.queryjob.QueryJobRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.queryjob.QueryJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.taskresult.GetTaskResultResponse;
import com.github.qingquanlv.testflow_service_api.service.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: qingquan.lv
 * @Date: 2021/8/7 下午8:54
 */
@RestController
@RequestMapping("task")
public class Task {

        @Autowired
        private TaskServiceImpl taskService;

        @RequestMapping("/getTask")
        public QueryJobResponse getTaskById(@RequestBody QueryJobRequest request){
                QueryJobResponse rsp =  taskService.getTask(request);
                return rsp;
        }

        @GetMapping("/getResult")
        public GetTaskResultResponse taskResult(@RequestParam(name = "taskId") Long taskId){
                GetTaskResultResponse rsp =  taskService.getTaskResult(taskId);
                return rsp;
        }

}
