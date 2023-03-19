package com.github.qingquanlv.testflow_service_api.service;

import com.github.qingquanlv.testflow_service_api.entity.opstask.getopstasklist.GetOpsTaskListRequest;
import com.github.qingquanlv.testflow_service_api.entity.opstask.getopstasklist.GetOpsTaskListResponse;
import com.github.qingquanlv.testflow_service_api.entity.opstask.getopstaskresult.GetOpsTaskResultRequest;
import com.github.qingquanlv.testflow_service_api.entity.opstask.getopstaskresult.GetOpsTaskResultResponse;

/**
 * @Author: qingquan.lv
 */
public interface OpsTaskService {

        GetOpsTaskListResponse getTask(GetOpsTaskListRequest request);

        GetOpsTaskResultResponse getTaskStepResult(GetOpsTaskResultRequest request);
}
