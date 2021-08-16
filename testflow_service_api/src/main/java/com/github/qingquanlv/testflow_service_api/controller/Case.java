package com.github.qingquanlv.testflow_service_api.controller;

import com.github.qingquanlv.testflow_service_api.entity.cases_v2.execcase.ExecCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases_v2.execcase.ExecCaseResponse;
import com.github.qingquanlv.testflow_service_api.service.impl.CaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Qingquan Lv
 * @Date 2021/2/10 15:55
 * @Version 1.0
 */
@RestController
@RequestMapping("case")
public class Case {

    @Autowired
    private CaseServiceImpl caseService;

    @RequestMapping("/exec")
    public String execCase(@RequestBody ExecCaseRequest request){
        ExecCaseResponse rsp = caseService.execCase(request);
        return rsp.getInfo().getData();
    }

}
