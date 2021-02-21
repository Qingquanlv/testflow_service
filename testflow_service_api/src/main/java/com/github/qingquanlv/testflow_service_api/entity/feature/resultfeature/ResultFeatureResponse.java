package com.github.qingquanlv.testflow_service_api.entity.feature.resultfeature;

import com.github.qingquanlv.testflow_service_api.entity.Status;

/**
 * @Author Qingquan Lv
 * @Date 2021/2/3 8:01
 * @Version 1.0
 */
public class ResultFeatureResponse {
    //返回执行结果

    //status
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
