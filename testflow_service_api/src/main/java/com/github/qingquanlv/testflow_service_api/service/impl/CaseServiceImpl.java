package com.github.qingquanlv.testflow_service_api.service.impl;

import com.github.qingquanlv.testflow_service_api.DAO.DatabaseCaseDao;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.createdatabase.CreateDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.createdatabase.CreateDataBaseCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.deletedatabase.DeleteDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.deletedatabase.DeleteDataBaseCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.updatedatabase.UpdateDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.updatedatabase.UpdateDataBaseCaseResponse;
import com.github.qingquanlv.testflow_service_api.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Qingquan Lv
 * @Date 2020/12/24 22:51
 * @Version 1.0
 */
@Service
public class CaseServiceImpl implements CaseService {

    @Autowired
    private DatabaseCaseDao databaseCaseDao;

    @Autowired
    UserMapper userMapper;

    public User Sel(int id){
        return userMapper.Sel(id);
    }

    /**
     * 创建dataBaseCase
     *
     * @param request
     * @return
     */
    @Override
    public CreateDataBaseCaseResponse createCase(CreateDataBaseCaseRequest request) {
        CreateDataBaseCaseResponse rsp = new CreateDataBaseCaseResponse();
        return rsp;
    }


    /**
     * 更新dataBaseCase
     *
     * @param request
     * @return
     */
    @Override
    public UpdateDataBaseCaseResponse updateCase(UpdateDataBaseCaseRequest request) {
        UpdateDataBaseCaseResponse rsp = new UpdateDataBaseCaseResponse();
        return rsp;
    }

    /**
     * 删除dataBaseCase
     *
     * @param request
     * @return
     */
    @Override
    public DeleteDataBaseCaseResponse deleteCase(DeleteDataBaseCaseRequest request) {
        DeleteDataBaseCaseResponse rsp = new DeleteDataBaseCaseResponse();
        return rsp;
    }
}
