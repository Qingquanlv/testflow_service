package com.github.qingquanlv.testflow_service_api.entity.feature_v2.queryallfeaure;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/13 23:43
 */
@Data
@Builder
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
