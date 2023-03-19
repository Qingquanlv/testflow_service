package com.github.qingquanlv.testflow_service_api.entity.opstask.getopstaskresult;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
public class GetOpsTaskResultRequest {

        private Integer opsTaskId;
        private String requestId;
}
