package com.github.qingquanlv.testflow_service_api.service.impl;

import com.github.qingquanlv.testflow_service_api.entity.cases.database.createdatabase.CreateDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.createdatabase.CreateDataBaseCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.deletedatabase.DeleteDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.deletedatabase.DeleteDataBaseCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.querydatabase.QueryDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.querydatabase.QueryDataBaseCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.updatedatabase.UpdateDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.updatedatabase.UpdateDataBaseCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.DatabaseCase;
import com.github.qingquanlv.testflow_service_api.mapper.DataBaseCaseMapper;
import com.github.qingquanlv.testflow_service_api.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Qingquan Lv
 * @Date 2020/12/24 22:51
 * @Version 1.0
 */
//@Service
public class CaseServiceImpl implements CaseService {

    @Autowired
    DataBaseCaseMapper data_base_case_mapper;

    /**
     * 删除dataBaseCase
     *
     * @param request
     * @return
     */
    @Override
    public DeleteDataBaseCaseResponse deleteCase(DeleteDataBaseCaseRequest request) {
        DeleteDataBaseCaseResponse response = new DeleteDataBaseCaseResponse();
        Long id = data_base_case_mapper.Del(request.getCase_id());
        return response;
    }

    /**
     * 查询dataBaseCase
     *
     * @param request
     * @return
     */
    @Override
    public QueryDataBaseCaseResponse queryCase(QueryDataBaseCaseRequest request){
        QueryDataBaseCaseResponse response = new QueryDataBaseCaseResponse();
        //DatabaseCase databaseCase = data_base_case_mapper.Sel(request.getCase_id());

        return response;
    }

    /**
     * 创建dataBaseCase
     *
     * @param request
     */
    @Override
    public CreateDataBaseCaseResponse createCase(CreateDataBaseCaseRequest request){
        //return data_base_case_mapper.Insert(case);
        return null;
    }

    /**
     * 更新dataBaseCase
     *
     * @param request
     * @return
     */
    @Override
    public UpdateDataBaseCaseResponse updateCase(UpdateDataBaseCaseRequest request){
        //return data_base_case_mapper.Update(case);
        return null;

    }

}
