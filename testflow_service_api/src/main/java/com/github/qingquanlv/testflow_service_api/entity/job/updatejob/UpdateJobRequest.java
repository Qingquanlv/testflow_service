package com.github.qingquanlv.testflow_service_api.entity.job.updatejob;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateJobRequest {

        private String requestId;
        @NotNull(message = "jobId不能为null")
        private Long jobId;
        private String name;
        private Long featureId;
        private String paramName;
        private String paramIndex;
        private String cron;
        private String description;
}
