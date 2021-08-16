package com.github.qingquanlv.testflow_service_api.service;

import com.github.qingquanlv.testflow_service_api.entity.job.queryjob.QueryJobRequest;
import com.github.qingquanlv.testflow_service_api.entity.job.queryjob.QueryJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.taskresult.GetTaskResultResponse;


/**
 * @Author: qingquan.lv
 * @Date: 2021/6/1 19:50
 */
public interface TaskService {


        QueryJobResponse getTask(QueryJobRequest request);

        GetTaskResultResponse getTaskResult(Long taskId);
}
