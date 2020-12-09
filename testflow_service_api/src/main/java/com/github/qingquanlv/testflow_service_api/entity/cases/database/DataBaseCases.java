package com.github.qingquanlv.testflow_service_api.entity.cases.database;

import com.github.qingquanlv.testflow_service_api.entity.cases.BaseCase;

/**
 * @Author Qingquan Lv
 * @Date 2020/12/9 7:50
 * @Version 1.0
 */
public class DataBaseCases extends BaseCase {

    //name
    private String name;
    //请求sql
    private String sql;

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
