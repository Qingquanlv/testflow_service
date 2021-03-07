package com.github.qingquanlv.testflow_service_api.controller;

import com.github.qingquanlv.testflow_service_api.entity.parameter.createparameter.CreateParameterRequest;
import com.github.qingquanlv.testflow_service_api.entity.parameter.createparameter.CreateParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.deleteparameter.DeleteParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.queryallparameter.QueryAllParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.queryparameter.QueryParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.updateparameter.UpdateParameterRequest;
import com.github.qingquanlv.testflow_service_api.entity.parameter.updateparameter.UpdateParameterResponse;
import com.github.qingquanlv.testflow_service_api.service.impl.ParameterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Qingquan Lv
 * @Date 2021/2/3 6:47
 * @Version 1.0
 */
@RestController
@RequestMapping("parameter")
public class Parameter {

    @Autowired
    private ParameterServiceImpl parameterService;

    @PostMapping(path = "/create")
    public CreateParameterResponse createParameter(@RequestBody @Validated CreateParameterRequest request) {
        CreateParameterResponse rsp =  parameterService.createParameter(request);
        return rsp;
    }

    @RequestMapping("/queryall")
    public QueryAllParameterResponse getParameterAll(){
        QueryAllParameterResponse rsp =  parameterService.getParameterAll();
        return rsp;
    }

    @RequestMapping("/query")
    public QueryParameterResponse getParameter(@RequestParam(value = "name")String name){
        QueryParameterResponse rsp =  parameterService.getParameter(name);
        return rsp;
    }

    @RequestMapping("/update")
    public UpdateParameterResponse updateParameter(@RequestBody UpdateParameterRequest request){
        UpdateParameterResponse rsp =  parameterService.updateParameter(request);
        return rsp;
    }

    @RequestMapping("/delete")
    public DeleteParameterResponse deleteParameter(@RequestParam(value = "name")String name){
        DeleteParameterResponse rsp =  parameterService.deleteParameter(name);
        return rsp;
    }
}
