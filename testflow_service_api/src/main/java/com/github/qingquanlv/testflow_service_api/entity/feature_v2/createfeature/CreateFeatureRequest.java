package com.github.qingquanlv.testflow_service_api.entity.feature_v2.createfeature;

import com.github.qingquanlv.testflow_service_api.entity.feature_v2.Config;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.Edges;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.Nodes;
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
public class CreateFeatureRequest {

        private String requestId;
        private Long featureId;
        private String featureName;
        private String description;
        private List<Nodes> nodes;
        private List<Edges> edges;
        private List<Config> config;
}
