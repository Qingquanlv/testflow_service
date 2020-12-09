package com.github.qingquanlv.testflow_service_api.entity.cases.parse;

import com.github.qingquanlv.testflow_service_api.entity.cases.BaseCase;

import java.util.List;

/**
 * @Author Qingquan Lv
 * @Date 2020/12/9 7:50
 * @Version 1.0
 */
public class ParseCases extends BaseCase {

    //name
    private String name;
    //method source
    private String cvtMethodSource;
    //return value type
    private List<String> returnType;
    //parameters
    private List<Parameter> parameters;

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getCvtMethodSource() {
        return cvtMethodSource;
    }

    public void setCvtMethodSource(String cvtMethodSource) {
        this.cvtMethodSource = cvtMethodSource;
    }

    public List<String> getReturnType() {
        return returnType;
    }

    public void setReturnType(List<String> returnType) {
        this.returnType = returnType;
    }
}
