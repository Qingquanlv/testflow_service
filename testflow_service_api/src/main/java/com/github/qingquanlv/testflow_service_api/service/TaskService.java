package com.github.qingquanlv.testflow_service_api.service;

import com.github.qingquanlv.testflow_service_api.entity.job.queryjob.QueryJobResponse;
import com.github.qingquanlv.testflow_service_api.entity.job.taskresult.GetTaskResultResponse;


/**
 * @Author: qingquan.lv
 * @Date: 2021/6/1 19:50
 */
public interface TaskService {


        QueryJobResponse getTaskByJobId(Long jobId);

        GetTaskResultResponse getTaskResult(Long taskId);
}
