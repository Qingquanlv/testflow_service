package com.github.qingquanlv.testflow_service_api.entity.job.queryalljob;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/17 18:30
 */
@Data
@Builder
public class JobDetails {

        private Long id;;
        private String name;
        private Long featureId;
        private String paramName;
        private String paramIndexId;
        private String cron;
        private String class_name;
        private String description;
}
