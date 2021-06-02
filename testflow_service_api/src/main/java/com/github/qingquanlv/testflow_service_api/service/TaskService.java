package com.github.qingquanlv.testflow_service_api.service;

import com.github.qingquanlv.testflow_service_api.entity.task.gettaskresults.GetTaskResultResponse;
import com.github.qingquanlv.testflow_service_api.entity.task.gettaskstepresult.GetTaskStepResultResponse;


/**
 * @Author: qingquan.lv
 * @Date: 2021/6/1 19:50
 */
public interface TaskService {

        /**
         * 获取task执行结果
         *
         * @param taskId
         * @return
         */
        GetTaskResultResponse getTaskResult(Long taskId);

        /**
         * 获取task step结果
         *
         * @param taskId
         * @param index
         * @return
         */
        GetTaskStepResultResponse getTaskStepResult(Long taskId, Long index);
}
