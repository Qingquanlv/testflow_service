package com.github.qingquanlv.testflow_service.entity.cases.database;

public class DataBaseCases {

    //索引
    private Integer index;
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
}
