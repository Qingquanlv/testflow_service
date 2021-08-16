package com.github.qingquanlv.testflow_service_api.entity.job.queryjob;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author: qingquan.lv
 * @Date: 2021/8/7 下午6:49
 */
@Data
@Builder
public class QueryJobResponse {

        //status
        private Status status;

        private List<TaskDetails> jobDetails;
}
