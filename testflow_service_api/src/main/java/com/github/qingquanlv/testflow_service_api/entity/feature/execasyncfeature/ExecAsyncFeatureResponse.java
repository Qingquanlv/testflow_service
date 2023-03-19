package com.github.qingquanlv.testflow_service_api.entity.feature.execasyncfeature;

import com.github.qingquanlv.testflow_service_api.entity.Status;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: qingquan.lv
 */
public class ExecAsyncFeatureResponse {
        /**
         * status
         */
        private Status status;

        private List<HashMap<String, String>> parameters;

        public Status getStatus() {
                return status;
        }

        public void setStatus(Status status) {
                this.status = status;
        }
}

