package com.github.qingquanlv.testflow_service_api.entity.parameter.queryallparameter;

import com.github.qingquanlv.testflow_service_api.entity.parameter.Parameter;

import java.util.List;

/**
 * @Author Qingquan Lv
 * @Date 2021/3/7 10:50
 * @Version 1.0
 */
public class QueryParameter {

    /**
     * parameter_name
     */
    private String parameter_name;

    /**
     * parameters
     */
    private List<Parameter> parameters;


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
