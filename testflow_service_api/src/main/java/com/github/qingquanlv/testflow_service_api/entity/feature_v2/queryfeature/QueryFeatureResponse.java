package com.github.qingquanlv.testflow_service_api.entity.feature_v2.queryfeature;

import com.github.qingquanlv.testflow_service_api.entity.feature_v2.Config;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.Edges;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.Nodes;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/13 22:50
 */
@Data
@Builder
public class QueryFeatureResponse {

        private Long featureId;
        private String featureName;
        private String description;
        private List<Nodes> nodes;
        private List<Edges> edges;
        private List<Config> config;
}
