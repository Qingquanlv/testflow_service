package com.github.qingquanlv.testflow_service_api.controller;

import com.github.qingquanlv.testflow_service_api.entity.task.gettaskresults.GetTaskResultResponse;
import com.github.qingquanlv.testflow_service_api.entity.task.gettaskstepresult.GetTaskStepResultResponse;
import com.github.qingquanlv.testflow_service_api.service.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/1 19:48
 */
@RestController
@RequestMapping("task")
public class Task {

        @Autowired
        private TaskServiceImpl taskService;

        @RequestMapping("/getResult/{id}")
        public GetTaskResultResponse taskResult(@PathVariable Long id){
                GetTaskResultResponse rsp =  taskService.getTaskResult(id);
                return rsp;
        }

        @GetMapping("/getStepResult")
        public GetTaskStepResultResponse taskStepResult(@RequestParam(name = "taskId") Long taskId, @RequestParam(name = "stepId") Long stepId){
                GetTaskStepResultResponse rsp =  taskService.getTaskStepResult(taskId, stepId);
                return rsp;
        }
}
