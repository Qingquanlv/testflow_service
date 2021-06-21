package com.github.qingquanlv.testflow_service_api.entity.feature_v2.deletefeature;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/13 23:57
 */
@Data
@Builder
public class DeleteFeatureResponse {

        //status
        private Status status;

        public Status getStatus() {
                return status;
        }

        public void setStatus(Status status) {
                this.status = status;
        }
}
