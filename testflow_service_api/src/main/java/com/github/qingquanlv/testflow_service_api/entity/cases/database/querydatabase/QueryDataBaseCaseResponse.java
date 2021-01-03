package com.github.qingquanlv.testflow_service_api.entity.cases.database.querydatabase;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.DataBaseCases;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author Qingquan Lv
 * @Date 2020/12/31 22:56
 * @Version 1.0
 */
public class QueryDataBaseCaseResponse {

    //status
    private Status status;

    //database case list
    private List<DataBaseCases> dataBaseCasesList;

    public List<DataBaseCases> getDataBaseCasesList() {
        return dataBaseCasesList;
    }

    public void setDataBaseCasesList(List<DataBaseCases> dataBaseCasesList) {
        this.dataBaseCasesList = dataBaseCasesList;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
