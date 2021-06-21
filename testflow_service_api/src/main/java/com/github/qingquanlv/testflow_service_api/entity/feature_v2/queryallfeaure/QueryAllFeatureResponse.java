package com.github.qingquanlv.testflow_service_api.entity.feature_v2.queryallfeaure;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/13 23:43
 */
@Data
@Builder
public class QueryAllFeatureResponse {

        /**
         * status
         */
        private Status status;
        /**
         * parameter_name
         */
        private List<QueryFeature> features;
}
