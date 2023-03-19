package com.github.qingquanlv.testflow_service_api.entity.job.queryjob;

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
public class TaskDetails {

        private Long id;
        private Long jobId;
        private String jobName;
        private Long featureId;
        private String featureName;
        private Timestamp startTime;
        private Timestamp endTime;
        private String paramName;
        private String paramIndex;
        private Integer status;
}
