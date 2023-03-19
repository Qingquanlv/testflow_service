package com.github.qingquanlv.testflow_service_api.entity.feature_v2.queryallfeaure;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryAllFeatureResponse {

        /**
         * status
         */
        private Status status;
        /**
         * parameter_name
         */
        private List<QueryFeature> features;
        /**
         * total
         */
        private long total;

}
