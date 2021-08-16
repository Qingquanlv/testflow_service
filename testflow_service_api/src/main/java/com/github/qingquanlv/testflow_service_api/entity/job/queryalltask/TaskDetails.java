package com.github.qingquanlv.testflow_service_api.entity.job.queryalltask;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author: qingquan.lv
 * @Date: 2021/8/14 下午3:06
 */
@Data
@Builder
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

