package com.github.qingquanlv.testflow_service_api.entity.execfeature;

import java.util.List;

/**
 * @author qingquanlv
 */
public class ExecFeatureRequest {

        private String execType;

        private List<String> execFeature;

        private List<String> getExecCase;

        public String getExecType() {
                return execType;
        }

        public void setExecType(String execType) {
                this.execType = execType;
        }

        public List<String> getExecFeature() {
                return execFeature;
        }

        public void setExecFeature(List<String> execFeature) {
                this.execFeature = execFeature;
        }

        public List<String> getGetExecCase() {
                return getExecCase;
        }

        public void setGetExecCase(List<String> getExecCase) {
                this.getExecCase = getExecCase;
        }
}
