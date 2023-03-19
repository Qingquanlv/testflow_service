package com.github.qingquanlv.testflow_service_api.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.qingquanlv.testflow_service_api.common.Utils;
import com.github.qingquanlv.testflow_service_api.dao.EnvTabMapper;
import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.cases_v2.CaseInfo;
import com.github.qingquanlv.testflow_service_api.entity.cases_v2.Config;
import com.github.qingquanlv.testflow_service_api.entity.cases_v2.execcase.ExecCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases_v2.execcase.ExecCaseResponse;
import com.github.qingquanlv.testflow_service_api.model.po.EnvTabPo;
import com.github.qingquanlv.testflow_service_api.service.CaseService;
import com.github.qingquanlv.testflow_service_api.service.IExecCase;
import com.github.qingquanlv.testflow_service_biz.TestFlowManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

/**
 * @Author Qingquan Lv
 * @Version 1.0
 */
@Service
public class CaseServiceImpl implements CaseService {


    @Autowired
    EnvTabMapper envConfigMapper;

    @Autowired
    CaseFactory caseFactory;

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
        String publicKey =
                UUID.randomUUID().toString();
        TestFlowManager testFlowManager = new TestFlowManager(publicKey);
        List<EnvTabPo> envConfigs
                = envConfigMapper.selectList(
                Wrappers.emptyWrapper());
        //输入配置
        if (!CollectionUtils.isEmpty(envConfigs)) {
            for (EnvTabPo config : envConfigs) {
                testFlowManager.addBuffer(
                        String.format("env$$config$$%s$$%s",
                                config.getCaseType(),
                                config.getConfigKey()),
                        config.getConfigVal());
            }
        }
        //
        //输入参数
        if (!CollectionUtils.isEmpty(request.getParams())) {
            for (String key : request.getParams().keySet()) {
                testFlowManager.addBuffer(key, request.getParams().get(key));
            }
        }
        Config config = request.getConfig();
        IExecCase iExecCase = caseFactory.reflactCaze(
            Utils.getCaseNameByType(config.getClazz().toLowerCase()));
        result = iExecCase.execute(publicKey, config);

//        if (null != config) {
//            //忽略大小写
//            switch (config.getClazz().toLowerCase()) {
//                case Constants.REQUEST: {
//                    result =
//                            testFlowManager.sendRequest(config.getLabel(),
//                                    "null".equals(config.getExec_params().get("request_body")) || null == config.getExec_params().get("request_body") ? null : config.getExec_params().get("request_body"),
//                                    "null".equals(config.getExec_params().get("request_configs")) || null == config.getExec_params().get("request_configs") ? null : config.getExec_params().get("request_configs"),
//                                    "null".equals(config.getExec_params().get("request_headers")) || null == config.getExec_params().get("request_headers") ? null : config.getExec_params().get("request_headers"),
//                                    config.getExec_params().get("request_type"),
//                                    config.getExec_params().get("content_type"),
//                                    config.getExec_params().get("url")
//                            );
//                    break;
//                }
//                case Constants.DATABASE: {
//                    String sqlStr
//                            = null == config.getExec_params() ?
//                            "" :
//                            config.getExec_params()
//                                    .get("sql_str");
//                    String env
//                            = null == config.getExec_params() ?
//                            "" :
//                            config.getExec_params()
//                                    .get("env");
//                    result = testFlowManager.queryDataBase(
//                            config.getLabel(),
//                            env,
//                            sqlStr);
//                    break;
//                }
//                case Constants.TIDB: {
//                    String sqlStr
//                            = null == config.getExec_params() ?
//                            "" :
//                            config.getExec_params()
//                                    .get("sql_str");
//                    String env
//                            = null == config.getExec_params() ?
//                            "" :
//                            config.getExec_params()
//                                    .get("env");
//                    result = testFlowManager.queryTIDB(
//                            config.getLabel(),
//                            env,
//                            sqlStr);
//                    break;
//                }
//                case Constants.DRUID: {
//                    String sqlStr
//                            = null == config.getExec_params() ?
//                            "" :
//                            config.getExec_params()
//                                    .get("sql_str");
//                    String env
//                            = null == config.getExec_params() ?
//                            "" :
//                            config.getExec_params()
//                                    .get("env");
//                    result = testFlowManager.queryDruid(
//                            config.getLabel(),
//                            env,
//                            sqlStr);
//                    break;
//                }
//                case Constants.PARSE: {
//                    String convertMethodSource
//                            = null == config.getExec_params() ?
//                            "" :
//                            config.getExec_params()
//                                    .get("cvt_method_source");
//                    String params
//                            = null == config.getExec_params() ?
//                            "" :
//                            config.getExec_params()
//                                    .get("parameters");
//                    result = testFlowManager.sourceParse(config.getLabel(),
//                            convertMethodSource,
//                            Utils.toListStr(params));
//                    break;
//                }
//                case Constants.COMPARE_VAL: {
//                    String expVal
//                            = null == config.getExec_params() ?
//                            "" :
//                            config.getExec_params()
//                                    .get("expVal");
//                    String atlVal
//                            = null == config.getExec_params() ?
//                            "" :
//                            config.getExec_params()
//                                    .get("atlVal");
//                    result =
//                            testFlowManager.verify(config.getLabel(),
//                                    expVal,
//                                    atlVal);
//
//                    break;
//                }
//                case Constants.COMPARE_PATH: {
//                    String atlObj
//                            = null == config.getExec_params() ?
//                            "" :
//                            config.getExec_params()
//                                    .get("atlObj");
//                    String JsonFilter
//                            = null == config.getExec_params() ?
//                            "" :
//                            config.getExec_params()
//                                    .get("jsonFilter");
//                    String expVal
//                            = null == config.getExec_params() ?
//                            "" :
//                            config.getExec_params()
//                                    .get("expVal");
//                    result =
//                            testFlowManager.verify(config.getLabel(),
//                                    atlObj,
//                                    JsonFilter,
//                                    expVal);
//                    break;
//                }
//                case Constants.COMPARE_OBJ: {
//                    String expObj
//                            = null == config.getExec_params() ?
//                            "" :
//                            config.getExec_params()
//                                    .get("expObj");
//                    String atlObj
//                            = null == config.getExec_params() ?
//                            "" :
//                            config.getExec_params()
//                                    .get("atlObj");
//                    String pkMap
//                            = null == config.getExec_params() ?
//                            "" :
//                            config.getExec_params()
//                                    .get("pkMap");
//                    String noCompareItemMap
//                            = null == config.getExec_params() ?
//                            "" :
//                            config.getExec_params()
//                                    .get("noCompareItemMap");
//                    String thresholdMap
//                            = null == config.getExec_params() ?
//                            "" :
//                            config.getExec_params()
//                                    .get("thresholdMap");
//                    result =
//                            testFlowManager.verify(config.getLabel(),
//                                    expObj,
//                                    atlObj,
//                                    pkMap,
//                                    noCompareItemMap,
//                                    thresholdMap);
//                    break;
//                }
//                default: {
//                }
//            }
//        }
        CaseInfo caseInfo = new CaseInfo();
        caseInfo.setClazz(config.getClazz());
        caseInfo.setConfig(testFlowManager
                .getBuffer(String.format("config$%s",
                        config.getLabel())));
        caseInfo.setData(result);
        caseInfo.setLabel(config.getLabel());
        caseInfo.setStatus(null == testFlowManager
                .getBuffer(String.format("status$%s",
                        config.getLabel()))
                ? 0
                : Integer.parseInt(testFlowManager
                .getBuffer(String.format("status$%s",
                        config.getLabel()))));
        rsp.setInfo(caseInfo);
        testFlowManager.deposed();
        return rsp;
    }
}
