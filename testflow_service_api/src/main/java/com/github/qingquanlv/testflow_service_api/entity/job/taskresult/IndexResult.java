package com.github.qingquanlv.testflow_service_api.entity.job.taskresult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: qingquan.lv
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexResult {

        private String paramName;

        private Long paramIndex;

        private String paramValue;

        private Integer status;

        private Timestamp startTime;

        private Timestamp endTime;

        private List<StepResult> stepResults;
}
