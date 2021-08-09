package com.github.qingquanlv.testflow_service_api.entity.job.createjob;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/17 15:18
 */
@Data
@Builder
public class CreateJobRequest {

        private String requestId;;
        private String name;
        private Long featureId;
        private String paramName;
        private String paramIndex;
        private String cron;
        private String description;
}
