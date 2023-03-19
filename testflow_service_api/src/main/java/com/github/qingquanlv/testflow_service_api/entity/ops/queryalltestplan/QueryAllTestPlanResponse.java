package com.github.qingquanlv.testflow_service_api.entity.ops.queryalltestplan;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: qingquan.lv
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryAllTestPlanResponse {
        /**
         * status
         */
        private Status status;
        /**
         * parameter_name
         */
        private List<QueryTestPlan> features;
        /**
         * total
         */
        private long total;

}