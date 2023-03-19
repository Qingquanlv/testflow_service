package com.github.qingquanlv.testflow_service_api.entity.opstask.getopstasklist;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
public class GetOpsTaskListRequest {

        private String requestId;
        private String filter;
        private Long pageNum;
        private Long pageSize;
}
