package com.github.qingquanlv.testflow_service.entity.cases.parse;

import java.util.List;

public class ParseCases {

    //索引
    private Integer index;
    //请求Id
    private String requestId;
    //type
    private String ParseType;
    //parameters
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

    public String getParseType() {
        return ParseType;
    }

    public void setParseType(String parseType) {
        ParseType = parseType;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }
}
