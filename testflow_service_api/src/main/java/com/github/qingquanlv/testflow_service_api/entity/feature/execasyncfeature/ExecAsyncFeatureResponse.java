package com.github.qingquanlv.testflow_service_api.entity.feature.execasyncfeature;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.parameter.Parameter;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: qingquan.lv
 * @Date: 2021/4/7 11:23
 */
public class ExecAsyncFeatureResponse {
        /**
         * status
         */
        private Status status;
        /**
         * info
         */
        private String info;
        /**
         * assertion
         */
        private String assertion;
        /**
         * parameters
         */
        private List<HashMap<String, String>> parameters;

        public Status getStatus() {
                return status;
        }

        public void setStatus(Status status) {
                this.status = status;
        }

        public String getInfo() {
                return info;
        }

        public void setInfo(String info) {
                this.info = info;
        }

        public String getAssertion() {
                return assertion;
        }

        public void setAssertion(String assertion) {
                this.assertion = assertion;
        }

        public List<HashMap<String, String>> getParameters() {
                return parameters;
        }

        public void setParameters(List<HashMap<String, String>> parameters) {
                this.parameters = parameters;
        }
}

