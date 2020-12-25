package com.github.qingquanlv.testflow_service_api.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.createdatabase.CreateDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.createdatabase.CreateDataBaseCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.deletedatabase.DeleteDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.deletedatabase.DeleteDataBaseCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.updatedatabase.UpdateDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.updatedatabase.UpdateDataBaseCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature.FeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature.FeatureResponse;
import com.github.qingquanlv.testflow_service_api.service.StepService;
import com.github.qingquanlv.testflow_service_api.service.impl.CaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author Qingquan Lv
 * @Date 2020/12/24 22:36
 * @Version 1.0
 */
public class Case {

    @Autowired
    private CaseServiceImpl caseServiceImpl;

    @PostMapping(path = "database/create")
    public CreateDataBaseCaseResponse createCase(@RequestBody CreateDataBaseCaseRequest request) {
        CreateDataBaseCaseResponse rsp =  caseServiceImpl.createCase(request);
        String rspStr = JSONObject.toJSONString(rsp);
        return rsp;
    }

    @PostMapping(path = "database/update")
    public UpdateDataBaseCaseResponse updateCase(@RequestBody UpdateDataBaseCaseRequest request) {
        UpdateDataBaseCaseResponse rsp = new UpdateDataBaseCaseResponse();
        rsp = caseServiceImpl.updateCase(request);
        String rspStr = JSONObject.toJSONString(rsp);
        return rsp;
    }

    @PostMapping(path = "database/delete")
    public DeleteDataBaseCaseResponse deleteCase(@RequestBody DeleteDataBaseCaseRequest request) {
        DeleteDataBaseCaseResponse rsp = new DeleteDataBaseCaseResponse();
        rsp = caseServiceImpl.deleteCase(request);
        String rspStr = JSONObject.toJSONString(rsp);
        return rsp;
    }

}
