package com.github.qingquanlv.testflow_service_api.entity.job.taskresult;

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
public class GetTaskResultRequest {

        private Integer taskId;
        private String requestId;
        private String filter;
        private Integer status;
        private Timestamp startTime;
        private Timestamp endTime;
        private Integer pageNum;
        private Integer pageSize;
}
