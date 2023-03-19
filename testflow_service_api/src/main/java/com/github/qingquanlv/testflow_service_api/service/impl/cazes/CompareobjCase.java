package com.github.qingquanlv.testflow_service_api.service.impl.cazes;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.qingquanlv.testflow_service_api.common.Utils;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.Config;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.CompareobjCaseConfig;
import com.github.qingquanlv.testflow_service_api.mapper.CompareobjCaseConfigMapper;
import com.github.qingquanlv.testflow_service_api.service.impl.BasicCase;
import com.github.qingquanlv.testflow_service_biz.TestFlowManager;

import java.util.List;
import java.util.Map;

/**
 * @Author: qingquan.lv
 * @Date: 2023/3/9 17:55
 */
public class CompareobjCase extends BasicCase {
    private final CompareobjCaseConfigMapper compareobjCaseConfigMapper;


    public CompareobjCase() {
        this.compareobjCaseConfigMapper = null;
    }

    public CompareobjCase(Object compareobjCaseConfigMapper) {
        this.compareobjCaseConfigMapper = (CompareobjCaseConfigMapper)compareobjCaseConfigMapper;
    }


    @Override
    public List<Map<String, String>> select(List<Long> caseIds) {
        List<CompareobjCaseConfig> obj = this.compareobjCaseConfigMapper.selectList(
            Wrappers.<CompareobjCaseConfig>lambdaQuery()
                .in(CompareobjCaseConfig::getCaseId, caseIds));
        return Utils.listToListMap(obj);
    }

    @Override
    public String execute(String publicKey, com.github.qingquanlv.testflow_service_api.entity.cases_v2.Config config) {
        TestFlowManager testFlowManager = new TestFlowManager(publicKey);
        String expObj
            = null == config.getExec_params() ?
            "" :
            config.getExec_params()
                .get("expObj");
        String atlObj
            = null == config.getExec_params() ?
            "" :
            config.getExec_params()
                .get("atlObj");
        String pkMap
            = null == config.getExec_params() ?
            "" :
            config.getExec_params()
                .get("pkMap");
        String noCompareItemMap
            = null == config.getExec_params() ?
            "" :
            config.getExec_params()
                .get("noCompareItemMap");
        String thresholdMap
            = null == config.getExec_params() ?
            "" :
            config.getExec_params()
                .get("thresholdMap");
       return testFlowManager.verify(config.getLabel(),
                expObj,
                atlObj,
                pkMap,
                noCompareItemMap,
                thresholdMap);
    }

    @Override
    public void insert(Long caseId, Config p) {
        CompareobjCaseConfig compareobjCaseConfig =
            CompareobjCaseConfig.builder(
            ).caseId(caseId
            ).expObj(p.getExec_params().get("expObj")
            ).atlObj(p.getExec_params().get("atlObj")
            ).pkMap(p.getExec_params().get("pkMap")
            ).noCompareItemMap(p.getExec_params().get("noCompareItemMap")
            ).thresholdMap(p.getExec_params().get("thresholdMap")
            ).build();
        compareobjCaseConfigMapper.insert(compareobjCaseConfig);
    }

    @Override
    public void delete(List<Long> caseIds) {
        this.compareobjCaseConfigMapper.delete(
            Wrappers.<CompareobjCaseConfig>lambdaQuery()
                .in(CompareobjCaseConfig::getCaseId, caseIds));
    }
}

