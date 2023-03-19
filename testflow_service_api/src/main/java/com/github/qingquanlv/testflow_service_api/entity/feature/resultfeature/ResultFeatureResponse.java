package com.github.qingquanlv.testflow_service_api.entity.feature.resultfeature;

import com.github.qingquanlv.testflow_service_api.entity.Status;

import java.util.List;

/**
 * @Author Qingquan Lv
 * @Version 1.0
 */
public class ResultFeatureResponse {

    /**
     * status
     */
    private Status status;

    private List<ResultCase> resultCaseList;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<ResultCase> getResultCaseList() {
        return resultCaseList;
    }

    public void setResultCaseList(List<ResultCase> resultCaseList) {
        this.resultCaseList = resultCaseList;
    }
}
