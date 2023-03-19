package com.github.qingquanlv.testflow_service_api.service.impl.cazes;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.qingquanlv.testflow_service_api.common.Utils;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.Config;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.ParseCaseConfig;
import com.github.qingquanlv.testflow_service_api.mapper.ParseCaseConfigMapper;
import com.github.qingquanlv.testflow_service_api.service.impl.BasicCase;
import com.github.qingquanlv.testflow_service_biz.TestFlowManager;
import com.github.qingquanlv.testflow_service_biz.utilities.FastJsonUtil;

import java.util.List;
import java.util.Map;

/**
 * @Author: qingquan.lv
 * @Date: 2023/3/9 18:08
 */
public class ParseCase extends BasicCase {

    private final ParseCaseConfigMapper parseCaseConfigMapper;

    public ParseCase() {
        this.parseCaseConfigMapper = null;
    }

    public ParseCase(Object parseCaseConfigMapper) {
        this.parseCaseConfigMapper = (ParseCaseConfigMapper)parseCaseConfigMapper;
    }

    @Override
    public List<Map<String, String>> select(List<Long> caseIds) {
        List<ParseCaseConfig> obj
            = parseCaseConfigMapper.selectList(
            Wrappers.<ParseCaseConfig>lambdaQuery()
                .in(ParseCaseConfig::getCaseId, caseIds));
        return Utils.listToListMap(obj);
    }

    @Override
    public String execute(String publicKey, com.github.qingquanlv.testflow_service_api.entity.cases_v2.Config config) {
        TestFlowManager testFlowManager = new TestFlowManager(publicKey);
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
        return testFlowManager.sourceParse(config.getLabel(),
            convertMethodSource,
            Utils.toListStr(params));
    }

    @Override
    public void insert(Long caseId, Config p) {
        ParseCaseConfig parseCaseConfig =
            ParseCaseConfig.builder(
            ).caseId(caseId
            ).parameters(FastJsonUtil.toJson(
                Utils.toListStr(p.getExec_params().get("parameters")))
            ).cvtMethodSource(p.getExec_params().get("cvt_method_source")
            ).build();
        parseCaseConfigMapper.insert(parseCaseConfig);
    }

    @Override
    public void delete(List<Long> caseIds) {
        this.parseCaseConfigMapper.delete(
            Wrappers.<ParseCaseConfig>lambdaQuery()
                .in(ParseCaseConfig::getCaseId, caseIds));
    }
}

