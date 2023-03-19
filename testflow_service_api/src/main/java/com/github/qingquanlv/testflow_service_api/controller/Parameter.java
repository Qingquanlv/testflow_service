package com.github.qingquanlv.testflow_service_api.controller;

import com.github.qingquanlv.testflow_service_api.entity.parameter.deleteparameter.DeleteParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.queryallparameter.QueryAllParameterRequest;
import com.github.qingquanlv.testflow_service_api.entity.parameter.queryallparameter.QueryAllParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.queryparameter.QueryParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.updateparameter.UpdateParameterRequest;
import com.github.qingquanlv.testflow_service_api.entity.parameter.updateparameter.UpdateParameterResponse;
import com.github.qingquanlv.testflow_service_api.service.impl.ParameterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @Author Qingquan Lv
 * @Version 1.0
 */
@RestController
@RequestMapping("parameter")
public class Parameter {

    @Autowired
    private ParameterServiceImpl parameterService;

    @PostMapping("/queryall")
    public QueryAllParameterResponse getParameterAll(
            @RequestBody QueryAllParameterRequest request){
        QueryAllParameterResponse rsp
                =  parameterService.getParameterAll(request);
        return rsp;
    }

    @GetMapping("/query")
    public QueryParameterResponse getParameter(
            @RequestParam(value = "id", required = true)
            @NotNull(message = "parameter id cannot be null")
                    Long id){
        QueryParameterResponse rsp
                =  parameterService.getParameter(id);
        return rsp;
    }

    @RequestMapping("/update")
    public UpdateParameterResponse updateParameter(
            @RequestBody @Validated UpdateParameterRequest request){
        UpdateParameterResponse rsp
                = parameterService.updateParameter(request);
        return rsp;
    }

    @RequestMapping("/delete")
    public DeleteParameterResponse deleteParameter(
            @RequestParam(value = "id", required = true)
            @NotNull(message = "parameter id cannot be null")
                    Long id){
        DeleteParameterResponse rsp
                = parameterService.deleteParameter(id);
        return rsp;
    }
}
