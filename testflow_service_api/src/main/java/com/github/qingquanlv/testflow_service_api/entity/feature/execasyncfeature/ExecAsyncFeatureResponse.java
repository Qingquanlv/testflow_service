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

        private List<HashMap<String, String>> parameters;

        public Status getStatus() {
                return status;
        }

        public void setStatus(Status status) {
                this.status = status;
        }
}

