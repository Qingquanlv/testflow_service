package com.github.qingquanlv.testflow_service_api.service;

import com.github.qingquanlv.testflow_service_api.entity.ops.createtestplan.CreateTestPlanRequest;
import com.github.qingquanlv.testflow_service_api.entity.ops.createtestplan.CreateTestPlanResponse;
import com.github.qingquanlv.testflow_service_api.entity.ops.deletetestplan.DeleteTestPlanResponse;
import com.github.qingquanlv.testflow_service_api.entity.ops.queryalltestplan.QueryAllTestPlanRequest;
import com.github.qingquanlv.testflow_service_api.entity.ops.queryalltestplan.QueryAllTestPlanResponse;
import com.github.qingquanlv.testflow_service_api.entity.ops.querytestplan.QueryTestPlanResponse;

/**
 * @Author: qingquan.lv
 */
public interface IOpsService {

    CreateTestPlanResponse createTestPlan(CreateTestPlanRequest taskId);

    void execTestPlan(Long taskId);

    DeleteTestPlanResponse deleteTestPlan(Long taskId);

    QueryTestPlanResponse getTestPlan(Long planId);

    QueryAllTestPlanResponse queryTestPlanAll(QueryAllTestPlanRequest request);
}
