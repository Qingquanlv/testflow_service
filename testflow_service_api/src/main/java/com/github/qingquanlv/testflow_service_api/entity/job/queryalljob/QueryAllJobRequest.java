package com.github.qingquanlv.testflow_service_api.entity.job.queryalljob;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryAllJobRequest {

        private String requestId;
        private String filter;
        private Long pageNum;
        private Long pageSize;
}

