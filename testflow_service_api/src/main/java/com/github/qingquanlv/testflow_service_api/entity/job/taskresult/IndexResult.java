package com.github.qingquanlv.testflow_service_api.entity.job.taskresult;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: qingquan.lv
 * @Date: 2021/8/7 下午9:58
 */
@Data
public class IndexResult {

        private String paramName;

        private Long paramIndex;

        private String paramValue;

        private Integer status;

        private Timestamp startTime;

        private Timestamp endTime;

        private List<StepResult> stepResults;
}
