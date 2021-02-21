package com.github.qingquanlv.testflow_service_api.entity.feature.execfeature;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.feature.CaseResult;
import com.github.qingquanlv.testflow_service_api.entity.parameter.Parameter;

import java.util.List;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/5 8:41
 * @Version 1.0
 */
public class ExecFeatureResponse {
    //status
    private Status status;
    //result
    private List<CaseResult> caseResults;
    //parameters
    private List<Parameter> parameters;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<CaseResult> getCaseResults() {
        return caseResults;
    }

    public void setCaseResults(List<CaseResult> caseResults) {
        this.caseResults = caseResults;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }
}
