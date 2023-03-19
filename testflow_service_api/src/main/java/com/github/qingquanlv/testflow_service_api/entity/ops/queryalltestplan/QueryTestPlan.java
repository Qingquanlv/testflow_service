package com.github.qingquanlv.testflow_service_api.entity.ops.queryalltestplan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: qingquan.lv
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryTestPlan {

        /**
         * feature Name
         */
        private Long testPlanId;
        /**
         * feature Name
         */
        private String testPlanName;
        /**
         * description
         */
        private String description;
}

