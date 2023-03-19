package com.github.qingquanlv.testflow_service_api.service.impl.cazes;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.qingquanlv.testflow_service_api.common.Utils;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.Config;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.RequestCaseConfigTab;
import com.github.qingquanlv.testflow_service_api.mapper.RequestCaseConfigMapper;
import com.github.qingquanlv.testflow_service_api.service.impl.BasicCase;
import com.github.qingquanlv.testflow_service_biz.TestFlowManager;

import java.util.List;
import java.util.Map;

/**
 * @Author: qingquan.lv
 * @Date: 2023/3/6 17:54
 */
public class RequestCaze extends BasicCase {


    private final RequestCaseConfigMapper requestCaseConfigMapper;


    public RequestCaze() {
        this.requestCaseConfigMapper = null;
    }

    public RequestCaze(Object requestCaseConfigMapper) {
        this.requestCaseConfigMapper = (RequestCaseConfigMapper)requestCaseConfigMapper;
    }

    @Override
    public List<Map<String, String>> select(List<Long> caseIds) {
        List<RequestCaseConfigTab> obj = this.requestCaseConfigMapper.selectList(
            Wrappers.<RequestCaseConfigTab>lambdaQuery()
                .in(RequestCaseConfigTab::getCaseId, caseIds));
        return Utils.listToListMap(obj);
    }

    @Override
    public String execute(String publicKey, com.github.qingquanlv.testflow_service_api.entity.cases_v2.Config config) {
        TestFlowManager testFlowManager = new TestFlowManager(publicKey);
        return testFlowManager.sendRequest(config.getLabel(),
            "null".equals(config.getExec_params().get("request_body")) || null == config.getExec_params().get("request_body") ? null : config.getExec_params().get("request_body"),
            "null".equals(config.getExec_params().get("request_configs")) || null == config.getExec_params().get("request_configs") ? null : config.getExec_params().get("request_configs"),
            "null".equals(config.getExec_params().get("request_headers")) || null == config.getExec_params().get("request_headers") ? null : config.getExec_params().get("request_headers"),
            config.getExec_params().get("request_type"),
            config.getExec_params().get("content_type"),
            config.getExec_params().get("url")
        );
    }

    @Override
    public void insert(Long caseId, Config p) {
        RequestCaseConfigTab requestCaseConfig =
            RequestCaseConfigTab.builder()
                .caseId(caseId)
                .requestBody(p.getExec_params().get("request_body"))
                .requestType(p.getExec_params().get("request_type"))
                .url(p.getExec_params().get("url"))
                .contentType(p.getExec_params().get("content_type"))
                .requestConfigs(p.getExec_params().get("request_configs"))
                .requestHeaders(p.getExec_params().get("request_headers")).build();
        this.requestCaseConfigMapper.insert(requestCaseConfig);
    }

    @Override
    public void delete(List<Long> caseIds) {
        this.requestCaseConfigMapper.delete(
            Wrappers.<RequestCaseConfigTab>lambdaQuery()
                .in(RequestCaseConfigTab::getCaseId, caseIds));
    }
}
