package com.github.qingquanlv.testflow_service_api.entity.parameter.deleteparameter;

import com.github.qingquanlv.testflow_service_api.entity.Status;

/**
 * @Author Qingquan Lv
 * @Version 1.0
 */
public class DeleteParameterResponse {

    //status
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

