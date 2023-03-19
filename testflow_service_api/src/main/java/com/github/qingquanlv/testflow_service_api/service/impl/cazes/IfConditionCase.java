package com.github.qingquanlv.testflow_service_api.service.impl.cazes;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.qingquanlv.testflow_service_api.common.Utils;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.Config;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.IfConditionCaseConfig;
import com.github.qingquanlv.testflow_service_api.mapper.IfConditionCaseConfigMapper;
import com.github.qingquanlv.testflow_service_api.service.impl.BasicCase;

import java.util.List;
import java.util.Map;

/**
 * @Author: qingquan.lv
 * @Date: 2023/3/9 18:17
 */
public class IfConditionCase extends BasicCase {

    private final IfConditionCaseConfigMapper ifConditionCaseConfigMapper;

    public IfConditionCase() {
        this.ifConditionCaseConfigMapper = null;
    }

    public IfConditionCase(Object ifConditionCaseConfigMapper) {
        this.ifConditionCaseConfigMapper = (IfConditionCaseConfigMapper)ifConditionCaseConfigMapper;
    }

    @Override
    public List<Map<String, String>> select(List<Long> caseIds) {
        List<IfConditionCaseConfig> obj
            = ifConditionCaseConfigMapper.selectList(
            Wrappers.<IfConditionCaseConfig>lambdaQuery()
                .in(IfConditionCaseConfig::getCaseId, caseIds));
        return Utils.listToListMap(obj);
    }

    @Override
    public String execute(String publicKey, com.github.qingquanlv.testflow_service_api.entity.cases_v2.Config config) {
        return "";
    }

    @Override
    public void insert(Long caseId, Config p) {
        IfConditionCaseConfig ifConditionCaseConfig =
            IfConditionCaseConfig.builder(
            ).caseId(caseId
            ).path(p.getExec_params().get("path")
            ).operator(p.getExec_params().get("operator")
            ).value(p.getExec_params().get("value")
            ).trueNodeName(p.getExec_params().get("trueNodeName")
            ).falseNodeName(p.getExec_params().get("falseNodeName")
            ).build();
        ifConditionCaseConfigMapper.insert(ifConditionCaseConfig);
    }

    @Override
    public void delete(List<Long> caseIds) {
        this.ifConditionCaseConfigMapper.delete(
            Wrappers.<IfConditionCaseConfig>lambdaQuery()
                .in(IfConditionCaseConfig::getCaseId, caseIds));
    }
}


