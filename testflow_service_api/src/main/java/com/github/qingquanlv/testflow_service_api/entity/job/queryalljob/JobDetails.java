package com.github.qingquanlv.testflow_service_api.entity.job.queryalljob;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
