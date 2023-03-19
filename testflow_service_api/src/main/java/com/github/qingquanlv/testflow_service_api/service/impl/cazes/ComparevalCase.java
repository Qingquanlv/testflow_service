package com.github.qingquanlv.testflow_service_api.service.impl.cazes;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.qingquanlv.testflow_service_api.common.Utils;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.Config;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.ComparevalCaseConfig;
import com.github.qingquanlv.testflow_service_api.mapper.ComparevalCaseConfigMapper;
import com.github.qingquanlv.testflow_service_api.service.impl.BasicCase;
import com.github.qingquanlv.testflow_service_biz.TestFlowManager;

import java.util.List;
import java.util.Map;

/**
 * @Author: qingquan.lv
 * @Date: 2023/3/9 17:49
 */
public class ComparevalCase extends BasicCase {

    private final ComparevalCaseConfigMapper comparevalCaseConfigMapper;

    public ComparevalCase() {
        this.comparevalCaseConfigMapper = null;
    }

    public ComparevalCase(Object comparevalCaseConfigMapper) {
        this.comparevalCaseConfigMapper = (ComparevalCaseConfigMapper)comparevalCaseConfigMapper;
    }

    @Override
    public List<Map<String, String>> select(List<Long> caseIds) {
        List<ComparevalCaseConfig> obj = this.comparevalCaseConfigMapper.selectList(
            Wrappers.<ComparevalCaseConfig>lambdaQuery()
                .in(ComparevalCaseConfig::getCaseId, caseIds));
        return Utils.listToListMap(obj);
    }

    @Override
    public String execute(String publicKey, com.github.qingquanlv.testflow_service_api.entity.cases_v2.Config config) {
        TestFlowManager testFlowManager = new TestFlowManager(publicKey);
        String expVal = null == config.getExec_params() ?
            "" :
            config.getExec_params()
                .get("expVal");
        String atlVal
            = null == config.getExec_params() ?
            "" :
            config.getExec_params()
                .get("atlVal");
        return testFlowManager.verify(config.getLabel(),
                expVal,
                atlVal);
    }

    @Override
    public void insert(Long caseId, Config p) {
        ComparevalCaseConfig comparepathCaseConfig =
            ComparevalCaseConfig.builder(
            ).caseId(caseId
            ).expVal(p.getExec_params().get("expVal")
            ).atlVal(p.getExec_params().get("atlVal")
            ).build();
        this.comparevalCaseConfigMapper.insert(comparepathCaseConfig);
    }

    @Override
    public void delete(List<Long> caseIds) {
        this.comparevalCaseConfigMapper.delete(
            Wrappers.<ComparevalCaseConfig>lambdaQuery()
                .in(ComparevalCaseConfig::getCaseId, caseIds));
    }
}
