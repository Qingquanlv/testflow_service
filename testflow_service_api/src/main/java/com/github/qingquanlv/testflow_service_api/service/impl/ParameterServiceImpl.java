package com.github.qingquanlv.testflow_service_api.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.parameter.createparameter.CreateParameterRequest;
import com.github.qingquanlv.testflow_service_api.entity.parameter.createparameter.CreateParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.deleteparameter.DeleteParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.queryallparameter.QueryAllParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.queryallparameter.QueryParameter;
import com.github.qingquanlv.testflow_service_api.entity.parameter.queryparameter.QueryParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.updateparameter.UpdateParameterRequest;
import com.github.qingquanlv.testflow_service_api.entity.parameter.updateparameter.UpdateParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Parameter;
import com.github.qingquanlv.testflow_service_api.mapper.ParameterMapper;
import com.github.qingquanlv.testflow_service_api.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        Status status = new Status();
        status.setSuccess(true);
        rsp.setStatus(status);
        List<Parameter> parameterCaseList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getParameters())) {
            //parameter index
            Long index = 0L;
            for (HashMap<String, String> map : request.getParameters()) {
                if (!map.isEmpty()) {
                    for (String key : map.keySet()) {
                        Parameter parameterCase = new Parameter();
                        parameterCase.setParameterValueIndex(index);
                        parameterCase.setParameterName(request.getParameter_name());
                        parameterCase.setParameterKey(key);
                        parameterCase.setParameterKey(map.get(key));
                        parameterCaseList.add(parameterCase);
                    }
                    index++;
                }
            }
        }
        if (!CollectionUtils.isEmpty(parameterCaseList)) {
            for (Parameter item : parameterCaseList)
            {
                parameterMapper.insert(item);
            }
        }
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
        Status status = new Status();
        status.setSuccess(true);
        rsp.setStatus(status);
        List<QueryParameter> queryParameterList = new ArrayList<>();
        //查询Database所有parameter
        List<Parameter> parameterCases
                = parameterMapper.selectList(Wrappers.emptyWrapper());
        if (!CollectionUtils.isEmpty(parameterCases)) {
            //获取Parameter List
            Set<String> parameterList = parameterCases
                    .stream().map(Parameter::getParameterName)
                    .collect(Collectors.toSet());
            if (!CollectionUtils.isEmpty(parameterList)) {
                //插入Parameter List
                for (String parameter : parameterList) {
                    QueryParameter queryParameter = new QueryParameter();
                    queryParameter.setParameter_name(parameter);
                    List<Parameter> hashMapList = parameterCases
                            .stream().filter(item->parameter.equals(item.getParameterName()))
                            .collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(hashMapList)) {
                        List<HashMap<String, String>> listMap = new ArrayList<>();
                        //获取Parameter Index List
                        Set<Long> parameterIndexList = hashMapList
                                .stream().map(Parameter::getParameterValueIndex)
                                .collect(Collectors.toSet());
                        if (!CollectionUtils.isEmpty(parameterIndexList)) {
                            //插入Map
                            for (Long index : parameterIndexList) {
                                HashMap<String, String> map = new HashMap<>();
                                List<Parameter> parameterCaseIndexList = hashMapList
                                        .stream().filter(item -> index.equals(item.getParameterValueIndex()))
                                        .collect(Collectors.toList());
                                for (Parameter item : parameterCaseIndexList) {
                                    map.put(item.getParameterKey(), item.getParameterValue());
                                }
                                listMap.add(map);
                            }
                        }
                        queryParameter.setParameters(listMap);
                    }
                    queryParameterList.add(queryParameter);
                }
            }
        }
        rsp.setParameters(queryParameterList);
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
        Status status = new Status();
        status.setSuccess(true);
        rsp.setStatus(status);
        List<Parameter> list
                = parameterMapper.selectList(
                        Wrappers.<Parameter>lambdaQuery()
                        .eq(Parameter::getParameterName, name));
        rsp.setParameter_name(name);
        List<HashMap<String, String>> parameterList = new ArrayList<>();
        //获取parameter index set
        Set<Long> indexList = list.stream().map(Parameter::getParameterValueIndex).collect(Collectors.toSet());
        if (!CollectionUtils.isEmpty(indexList)) {
            for (Long index : indexList) {
                HashMap<String, String> parameterMap = new HashMap<>();
                List<Parameter> parameterCases = list.stream()
                        .filter(item->index.equals(item.getParameterValueIndex()))
                        .collect(Collectors.toList());
                for (Parameter parameterCase : parameterCases) {
                    if (!parameterMap.containsKey(parameterCase.getParameterKey())) {
                        parameterMap.put(parameterCase.getParameterKey(),
                                parameterCase.getParameterKey());
                    }
                }
                parameterList.add(parameterMap);
            }
         }
        rsp.setParameters(parameterList);
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
        Status status = new Status();
        status.setSuccess(true);
        rsp.setStatus(status);
        List<Parameter> parameterCaseList = new ArrayList<>();
        //parameter index
        Long index = 0L;
        for (HashMap<String, String> map : request.getParameters()) {
            if (!map.isEmpty()) {
                for (String key : map.keySet()) {
                    Parameter parameterCase = new Parameter();
                    parameterCase.setParameterValueIndex(index);
                    parameterCase.setParameterName(request.getParameter_name());
                    parameterCase.setParameterKey(key);
                    parameterCase.setParameterValue(map.get(key));
                    parameterCaseList.add(parameterCase);
                }
                index++;
            }
        }
        parameterMapper.delete(
                Wrappers.<Parameter>lambdaQuery()
                        .eq(Parameter::getParameterName,
                                request.getParameter_name()));
        if (!CollectionUtils.isEmpty(parameterCaseList)) {
            for (Parameter item : parameterCaseList)
            {
                parameterMapper.insert(item);
            }
        }
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
        Status status = new Status();
        status.setSuccess(true);
        rsp.setStatus(status);
        parameterMapper.delete(
                Wrappers.<Parameter>lambdaQuery()
                        .eq(Parameter::getParameterName, name));
        return rsp;
    }

}

