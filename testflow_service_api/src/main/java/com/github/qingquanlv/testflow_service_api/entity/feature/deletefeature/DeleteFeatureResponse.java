package com.github.qingquanlv.testflow_service_api.entity.feature.deletefeature;

import com.github.qingquanlv.testflow_service_api.entity.Status;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/30 7:29
 * @Version 1.0
 */
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
