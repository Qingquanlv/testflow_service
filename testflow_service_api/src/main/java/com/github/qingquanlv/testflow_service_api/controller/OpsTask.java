package com.github.qingquanlv.testflow_service_api.controller;

import com.github.qingquanlv.testflow_service_api.entity.opstask.getopstasklist.GetOpsTaskListRequest;
import com.github.qingquanlv.testflow_service_api.entity.opstask.getopstasklist.GetOpsTaskListResponse;
import com.github.qingquanlv.testflow_service_api.entity.opstask.getopstaskresult.GetOpsTaskResultRequest;
import com.github.qingquanlv.testflow_service_api.entity.opstask.getopstaskresult.GetOpsTaskResultResponse;
import com.github.qingquanlv.testflow_service_api.service.impl.OpsTaskImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: qingquan.lv
 */
@RestController
@RequestMapping("opstask")
public class OpsTask {

        @Autowired
        private OpsTaskImpl opsTaskService;

        @RequestMapping("/getTaskList")
        public GetOpsTaskListResponse getTaskById(@RequestBody GetOpsTaskListRequest request){
                GetOpsTaskListResponse rsp =  opsTaskService.getTask(request);
                return rsp;
        }

        @PostMapping("/getResult")
        public GetOpsTaskResultResponse taskStepResult(@RequestBody GetOpsTaskResultRequest request){
                GetOpsTaskResultResponse rsp =  opsTaskService.getTaskStepResult(request);
                return rsp;
        }
}
