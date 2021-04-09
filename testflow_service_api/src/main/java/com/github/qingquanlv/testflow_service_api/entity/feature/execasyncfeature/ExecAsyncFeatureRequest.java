package com.github.qingquanlv.testflow_service_api.entity.feature.execasyncfeature;

import java.util.List;

/**
 * @Author: qingquan.lv
 * @Date: 2021/4/7 11:23
 */
public class ExecAsyncFeatureRequest {
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
        private String parameter_names;


        public String getRequestId() {
                return requestId;
        }

        public void setRequestId(String requestId) {
                this.requestId = requestId;
        }

        public Long getFeatureId() {
                return featureId;
        }

        public void setFeatureId(Long featureId) {
                this.featureId = featureId;
        }

        public String getParameter_names() {
                return parameter_names;
        }

        public void setParameter_names(String parameter_names) {
                this.parameter_names = parameter_names;
        }
}

