package com.github.qingquanlv.testflow_service_api.entity.parameter.updateparameter;

import com.github.qingquanlv.testflow_service_api.entity.Status;

/**
 * @Author Qingquan Lv
 * @Date 2021/2/3 7:07
 * @Version 1.0
 */
public class UpdateParameterResponse {

    //status
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}


