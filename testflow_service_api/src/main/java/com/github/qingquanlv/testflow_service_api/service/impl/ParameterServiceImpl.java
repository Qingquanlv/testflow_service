package com.github.qingquanlv.testflow_service_api.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.parameter.deleteparameter.DeleteParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.queryallparameter.QueryAllParameterRequest;
import com.github.qingquanlv.testflow_service_api.entity.parameter.queryallparameter.QueryAllParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.queryallparameter.QueryParameter;
import com.github.qingquanlv.testflow_service_api.entity.parameter.queryparameter.QueryParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.updateparameter.UpdateParameterRequest;
import com.github.qingquanlv.testflow_service_api.entity.parameter.updateparameter.UpdateParameterResponse;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.ParameterDetailsTab;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.ParameterTab;
import com.github.qingquanlv.testflow_service_api.mapper.ParameterDetailsMapper;
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
 * @Version 1.0
 */
@Service
public class ParameterServiceImpl implements ParameterService {

    @Autowired
    ParameterDetailsMapper parameterDetailsMapper;

    @Autowired
    ParameterMapper parameterMapper;

    /**
     * 获取所有Paramete
     *
     * @return
     */
    @Override
    public QueryAllParameterResponse getParameterAll(QueryAllParameterRequest request) {
        Status status = new Status();
        status.setSuccess(true);
        String filter
                = null == request.getFilter() ? "" : request.getFilter();
        long pageNum
                = null == request.getPageNum() ? 1L : request.getPageNum();
        long pageSize
                = null == request.getPageSize() ? 28L : request.getPageSize();
        Page<ParameterTab> parameterList
                = parameterMapper.selectPage(new Page<>(pageNum, pageSize),
                Wrappers.<ParameterTab>lambdaQuery()
                        .like(ParameterTab::getParameterName, filter)
                        .orderByDesc(ParameterTab::getId));
        //赋值taskResult
        List<QueryParameter> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(parameterList.getRecords())) {
            for (ParameterTab item : parameterList.getRecords()) {
                QueryParameter parameter
                        = QueryParameter
                        .builder()
                        .id(item.getId())
                        .parameter_name(item.getParameterName())
                        .build();
                list.add(parameter);
            }
        }
        QueryAllParameterResponse rsp =
                QueryAllParameterResponse.builder()
                        .status(status)
                        .parameters(list)
                        .total(parameterList.getTotal()).build();
        return rsp;
    }

    /**
     * 获取Paramete
     *
     * @param id
     * @return
     */
    @Override
    public QueryParameterResponse getParameter(Long id) {
        QueryParameterResponse rsp = new QueryParameterResponse();
        Status status = new Status();
        status.setSuccess(true);
        rsp.setStatus(status);

        ParameterTab parameterName
                = parameterMapper.selectById(id);
        if (null != parameterName) {
            List<ParameterDetailsTab> list
                    = parameterDetailsMapper.selectList(
                    Wrappers.<ParameterDetailsTab>lambdaQuery()
                            .eq(ParameterDetailsTab::getParameterId, id));
            rsp.setParameter_name(parameterName.getParameterName());
            List<HashMap<String, String>> parameterList = new ArrayList<>();
            //get parameter index set
            Set<Long> indexList = list.stream()
                    .map(ParameterDetailsTab::getParameterValueIndex)
                    .collect(Collectors.toSet());
            if (!CollectionUtils.isEmpty(indexList)) {
                for (Long index : indexList) {
                    HashMap<String, String> parameterMap = new HashMap<>();
                    List<ParameterDetailsTab> parameterCases = list.stream()
                            .filter(item -> index.equals(item.getParameterValueIndex()))
                            .collect(Collectors.toList());
                    for (ParameterDetailsTab parameterCase : parameterCases) {
                        if (!parameterMap.containsKey(parameterCase.getParameterKey())) {
                            parameterMap.put(parameterCase.getParameterKey(),
                                    parameterCase.getParameterValue());
                        }
                    }
                    parameterList.add(parameterMap);
                }
            }
            rsp.setParameters(parameterList);
        }
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
        Status status = new Status();
        status.setSuccess(true);

        ParameterTab parameterName
                = ParameterTab
                .builder()
                .parameterName(request.getParameterName())
                .build();
        if (null == request.getParameterId()) {
            parameterMapper.insert(parameterName);
        }
        else {
            parameterName.setId(request.getParameterId());
            parameterMapper.updateById(parameterName);
            parameterDetailsMapper.delete(
                    Wrappers.<ParameterDetailsTab>lambdaQuery()
                            .eq(ParameterDetailsTab::getParameterId,
                                    request.getParameterId()));
            parameterName.setId(request.getParameterId());

        }
        List<ParameterDetailsTab> parameterCaseList = new ArrayList<>();
        //parameter index
        Long index = 0L;
        for (HashMap<String, String> map : request.getParameters()) {
            if (!map.isEmpty()) {
                for (String key : map.keySet()) {
                    ParameterDetailsTab parameterCase
                            = ParameterDetailsTab.builder()
                            .appid(0L)
                            .parameterValueIndex(index)
                            .parameterName(request.getParameterName())
                            .parameterId(parameterName.getId())
                            .parameterKey(key)
                            .parameterValue(map.get(key))
                            .build();
                    parameterCaseList.add(parameterCase);
                }
                index++;
            }
        }
        if (!CollectionUtils.isEmpty(parameterCaseList)) {
            parameterDetailsMapper.saveBatchByNative(parameterCaseList);
        }
        UpdateParameterResponse rsp
                = UpdateParameterResponse
                .builder()
                .status(status)
                .parameterId(parameterName.getId())
                .build();
        return rsp;
    }

    /**
     * 删除Paramete
     *
     * @param id
     * @return
     */
    @Override
    public DeleteParameterResponse deleteParameter(Long id) {
        DeleteParameterResponse rsp = new DeleteParameterResponse();
        Status status = new Status();
        status.setSuccess(true);
        rsp.setStatus(status);
        parameterMapper.delete(
                Wrappers.<ParameterTab>lambdaQuery()
                        .eq(ParameterTab::getId, id));
        parameterDetailsMapper.delete(
                Wrappers.<ParameterDetailsTab>lambdaQuery()
                        .eq(ParameterDetailsTab::getParameterId, id));
        return rsp;
    }

}

