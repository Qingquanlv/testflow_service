package com.github.qingquanlv.testflow_service_api.entity.parameter.createparameter;

import com.github.qingquanlv.testflow_service_api.entity.parameter.Parameter;

import java.util.List;

/**
 * @Author Qingquan Lv
 * @Date 2021/2/3 6:53
 * @Version 1.0
 */
public class CreateParameterRequest {

    /**
     * 请求唯一标识
     */
    private String requestId;

    /**
     * parameter_name
     */
    private String parameter_name;

    /**
     * parameters
     */
    private List<Parameter> parameters;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public String getParameter_name() {
        return parameter_name;
    }

    public void setParameter_name(String parameter_name) {
        this.parameter_name = parameter_name;
    }
}
