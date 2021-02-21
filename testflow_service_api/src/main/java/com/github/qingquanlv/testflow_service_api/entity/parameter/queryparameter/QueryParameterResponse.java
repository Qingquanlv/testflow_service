package com.github.qingquanlv.testflow_service_api.entity.parameter.queryparameter;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.parameter.Parameter;

import java.util.List;

/**
 * @Author Qingquan Lv
 * @Date 2021/2/3 7:01
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
    private List<Parameter> parameters;


    //status
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

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }
}

