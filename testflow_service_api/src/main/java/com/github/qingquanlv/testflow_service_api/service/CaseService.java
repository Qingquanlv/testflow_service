package com.github.qingquanlv.testflow_service_api.service;

import com.github.qingquanlv.testflow_service_api.entity.cases.database.createdatabase.CreateDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.createdatabase.CreateDataBaseCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.deletedatabase.DeleteDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.deletedatabase.DeleteDataBaseCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.querydatabase.QueryDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.querydatabase.QueryDataBaseCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.updatedatabase.UpdateDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.updatedatabase.UpdateDataBaseCaseResponse;
import org.springframework.stereotype.Service;

/**
 * @Author Qingquan Lv
 * @Date 2020/12/24 22:37
 * @Version 1.0
 */
public interface CaseService {

    /**
     * 删除dataBaseCase
     *
     * @param request
     * @return
     */
    DeleteDataBaseCaseResponse deleteCase(DeleteDataBaseCaseRequest request);

    /**
     * 查询dataBaseCase
     *
     * @param request
     * @return
     */
    QueryDataBaseCaseResponse queryCase(QueryDataBaseCaseRequest request);
    /**
     * 创建dataBaseCase
     *
     * @param request
     */
    CreateDataBaseCaseResponse createCase(CreateDataBaseCaseRequest request);

    /**
     * 更新dataBaseCase
     *
     * @param request
     * @return
     */
    UpdateDataBaseCaseResponse updateCase(UpdateDataBaseCaseRequest request);

}
