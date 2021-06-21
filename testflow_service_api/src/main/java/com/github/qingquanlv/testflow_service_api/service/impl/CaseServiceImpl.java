package com.github.qingquanlv.testflow_service_api.service.impl;

import com.github.qingquanlv.testflow_service_api.common.Constants;
import com.github.qingquanlv.testflow_service_api.common.Utils;
import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.cases_v2.execcase.ExecCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases_v2.execcase.ExecCaseResponse;
import com.github.qingquanlv.testflow_service_api.service.CaseService;
import com.github.qingquanlv.testflow_service_biz.TestFlowManager;
import com.github.qingquanlv.testflow_service_biz.utilities.FastJsonUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

/**
 * @Author Qingquan Lv
 * @Date 2020/12/24 22:51
 * @Version 1.0
 */
@Service
public class CaseServiceImpl implements CaseService {

    /**
     * 执行Case
     *
     * @param request
     * @return
     */
    @Override
    public ExecCaseResponse execCase(ExecCaseRequest request) {
        Status status = new Status();
        status.setSuccess(true);
        ExecCaseResponse rsp
                = ExecCaseResponse.builder()
                .status(status)
                .params(request.getParams())
                .build();
        String result = "";
        //buffer 公共key
        String publicKey = Utils.hashKeyForDisk(String.format("%s%s",
                System.currentTimeMillis(),
                UUID.randomUUID().toString()));
        TestFlowManager testFlowManager = new TestFlowManager(publicKey);
        //输入参数
        if (!CollectionUtils.isEmpty(request.getParams())) {
            for (String key : request.getParams().keySet()) {
                testFlowManager.addBuffer(key, request.getParams().get(key));
            }
        }
        if (null != request.getConfig()) {
            rsp.setId(request.getConfig().getId());
            //忽略大小写
            switch (request.getConfig().getClazz().toLowerCase()) {
                case Constants.DATABASE: {
                    String sqlStr
                            = request.getConfig().getParams()
                            .stream().allMatch(item ->
                                    "sqlStr".equals(item.getKey()))
                            ? request.getConfig().getParams()
                            .stream().filter(item ->
                                    "sqlStr".equals(item.getKey()))
                            .findFirst()
                            .get().getValue() : "";
                    result = testFlowManager.queryDataBase(request.getConfig().getId(),
                            sqlStr);
                    break;
                }
                case Constants.PARSE: {
                    String convertMethodSource
                            = request.getConfig().getParams()
                            .stream().allMatch(item ->
                                    "convertMethodSource".equals(item.getKey()))
                            ? request.getConfig().getParams()
                            .stream().filter(item ->
                                    "convertMethodSource".equals(item.getKey()))
                            .findFirst()
                            .get().getValue() : "";
                    String params
                            = request.getConfig().getParams()
                            .stream().allMatch(item ->
                                    "parameters".equals(item.getKey()))
                            ? request.getConfig().getParams()
                            .stream().filter(item ->
                                    "parameters".equals(item.getKey()))
                            .findFirst()
                            .get().getValue() : "";
                    result = testFlowManager.sourceParse(request.getConfig().getId(),
                            convertMethodSource,
                            FastJsonUtil.toList(params));
                    break;
                }
                case Constants.REQUEST: {
                    String requestBody
                            = request.getConfig().getParams()
                            .stream().allMatch(item ->
                                    "requestBody".equals(item.getKey()))
                            ? request.getConfig().getParams()
                            .stream().filter(item ->
                                    "requestBody".equals(item.getKey()))
                            .findFirst()
                            .get().getValue() : "";
                    String requestConfigs
                            = request.getConfig().getParams()
                            .stream().allMatch(item ->
                                    "requestConfigs".equals(item.getKey()))
                            ? request.getConfig().getParams()
                            .stream().filter(item ->
                                    "requestConfigs".equals(item.getKey()))
                            .findFirst()
                            .get().getValue() : "";
                    String requestHeaders
                            = request.getConfig().getParams()
                            .stream().allMatch(item ->
                                    "requestHeaders".equals(item.getKey()))
                            ? request.getConfig().getParams()
                            .stream().filter(item ->
                                    "requestHeaders".equals(item.getKey()))
                            .findFirst()
                            .get().getValue() : "";
                    String requestType
                            = request.getConfig().getParams()
                            .stream().allMatch(item ->
                                    "requestType".equals(item.getKey()))
                            ? request.getConfig().getParams()
                            .stream().filter(item ->
                                    "requestType".equals(item.getKey()))
                            .findFirst()
                            .get().getValue() : "";
                    String contentType
                            = request.getConfig().getParams()
                            .stream().allMatch(item ->
                                    "contentType".equals(item.getKey()))
                            ? request.getConfig().getParams()
                            .stream().filter(item ->
                                    "contentType".equals(item.getKey()))
                            .findFirst()
                            .get().getValue() : "";
                    String url
                            = request.getConfig().getParams()
                            .stream().allMatch(item ->
                                    "url".equals(item.getKey()))
                            ? request.getConfig().getParams()
                            .stream().filter(item ->
                                    "url".equals(item.getKey()))
                            .findFirst()
                            .get().getValue() : "";
                    result = testFlowManager.sendRequest(request.getConfig().getId(),
                            requestBody,
                            "null".equals(requestConfigs) || null == requestConfigs ? null : FastJsonUtil.toMap(requestConfigs),
                            "null".equals(requestHeaders) || null == requestHeaders ? null : FastJsonUtil.toMap(requestHeaders),
                            requestType,
                            contentType,
                            url
                    );
                    break;
                }
                case Constants.VERIFICATION: {
                    String verificationType
                            = request.getConfig().getParams()
                            .stream().allMatch(item ->
                                    "verificationType".equals(item.getKey()))
                            ? request.getConfig().getParams()
                            .stream().filter(item ->
                                    "verificationType".equals(item.getKey()))
                            .findFirst()
                            .get().getValue() : "";
                    String params
                            = request.getConfig().getParams()
                            .stream().allMatch(item ->
                                    "parameters".equals(item.getKey()))
                            ? request.getConfig().getParams()
                            .stream().filter(item ->
                                    "parameters".equals(item.getKey()))
                            .findFirst()
                            .get().getValue() : "";
                    List<String> parameters = FastJsonUtil.toList(params);
                    if (Constants.COMPARE.equals(verificationType)) {
                        testFlowManager.verify(parameters.get(0), parameters.get(1));
                    } else if (Constants.XPATHCOMPARE.equals(verificationType)) {
                        testFlowManager.verify(parameters.get(0), parameters.get(1), parameters.get(2));
                    } else if (Constants.OBJCOMPARE.equals(verificationType)) {
                        testFlowManager.verify(parameters.get(0), parameters.get(1), parameters.get(2), parameters.get(3));
                    }
                    break;
                }
                default: {
                }
            }
        }
        testFlowManager.deposed();
        rsp.setResult(result);
        return rsp;
    }
}
