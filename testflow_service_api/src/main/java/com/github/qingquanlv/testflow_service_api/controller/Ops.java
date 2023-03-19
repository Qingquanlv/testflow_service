package com.github.qingquanlv.testflow_service_api.controller;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.ops.createtestplan.CreateTestPlanRequest;
import com.github.qingquanlv.testflow_service_api.entity.ops.createtestplan.CreateTestPlanResponse;
import com.github.qingquanlv.testflow_service_api.entity.ops.deletetestplan.DeleteTestPlanResponse;
import com.github.qingquanlv.testflow_service_api.entity.ops.exectestplan.ExecTestPlanResponse;
import com.github.qingquanlv.testflow_service_api.entity.ops.queryalltestplan.QueryAllTestPlanRequest;
import com.github.qingquanlv.testflow_service_api.entity.ops.queryalltestplan.QueryAllTestPlanResponse;
import com.github.qingquanlv.testflow_service_api.entity.ops.querytestplan.QueryTestPlanResponse;
import com.github.qingquanlv.testflow_service_api.service.impl.OpsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: qingquan.lv
 */
@RestController
@RequestMapping("ops")
public class Ops {

    @Autowired
    private OpsImpl opsService;

    @RequestMapping("/create")
    public CreateTestPlanResponse createTestPlan(@RequestBody CreateTestPlanRequest request) {
        CreateTestPlanResponse rsp =  opsService.createTestPlan(request);
        return rsp;
    }

    @RequestMapping("/execute/{id}")
    public ExecTestPlanResponse execTestPlan(@PathVariable Long id) {
        Status status = new Status();
        status.setSuccess(true);
                opsService.execTestPlan(id);
        ExecTestPlanResponse rsp
                = ExecTestPlanResponse.builder()
                .status(status).build();
        return rsp;
    }

    @RequestMapping("/delete/{id}")
    public DeleteTestPlanResponse deleteTestPlan(@PathVariable Long id) {
        return opsService.deleteTestPlan(id);
    }

    @RequestMapping("/get/{id}")
    public QueryTestPlanResponse getTestPlan(@PathVariable Long id) {
        return opsService.getTestPlan(id);
    }

    @PostMapping("/queryAll")
    public QueryAllTestPlanResponse queryTestPlanAll(@RequestBody QueryAllTestPlanRequest request){
        return opsService.queryTestPlanAll(request);
    }
}
