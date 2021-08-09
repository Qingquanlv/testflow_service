package com.github.qingquanlv.testflow_service_api.entity.job.queryalljob;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/17 18:30
 */
@Data
@Builder
public class JobDetails {

        private Long id;
        private String name;
        private Long featureId;
        private String featureName;
        private String paramName;
        private String paramIndex;
        private String cron;
        private Integer status;
        private String description;
        private Timestamp dataChangedLastTime;
}
