package com.github.qingquanlv.testflow_service_api.entity.feature_v2.execfeature;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecFeatureRequest {

        /**
         * 请求唯一标识
         */
        private String requestId;
        /**
         * featureId
         */
        private Long featureId;
        /**
         * parameters
         */
        private HashMap<String, String> parameters;
}
