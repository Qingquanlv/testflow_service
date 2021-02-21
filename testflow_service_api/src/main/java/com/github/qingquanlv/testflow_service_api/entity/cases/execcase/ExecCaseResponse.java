package com.github.qingquanlv.testflow_service_api.entity.cases.execcase;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.parameter.Parameter;

import java.util.List;

/**
 * @Author Qingquan Lv
 * @Date 2021/2/3 7:47
 * @Version 1.0
 */
public class ExecCaseResponse {
    /**
     * case type
     */
    private String case_type;
    /**
     * case id
     */
    private Long case_id;

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCase_type() {
        return case_type;
    }

    public void setCase_type(String case_type) {
        this.case_type = case_type;
    }

    public Long getCase_id() {
        return case_id;
    }

    public void setCase_id(Long case_id) {
        this.case_id = case_id;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
