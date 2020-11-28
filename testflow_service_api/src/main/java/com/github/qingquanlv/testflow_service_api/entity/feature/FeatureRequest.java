package com.github.qingquanlv.testflow_service_api.entity.feature;

import com.github.qingquanlv.testflow_service_api.entity.cases.Cases;

import java.util.List;

public class FeatureRequest {

    /**
     * 请求唯一标识
     */
    private String requestId;

    /**
     * feature Name主键
     */
    private String featureName;

    /**
     * feature Name 介绍
     */
    private String description;

    /**
     * feature 相关操作
     */
    private String opertion;

    /**
     * feature parameters 参数
     */
    private List<String> parameters;

    /**
     * feature cases
     */
    private Cases cases;


    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }


    public Cases getCases() {
        return cases;
    }

    public void setCases(Cases cases) {
        this.cases = cases;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOpertion() {
        return opertion;
    }

    public void setOpertion(String opertion) {
        this.opertion = opertion;
    }
}
