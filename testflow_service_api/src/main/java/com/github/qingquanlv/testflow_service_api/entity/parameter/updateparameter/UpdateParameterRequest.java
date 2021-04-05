package com.github.qingquanlv.testflow_service_api.entity.parameter.updateparameter;

import com.github.qingquanlv.testflow_service_api.entity.parameter.Parameter;

import java.util.HashMap;
import java.util.List;

/**
 * @Author Qingquan Lv
 * @Date 2021/2/10 12:25
 * @Version 1.0
 */
public class UpdateParameterRequest {

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
    private List<HashMap<String, String>> parameters;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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
