package com.github.qingquanlv.testflow_service_api.service;

import com.github.qingquanlv.testflow_service_api.entity.cases.database.createdatabase.CreateDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.createdatabase.CreateDataBaseCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.deletedatabase.DeleteDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.deletedatabase.DeleteDataBaseCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.querydatabase.QueryDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.querydatabase.QueryDataBaseCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.updatedatabase.UpdateDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.updatedatabase.UpdateDataBaseCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.cases.execcase.ExecCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.execcase.ExecCaseResponse;
import org.springframework.stereotype.Service;

/**
 * @Author Qingquan Lv
 * @Date 2020/12/24 22:37
 * @Version 1.0
 */
public interface CaseService {

    /**
     * 执行Case
     *
     * @param id
     * @return
     */
    ExecCaseResponse execCase(ExecCaseRequest id);
}
