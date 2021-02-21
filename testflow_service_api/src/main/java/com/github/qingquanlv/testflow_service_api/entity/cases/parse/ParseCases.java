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
    //parameters
    private List<String> parameters;

    public String getCvtMethodSource() {
        return cvtMethodSource;
    }

    public void setCvtMethodSource(String cvtMethodSource) {
        this.cvtMethodSource = cvtMethodSource;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }
}


