package com.github.qingquanlv.testflow_service_api.service.impl.cazes;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.qingquanlv.testflow_service_api.common.Utils;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.Config;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.ForConditionCaseConfig;
import com.github.qingquanlv.testflow_service_api.mapper.ForConditionCaseConfigMapper;
import com.github.qingquanlv.testflow_service_api.service.impl.BasicCase;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @Author: qingquan.lv
 * @Date: 2023/3/9 18:22
 */
public class ForConditionCase extends BasicCase {
    private final ForConditionCaseConfigMapper forConditionCaseConfigMapper;


    public ForConditionCase() {
        this.forConditionCaseConfigMapper = null;
    }

    public ForConditionCase(Object forConditionCaseConfigMapper) {
        this.forConditionCaseConfigMapper = (ForConditionCaseConfigMapper)forConditionCaseConfigMapper;
    }


    @Override
    public List<Map<String, String>> select(List<Long> caseIds) {
        List<ForConditionCaseConfig> obj
            = forConditionCaseConfigMapper.selectList(
            Wrappers.<ForConditionCaseConfig>lambdaQuery()
                .in(ForConditionCaseConfig::getCaseId, caseIds));
        return Utils.listToListMap(obj);
    }

    @Override
    public String execute(String publicKey, com.github.qingquanlv.testflow_service_api.entity.cases_v2.Config config) {
        return "";
    }

    @Override
    public void insert(Long caseId, Config p) {
        ForConditionCaseConfig forConditionCaseConfig =
            ForConditionCaseConfig.builder(
            ).caseId(caseId
            ).path(p.getExec_params().get("path")
            ).operator(p.getExec_params().get("operator")
            ).value(p.getExec_params().get("value")
            ).maxLoop(StringUtils.isEmpty(
                p.getExec_params().get("maxLoop")) ? 0L
                : Long.parseLong(p.getExec_params().get("maxLoop"))
            ).waitTime(StringUtils.isEmpty(
                p.getExec_params().get("waitTime")) ? 0L
                : Long.parseLong(p.getExec_params().get("waitTime"))
            ).loopNodeName(p.getExec_params().get("loopNodeName")
            ).nextNodeName(p.getExec_params().get("nextNodeName")
            ).build();
        forConditionCaseConfigMapper.insert(forConditionCaseConfig);
    }

    @Override
    public void delete(List<Long> caseIds) {
        this.forConditionCaseConfigMapper.delete(
            Wrappers.<ForConditionCaseConfig>lambdaQuery()
                .in(ForConditionCaseConfig::getCaseId, caseIds));
    }
}



