package com.github.qingquanlv.testflow_service_api.entity.job.queryalltask;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.job.queryjob.TaskDetails;

import java.util.List;

/**
 * @Author: qingquan.lv
 */
public class QueryAllTaskResponse {

        //status
        private Status status;

        private List<TaskDetails> jobDetails;

}
