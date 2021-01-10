package com.github.qingquanlv.testflow_service_api.entity.feature.execfeature;

import com.github.qingquanlv.testflow_service_api.entity.Status;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/5 8:41
 * @Version 1.0
 */
public class ExecFeatureResponse {
    //status
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
