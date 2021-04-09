package com.github.qingquanlv.testflow_service_api.entity.feature.execfeature;

import com.github.qingquanlv.testflow_service_api.entity.parameter.Parameter;

import java.util.HashMap;
import java.util.List;

/**
 * @Author Qingquan Lv
 * @Date 2021/3/9 23:06
 * @Version 1.0
 */
public class ExecFeatureRequest {

    /**
     * 请求唯一标识
     */
    private String requestId;
    /**
     * featureId
     */
    private Long featureId;
    /**
     * parameters
     */
    private HashMap<String, String> parameters;


    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Long getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Long featureId) {
        this.featureId = featureId;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, String> parameters) {
        this.parameters = parameters;
    }
}
