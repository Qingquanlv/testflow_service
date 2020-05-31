package com.github.qingquanlv.testflow_service.entity.cases.verification;

import java.util.List;

public class VerificationCases {
    //索引
    private Integer index;
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
}
