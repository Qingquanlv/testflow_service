package com.github.qingquanlv.testflow_service_api.service.impl;

import com.github.qingquanlv.testflow_service_api.entity.parameter.Parameter;
import com.github.qingquanlv.testflow_service_api.entity.parameter.createparameter.CreateParameterRequest;
import com.github.qingquanlv.testflow_service_api.entity.parameter.createparameter.CreateParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.deleteparameter.DeleteParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.queryallparameter.QueryAllParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.queryallparameter.QueryParameter;
import com.github.qingquanlv.testflow_service_api.entity.parameter.queryparameter.QueryParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.updateparameter.UpdateParameterRequest;
import com.github.qingquanlv.testflow_service_api.entity.parameter.updateparameter.UpdateParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.ParameterCase;
import com.github.qingquanlv.testflow_service_api.mapper.ParameterMapper;
import com.github.qingquanlv.testflow_service_api.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Qingquan Lv
 * @Date 2021/2/3 7:12
 * @Version 1.0
 */
@Service
public class ParameterServiceImpl implements ParameterService {

    @Autowired
    ParameterMapper parameterMapper;

    /**
     * 创建Paramete
     *
     * @param request
     * @return
     */
    @Override
    public CreateParameterResponse createParameter(CreateParameterRequest request) {
        CreateParameterResponse rsp = new CreateParameterResponse();
        List<ParameterCase> parameterCaseList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getParameters())) {
            for (Parameter parameter : request.getParameters()) {
                ParameterCase parameterCase = new ParameterCase();
                parameterCase.setParameter_name(request.getParameter_name());
                parameterCase.setParameter_key(parameter.getParameter_key());
                parameterCase.setParameter_value(parameter.getParameter_value());
                parameterCaseList.add(parameterCase);
            }
        }
        parameterMapper.Ins(parameterCaseList);
        return rsp;
    }

    /**
     * 获取所有Paramete
     *
     * @return
     */
    @Override
    public QueryAllParameterResponse getParameterAll() {
        QueryAllParameterResponse rsp = new QueryAllParameterResponse();
        List<QueryParameter> queryParametersRsp = new ArrayList<>();
        List<ParameterCase> parameterCases = parameterMapper.SelAll();
        for (ParameterCase parameterCase : parameterCases) {
            if (null == queryParametersRsp.stream().filter(i->i.getParameter_name().equals(parameterCase.getParameter_name())).findFirst().orElse(null)) {
                QueryParameter queryParameterRsp = new QueryParameter();
                queryParameterRsp.setParameter_name(parameterCase.getParameter_name());
                List<Parameter> parametersRsp = new ArrayList<>();
                Parameter parameter = new Parameter();
                parameter.setParameter_key(parameterCase.getParameter_key());
                parameter.setParameter_value(parameterCase.getParameter_value());
                parametersRsp.add(parameter);
                queryParameterRsp.setParameters(parametersRsp);
                queryParametersRsp.add(queryParameterRsp);
            }
            else {
                QueryParameter queryParameterRsp = queryParametersRsp.stream().filter(i->i.getParameter_name().equals(parameterCase.getParameter_name())).findFirst().orElse(null);
                List<Parameter> parametersRsp = queryParameterRsp.getParameters();
                Parameter parameter = new Parameter();
                parameter.setParameter_key(parameterCase.getParameter_key());
                parameter.setParameter_value(parameterCase.getParameter_value());
                parametersRsp.add(parameter);
            }
        }
        rsp.setParameters(queryParametersRsp);
        return rsp;
    }

    /**
     * 获取Paramete
     *
     * @param name
     * @return
     */
    @Override
    public QueryParameterResponse getParameter(String name) {
        QueryParameterResponse rsp = new QueryParameterResponse();
        List<Parameter> parameters = new ArrayList<>();
        List<ParameterCase> list = parameterMapper.Sel(name);
        if (!CollectionUtils.isEmpty(list)) {
            for (ParameterCase parameterCase : list) {
                Parameter parameter = new Parameter();
                rsp.setParameter_name(parameterCase.getParameter_name());
                parameter.setParameter_key(parameterCase.getParameter_key());
                parameter.setParameter_value(parameterCase.getParameter_value());
                parameters.add(parameter);
            }
         }
        rsp.setParameters(parameters);
        return rsp;
    }

    /**
     * 获取Paramete
     *
     * @param request
     * @return
     */
    @Override
    public UpdateParameterResponse updateParameter(UpdateParameterRequest request) {
        UpdateParameterResponse rsp = new UpdateParameterResponse();
        List<ParameterCase> parameterCaseList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getParameters())) {
            for (Parameter parameter : request.getParameters()) {
                ParameterCase parameterCase = new ParameterCase();
                parameterCase.setParameter_name(request.getParameter_name());
                parameterCase.setParameter_key(parameter.getParameter_key());
                parameterCase.setParameter_value(parameter.getParameter_value());
                parameterCaseList.add(parameterCase);
            }
        }
        parameterMapper.Del(request.getParameter_name());
        parameterMapper.Ins(parameterCaseList);
        return rsp;
    }

    /**
     * 删除Paramete
     *
     * @param name
     * @return
     */
    @Override
    public DeleteParameterResponse deleteParameter(String name) {
        DeleteParameterResponse rsp = new DeleteParameterResponse();
        parameterMapper.Del(name);
        return rsp;
    }

}

