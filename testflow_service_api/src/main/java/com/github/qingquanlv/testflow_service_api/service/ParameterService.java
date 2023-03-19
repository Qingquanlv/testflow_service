package com.github.qingquanlv.testflow_service_api.service;

import com.github.qingquanlv.testflow_service_api.entity.parameter.deleteparameter.DeleteParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.queryallparameter.QueryAllParameterRequest;
import com.github.qingquanlv.testflow_service_api.entity.parameter.queryallparameter.QueryAllParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.queryparameter.QueryParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.updateparameter.UpdateParameterRequest;
import com.github.qingquanlv.testflow_service_api.entity.parameter.updateparameter.UpdateParameterResponse;

/**
 * @Author Qingquan Lv
 * @Version 1.0
 */
public interface ParameterService {
    /**
     * 获取Parameter
     *
     * @param id
     * @return
     */
    QueryParameterResponse getParameter(Long id);

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
    QueryAllParameterResponse getParameterAll(QueryAllParameterRequest request);

    /**
     * 删除Paramete
     *
     * @param id
     * @return
     */
    DeleteParameterResponse deleteParameter(Long id);

}

