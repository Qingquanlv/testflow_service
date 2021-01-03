package com.github.qingquanlv.testflow_service_api.entity.cases.database;

import com.github.qingquanlv.testflow_service_api.entity.cases.BaseCase;

import java.sql.Timestamp;

/**
 * @Author Qingquan Lv
 * @Date 2020/12/9 7:50
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
