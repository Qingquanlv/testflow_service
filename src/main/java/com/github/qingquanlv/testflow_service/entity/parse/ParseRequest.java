package com.github.qingquanlv.testflow_service.entity.parse;

import java.util.List;

public class ParseRequest {

    private String key;

    private String requestType;

    private List<Parameter> parameters;

    private String method;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}
