package com.github.qingquanlv.testflow_service_api.entity.feature_v2.execfeature;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/22 20:14
 */
@Data
@Builder
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
