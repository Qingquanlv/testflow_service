package com.github.qingquanlv.testflow_service_api.entity.cases.parse;

import com.github.qingquanlv.testflow_service_api.entity.cases.BaseCase;
import java.util.List;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/2 15:21
 * @Version 1.0
 */
public class ParseCases extends BaseCase {

    //method source
    private String cvtMethodSource;
    //return value type
    private String returnType;
    //parameters
    private List<Parameter> parameters;

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public String getCvtMethodSource() {
        return cvtMethodSource;
    }

    public void setCvtMethodSource(String cvtMethodSource) {
        this.cvtMethodSource = cvtMethodSource;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
}


