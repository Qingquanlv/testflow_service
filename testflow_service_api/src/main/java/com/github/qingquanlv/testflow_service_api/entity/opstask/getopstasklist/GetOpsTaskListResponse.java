package com.github.qingquanlv.testflow_service_api.entity.opstask.getopstasklist;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
public class GetOpsTaskListResponse {

        /**
         * status
         */
        private Status status;

        private List<OpsTaskDetails> opsTaskDetails;
        /**
         * total
         */
        private long total;
}

