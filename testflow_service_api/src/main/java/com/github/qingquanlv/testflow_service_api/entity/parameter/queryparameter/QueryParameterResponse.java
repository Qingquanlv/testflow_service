package com.github.qingquanlv.testflow_service_api.entity.parameter.queryparameter;

import com.github.qingquanlv.testflow_service_api.entity.Status;

import java.util.HashMap;
import java.util.List;

/**
 * @Author Qingquan Lv
 * @Version 1.0
 */
public class QueryParameterResponse {

    /**
     * parameter_name
     */
    private String parameter_name;

    /**
     * parameters
     */
    private List<HashMap<String, String>> parameters;

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

    public String getParameter_name() {
        return parameter_name;
    }

    public void setParameter_name(String parameter_name) {
        this.parameter_name = parameter_name;
    }

    public List<HashMap<String, String>> getParameters() {
        return parameters;
    }

    public void setParameters(List<HashMap<String, String>> parameters) {
        this.parameters = parameters;
    }
}

