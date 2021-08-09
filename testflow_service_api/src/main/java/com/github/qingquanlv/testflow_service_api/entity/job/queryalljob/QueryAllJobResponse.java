package com.github.qingquanlv.testflow_service_api.entity.job.queryalljob;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/17 18:12
 */
@Data
@Builder
public class QueryAllJobResponse {
        /**
         * status
         */
        private Status status;

        private List<JobDetails> jobDetails;
}
