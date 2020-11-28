package com.github.qingquanlv.testflow_service_api.entity.savefeature;

import com.github.qingquanlv.testflow_service_api.entity.cases.Cases;

import java.util.List;

public class SaveFeatureRequest {

        /**
         * 请求唯一标识
         */
        private String requestId;

        /**
         * feature Name主键
         */
        private String featureName;

        /**
         *  feature 相关操作
         */
        private String operation;

        /**
         * feature parameters 参数
         */
        private List<String> parameters;

        /**
         * feature casesfeature parameters 参数
         */
        private Cases cases;


        public String getRequestId() {
                return requestId;
        }

        public void setRequestId(String requestId) {
                this.requestId = requestId;
        }

        public List<String> getParameters() {
                return parameters;
        }

        public void setParameters(List<String> parameters) {
                this.parameters = parameters;
        }


        public Cases getCases() {
                return cases;
        }

        public void setCases(Cases cases) {
                this.cases = cases;
        }

        public String getFeatureName() {
                return featureName;
        }

        public void setFeatureName(String featureName) {
                this.featureName = featureName;
        }

        public String getOperation() {
                return operation;
        }

        public void setOperation(String operation) {
                this.operation = operation;
        }
}
