package com.github.qingquanlv.testflow_service_api.entity.job.updatejob;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/17 18:10
 */
@Data
@Builder
public class UpdateJobRequest {

        private String requestId;;
        private Long taskId;
        private String name;
        private Long featureId;
        private String paramName;
        private String paramIndexId;
        private String cron;
        private String class_name;
        private String description;
}
