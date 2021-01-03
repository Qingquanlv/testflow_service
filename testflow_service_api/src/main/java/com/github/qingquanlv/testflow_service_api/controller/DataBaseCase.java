package com.github.qingquanlv.testflow_service_api.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.createdatabase.CreateDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.createdatabase.CreateDataBaseCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.deletedatabase.DeleteDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.deletedatabase.DeleteDataBaseCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.updatedatabase.UpdateDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.updatedatabase.UpdateDataBaseCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.DatabaseCase;
import com.github.qingquanlv.testflow_service_api.service.impl.CaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Qingquan Lv
 * @Date 2020/12/31 22:07
 * @Version 1.0
 */
@RestController
@RequestMapping("case/database")
public class DataBaseCase {

    //@Autowired
    private CaseServiceImpl caseServiceImpl;

    @PostMapping(path = "/create")
    public CreateDataBaseCaseResponse createCase(@RequestBody CreateDataBaseCaseRequest request) {
        CreateDataBaseCaseResponse rsp =  caseServiceImpl.createCase(request);
        String rspStr = JSONObject.toJSONString(rsp);
        return rsp;
    }

    @PostMapping(path = "/update")
    public UpdateDataBaseCaseResponse updateCase(@RequestBody UpdateDataBaseCaseRequest request) {
        UpdateDataBaseCaseResponse rsp = new UpdateDataBaseCaseResponse();
        rsp = caseServiceImpl.updateCase(request);
        String rspStr = JSONObject.toJSONString(rsp);
        return rsp;
    }

    @PostMapping(path = "/delete")
    public DeleteDataBaseCaseResponse deleteCase(@RequestBody DeleteDataBaseCaseRequest request) {
        DeleteDataBaseCaseResponse rsp = new DeleteDataBaseCaseResponse();
        rsp = caseServiceImpl.deleteCase(request);
        String rspStr = JSONObject.toJSONString(rsp);
        return rsp;
    }

    @RequestMapping("getCase/{id}")
    public DeleteDataBaseCaseResponse queryCase(@PathVariable Long id){
        DeleteDataBaseCaseResponse rsp = new DeleteDataBaseCaseResponse();
        String rspStr = JSONObject.toJSONString(rsp);
        return rsp;
    }
}

