package com.github.qingquanlv.testflow_service_api.entity.parameter.queryallparameter;

import com.github.qingquanlv.testflow_service_api.entity.Status;

import java.util.List;

/**
 * @Author Qingquan Lv
 * @Date 2021/3/7 10:48
 * @Version 1.0
 */
public class QueryAllParameterResponse {

    /**
     * status
     */
    private Status status;
    /**
     * parameter_name
     */
    private List<QueryParameter> parameters;

    public List<QueryParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<QueryParameter> parameters) {
        this.parameters = parameters;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
