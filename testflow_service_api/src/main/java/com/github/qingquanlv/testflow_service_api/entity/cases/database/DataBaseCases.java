package com.github.qingquanlv.testflow_service_api.entity.cases.database;

import com.github.qingquanlv.testflow_service_api.entity.cases.BaseCase;

/**
 * @Author Qingquan Lv
 * @Version 1.0
 */
public class DataBaseCases extends BaseCase {

    //请求sql
    private String sql;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
