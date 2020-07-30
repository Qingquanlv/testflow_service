package com.github.qingquanlv.testflow_service_api.entity.cases.parse;

import java.util.List;

public class ParseCases {

    //索引
    private Integer index;
    //name
    private String name;
    //method name
    private String cvtMethodName;
    //method source
    private String cvtMethodSource;
    //return parameter type
    private String returnType;
    //parameters
    private List<String> parameters;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getReturnType() { return returnType; }

    public void setReturnType(String returnType) { this.returnType = returnType; }

    public String getCvtMethodName() {
        return cvtMethodName;
    }

    public void setCvtMethodName(String cvtMethodName) {
        this.cvtMethodName = cvtMethodName;
    }

    public String getCvtMethodSource() {
        return cvtMethodSource;
    }

    public void setCvtMethodSource(String cvtMethodSource) {
        this.cvtMethodSource = cvtMethodSource;
    }
}
