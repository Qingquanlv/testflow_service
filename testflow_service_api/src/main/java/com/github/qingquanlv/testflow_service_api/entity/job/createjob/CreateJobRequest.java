package com.github.qingquanlv.testflow_service_api.entity.job.createjob;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateJobRequest {

        private String requestId;;
        private String name;
        private Long featureId;
        private String paramName;
        private String paramIndex;
        private String cron;
        private String description;
}
