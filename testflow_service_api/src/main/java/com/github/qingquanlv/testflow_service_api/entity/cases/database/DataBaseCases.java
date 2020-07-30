package com.github.qingquanlv.testflow_service_api.entity.cases.database;

public class DataBaseCases {

    //索引
    private Integer index;
    //name
    private String name;
    //请求Id
    private String requestId;
    //请求sql
    private String sql;


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

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
