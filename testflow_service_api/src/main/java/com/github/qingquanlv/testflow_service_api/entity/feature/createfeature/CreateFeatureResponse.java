package com.github.qingquanlv.testflow_service_api.entity.feature.createfeature;

import com.github.qingquanlv.testflow_service_api.entity.Status;


/**
 * @Author Qingquan Lv
 * @Date 2021/1/1 16:15
 * @Version 1.0
 */
public class CreateFeatureResponse {

    //status
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}