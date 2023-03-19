package com.github.qingquanlv.testflow_service_api.service;

import com.github.qingquanlv.testflow_service_api.entity.job.queryjob.QueryJobRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.queryjob.QueryJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.taskresult.GetTaskResultRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.taskresult.GetTaskResultResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.taskstepresult.GetTaskStepResultRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.taskstepresult.GetTaskStepResultResponse;


/**
 * @Author: qingquan.lv
 */
public interface TaskService {


        QueryJobResponse getTask(QueryJobRequest request);

        GetTaskResultResponse getTaskResult(GetTaskResultRequest taskId);

        GetTaskStepResultResponse getTaskStepResult(GetTaskStepResultRequest request);
}
