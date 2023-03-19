package com.github.qingquanlv.testflow_service_api.entity.opstask.getopstaskresult;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
public class GetOpsTaskResultResponse {

        /**
         * status
         */
        private Status status;

        private List<OpsPlanTaskResultDetails> opsPlanTaskResultDetails;
        /**
         * total
         */
        private long total;
}
