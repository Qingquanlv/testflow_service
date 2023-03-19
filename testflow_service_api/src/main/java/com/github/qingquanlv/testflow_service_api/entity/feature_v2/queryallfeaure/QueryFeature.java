package com.github.qingquanlv.testflow_service_api.entity.feature_v2.queryallfeaure;

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
public class QueryFeature {

        /**
         * feature Name
         */
        private Long feature_id;
        /**
         * feature Name
         */
        private String featureName;
        /**
         * description
         */
        private String description;
}
