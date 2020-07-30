package com.github.qingquanlv.testflow_service_api.entity.cases.verification;

import java.util.List;

public class VerificationCases {
    //索引
    private Integer index;
    //name
    private String name;
    //请求Id
    private String requestId;
    //verify type
    private String verifyType;
    //verify Parameter
    private List<String> parameters;


    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getVerifyType() {
        return verifyType;
    }

    public void setVerifyType(String verifyType) {
        this.verifyType = verifyType;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
