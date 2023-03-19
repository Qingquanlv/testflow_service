package com.github.qingquanlv.testflow_service_api.service.impl.cazes;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.qingquanlv.testflow_service_api.common.Utils;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.Config;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.ComparepathCaseConfig;
import com.github.qingquanlv.testflow_service_api.mapper.ComparepathCaseConfigMapper;
import com.github.qingquanlv.testflow_service_api.service.impl.BasicCase;
import com.github.qingquanlv.testflow_service_biz.TestFlowManager;

import java.util.List;
import java.util.Map;

/**
 * @Author: qingquan.lv
 * @Date: 2023/3/9 18:03
 */
public class ComparepathCase extends BasicCase {

    private final ComparepathCaseConfigMapper comparepathCaseConfigMapper;

    public ComparepathCase() {
        this.comparepathCaseConfigMapper = null;
    }

    public ComparepathCase(Object comparepathCaseConfigMapper) {
        this.comparepathCaseConfigMapper = (ComparepathCaseConfigMapper)comparepathCaseConfigMapper;
    }


    @Override
    public List<Map<String, String>> select(List<Long> caseIds) {
        List<ComparepathCaseConfig> obj =
            this.comparepathCaseConfigMapper.selectList(
            Wrappers.<ComparepathCaseConfig>lambdaQuery()
                .in(ComparepathCaseConfig::getCaseId, caseIds));
        return Utils.listToListMap(obj);
    }

    @Override
    public String execute(String publicKey, com.github.qingquanlv.testflow_service_api.entity.cases_v2.Config config) {
        TestFlowManager testFlowManager = new TestFlowManager(publicKey);
        String atlObj
            = null == config.getExec_params() ?
            "" :
            config.getExec_params()
                .get("atlObj");
        String JsonFilter
            = null == config.getExec_params() ?
            "" :
            config.getExec_params()
                .get("jsonFilter");
        String expVal
            = null == config.getExec_params() ?
            "" :
            config.getExec_params()
                .get("expVal");
        return testFlowManager.verify(config.getLabel(),
                atlObj,
                JsonFilter,
                expVal);
    }

    @Override
    public void insert(Long caseId, Config p) {
        ComparepathCaseConfig comparepathCaseConfig =
            ComparepathCaseConfig.builder(
            ).caseId(caseId
            ).atlObj(p.getExec_params().get("atlObj")
            ).JsonFilter(p.getExec_params().get("jsonFilter")
            ).expVal(p.getExec_params().get("expVal")
            ).build();
        comparepathCaseConfigMapper.insert(comparepathCaseConfig);
    }

    @Override
    public void delete(List<Long> caseIds) {
        this.comparepathCaseConfigMapper.delete(
            Wrappers.<ComparepathCaseConfig>lambdaQuery()
                .in(ComparepathCaseConfig::getCaseId, caseIds));
    }
}
