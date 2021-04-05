package com.github.qingquanlv.testflow_service_api.entity.cases.execcase;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.cases.CaseKey;
import com.github.qingquanlv.testflow_service_api.entity.parameter.Parameter;

import java.util.List;

/**
 * @Author Qingquan Lv
 * @Date 2021/2/3 7:47
 * @Version 1.0
 */
public class ExecCaseResponse {

    /**
     * caseKey
     */
    private CaseKey caseKey;
    /**
     * parameters
     */
    private List<Parameter> parameters;
    /**
     * result
     */
    private String result;
    /**
     * status
     */
    private Status status;


    public CaseKey getCaseKey() {
        return caseKey;
    }

    public void setCaseKey(CaseKey caseKey) {
        this.caseKey = caseKey;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
