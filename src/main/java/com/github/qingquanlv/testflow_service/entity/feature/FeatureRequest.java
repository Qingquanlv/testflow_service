package com.github.qingquanlv.testflow_service.entity.feature;

import com.github.qingquanlv.testflow_service.entity.cases.Cases;
import com.github.qingquanlv.testflow_service.entity.cases.request.RequestCases;

import java.util.List;

public class FeatureRequest {

    //请求唯一标识
    private String requestId;

    //feature parameter
    private List<String> parameters;

    //feature cases
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
}
