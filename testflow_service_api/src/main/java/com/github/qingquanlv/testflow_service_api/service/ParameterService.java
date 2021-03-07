package com.github.qingquanlv.testflow_service_api.service;

import com.github.qingquanlv.testflow_service_api.entity.parameter.createparameter.CreateParameterRequest;
import com.github.qingquanlv.testflow_service_api.entity.parameter.createparameter.CreateParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.deleteparameter.DeleteParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.queryallparameter.QueryAllParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.queryparameter.QueryParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.updateparameter.UpdateParameterRequest;
import com.github.qingquanlv.testflow_service_api.entity.parameter.updateparameter.UpdateParameterResponse;

/**
 * @Author Qingquan Lv
 * @Date 2021/2/3 6:58
 * @Version 1.0
 */
public interface ParameterService {
    /**
     * 创建Paramete
     *
     * @param request
     * @return
     */
    CreateParameterResponse createParameter(CreateParameterRequest request);

    /**
     * 执行Paramete
     *
     * @param name
     * @return
     */
    QueryParameterResponse getParameter(String name);

    /**
     * 获取Paramete
     *
     * @param request
     * @return
     */
    UpdateParameterResponse updateParameter(UpdateParameterRequest request);


    /**
     * 获取ParameteAll
     *
     * @return
     */
    QueryAllParameterResponse getParameterAll();

    /**
     * 删除Paramete
     *
     * @param name
     * @return
     */
    DeleteParameterResponse deleteParameter(String name);

}

