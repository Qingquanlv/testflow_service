package com.github.qingquanlv.testflow_service_api.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.qingquanlv.testflow_service_api.entity.Status;
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
        List<ParameterCase> parameterCaseList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getParameters())) {
            //parameter index
            Long index = 0L;
            for (HashMap<String, String> map : request.getParameters()) {
                if (!map.isEmpty()) {
                    for (String key : map.keySet()) {
                        ParameterCase parameterCase = new ParameterCase();
                        parameterCase.setParameter_value_index(index);
                        parameterCase.setParameter_name(request.getParameter_name());
                        parameterCase.setParameter_key(key);
                        parameterCase.setParameter_value(map.get(key));
                        parameterCaseList.add(parameterCase);
                    }
                    index++;
                }
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
        Status status = new Status();
        status.setSuccess(true);
        rsp.setStatus(status);
        List<QueryParameter> queryParameterList = new ArrayList<>();
        //查询Database所有parameter
        List<ParameterCase> parameterCases = parameterMapper.SelAll();
        if (!CollectionUtils.isEmpty(parameterCases)) {
            //获取Parameter List
            Set<String> parameterList = parameterCases
                    .stream().map(ParameterCase::getParameter_name)
                    .collect(Collectors.toSet());
            if (!CollectionUtils.isEmpty(parameterList)) {
                //插入Parameter List
                for (String parameter : parameterList) {
                    QueryParameter queryParameter = new QueryParameter();
                    queryParameter.setParameter_name(parameter);
                    List<ParameterCase> hashMapList = parameterCases
                            .stream().filter(item->parameter.equals(item.getParameter_name()))
                            .collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(hashMapList)) {
                        List<HashMap<String, String>> listMap = new ArrayList<>();
                        //获取Parameter Index List
                        Set<Long> parameterIndexList = hashMapList
                                .stream().map(ParameterCase::getParameter_value_index)
                                .collect(Collectors.toSet());
                        if (!CollectionUtils.isEmpty(parameterIndexList)) {
                            //插入Map
                            for (Long index : parameterIndexList) {
                                HashMap<String, String> map = new HashMap<>();
                                List<ParameterCase> parameterCaseIndexList = hashMapList
                                        .stream().filter(item -> index.equals(item.getParameter_value_index()))
                                        .collect(Collectors.toList());
                                for (ParameterCase item : parameterCaseIndexList) {
                                    map.put(item.getParameter_key(), item.getParameter_value());
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
        List<ParameterCase> list = parameterMapper.Sel(name);
        rsp.setParameter_name(name);
        List<HashMap<String, String>> parameterList = new ArrayList<>();
        //获取parameter index set
        Set<Long> indexList = list.stream().map(ParameterCase::getParameter_value_index).collect(Collectors.toSet());
        if (!CollectionUtils.isEmpty(indexList)) {
            for (Long index : indexList) {
                HashMap<String, String> parameterMap = new HashMap<>();
                List<ParameterCase> parameterCases = list.stream()
                        .filter(item->index.equals(item.getParameter_value_index()))
                        .collect(Collectors.toList());
                for (ParameterCase parameterCase : parameterCases) {
                    if (!parameterMap.containsKey(parameterCase.getParameter_key())) {
                        parameterMap.put(parameterCase.getParameter_key(),
                                parameterCase.getParameter_key());
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
        List<ParameterCase> parameterCaseList = new ArrayList<>();
        //parameter index
        Long index = 0L;
        for (HashMap<String, String> map : request.getParameters()) {
            if (!map.isEmpty()) {
                for (String key : map.keySet()) {
                    ParameterCase parameterCase = new ParameterCase();
                    parameterCase.setParameter_value_index(index);
                    parameterCase.setParameter_name(request.getParameter_name());
                    parameterCase.setParameter_key(key);
                    parameterCase.setParameter_value(map.get(key));
                    parameterCaseList.add(parameterCase);
                }
                index++;
            }
        }
        parameterMapper.delete(
                Wrappers.<ParameterCase>lambdaQuery()
                        .eq(ParameterCase::getParameter_name,
                                request.getParameter_name()));
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
        Status status = new Status();
        status.setSuccess(true);
        rsp.setStatus(status);
        parameterMapper.Del(name);
        return rsp;
    }

}

