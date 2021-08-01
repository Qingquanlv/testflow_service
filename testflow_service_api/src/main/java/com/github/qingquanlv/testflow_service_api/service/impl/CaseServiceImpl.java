package com.github.qingquanlv.testflow_service_api.service.impl;

import com.github.qingquanlv.testflow_service_api.common.Constants;
import com.github.qingquanlv.testflow_service_api.common.Utils;
import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.cases_v2.CaseInfo;
import com.github.qingquanlv.testflow_service_api.entity.cases_v2.Config;
import com.github.qingquanlv.testflow_service_api.entity.cases_v2.execcase.ExecCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases_v2.execcase.ExecCaseResponse;
import com.github.qingquanlv.testflow_service_api.service.CaseService;
import com.github.qingquanlv.testflow_service_biz.TestFlowManager;
import com.github.qingquanlv.testflow_service_biz.utilities.FastJsonUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
        Config config = request.getConfig();
        if (null != config) {
            //忽略大小写
            switch (config.getClazz().toLowerCase()) {
                case Constants.REQUEST: {
                    result =
                            testFlowManager.sendRequest(config.getLabel(),
                                "null".equals(config.getExec_params().get("request_body")) || null == config.getExec_params().get("request_body") ? null : config.getExec_params().get("request_body"),
                                "null".equals(config.getExec_params().get("request_configs")) || null == config.getExec_params().get("request_configs") ? null : FastJsonUtil.toMap(config.getExec_params().get("request_configs")),
                                "null".equals(config.getExec_params().get("request_headers")) || null == config.getExec_params().get("request_headers") ? null : FastJsonUtil.toMap(config.getExec_params().get("request_headers")),
                                config.getExec_params().get("request_type"),
                                config.getExec_params().get("content_type"),
                                config.getExec_params().get("url")
                    );
                    break;
                }
                case Constants.DATABASE: {
                    String sqlStr
                            = null == config.getExec_params() ?
                            "" :
                            config.getExec_params()
                            .get("sql_str");
                    String env
                            = null == config.getExec_params() ?
                            "" :
                            config.getExec_params()
                            .get("env");
                    result = testFlowManager.queryDataBase(
                            config.getLabel(),
                            env,
                            sqlStr);
                    break;
                }
                case Constants.PARSE: {
                    String convertMethodSource
                            = null == config.getExec_params() ?
                            "" :
                            config.getExec_params()
                            .get("cvt_method_source");
                    String params
                            = null == config.getExec_params() ?
                            "" :
                            config.getExec_params()
                            .get("parameters");
                    result = testFlowManager.sourceParse(config.getLabel(),
                            convertMethodSource,
                            Utils.toListStr(params));
                    break;
                }
                case Constants.VERIFICATION: {
                    String verificationType
                            = null == config.getExec_params() ?
                            "" :
                            config.getExec_params()
                            .get("verification_type");
                    String params
                            = null == config.getExec_params() ?
                            "" :
                            config.getExec_params()
                            .get("parameters");
                    List<String> parameters = Utils.toListStr(params);
                    if (Constants.XPATHCOMPARE.equals(verificationType)) {
                        result =
                                testFlowManager.verify(config.getLabel(),
                                        parameters.get(0),
                                        parameters.get(1),
                                        parameters.get(2));
                        result = StringUtils.isEmpty(result) ?
                                "Pass" : result;
                    }
                    else if (Constants.OBJCOMPARE.equals(verificationType)) {
                        result =
                                testFlowManager.verify(config.getLabel(),
                                        parameters.get(0),
                                        parameters.get(1),
                                        parameters.get(2),
                                        parameters.get(3));
                        result = StringUtils.isEmpty(result) ?
                                "Pass" : result;
                    }
                    else {
                        result =
                                testFlowManager.verify(config.getLabel(),
                                        parameters.get(0),
                                        parameters.get(1));
                        result = StringUtils.isEmpty(result) ?
                                "Pass" : result;
                    }
                    break;
                }
                default: {}
            }
        }
        CaseInfo caseInfo = new CaseInfo();
        caseInfo.setClazz(config.getClazz());
        caseInfo.setConfig(testFlowManager
                .getBuffer(String.format("config$%s",
                        config.getLabel())));
        caseInfo.setData(result);
        caseInfo.setLabel(config.getLabel());
        rsp.setInfo(caseInfo);
        testFlowManager.deposed();
        return rsp;
    }
}
