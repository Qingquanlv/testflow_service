package com.github.qingquanlv.testflow_service_api.service.impl.cazes;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.qingquanlv.testflow_service_api.common.Utils;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.Config;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.DruidCaseConfig;
import com.github.qingquanlv.testflow_service_api.mapper.DruidCaseConfigMapper;
import com.github.qingquanlv.testflow_service_api.service.impl.BasicCase;
import com.github.qingquanlv.testflow_service_biz.TestFlowManager;

import java.util.List;
import java.util.Map;

/**
 * @Author: qingquan.lv
 * @Date: 2023/3/9 17:47
 */
public class DruidCase extends BasicCase {

    private final DruidCaseConfigMapper druidCaseConfigMapper;

    public DruidCase() {
        this.druidCaseConfigMapper = null;
    }

    public DruidCase(Object druidCaseConfigMapper) {
        this.druidCaseConfigMapper = (DruidCaseConfigMapper)druidCaseConfigMapper;
    }


    @Override
    public List<Map<String, String>> select(List<Long> caseIds) {
        List<DruidCaseConfig> obj = this.druidCaseConfigMapper.selectList(
            Wrappers.<DruidCaseConfig>lambdaQuery()
                .in(DruidCaseConfig::getCaseId, caseIds));
        return Utils.listToListMap(obj);
    }

    @Override
    public String execute(String publicKey, com.github.qingquanlv.testflow_service_api.entity.cases_v2.Config config) {
        TestFlowManager testFlowManager = new TestFlowManager(publicKey);
        String sqlStr = null == config.getExec_params() ?
            "" :
            config.getExec_params()
                .get("sql_str");
        String env = null == config.getExec_params() ?
            "" :
            config.getExec_params()
                .get("env");
        return testFlowManager.queryDataBase(
            config.getLabel(),
            env,
            sqlStr);
    }

    @Override
    public void insert(Long caseId, Config p) {
        DruidCaseConfig druidCaseConfig =
            DruidCaseConfig.builder(
            ).caseId(caseId
            ).env(p.getExec_params().get("env")
            ).sqlStr(p.getExec_params().get("sql_str")
            ).build();
        this.druidCaseConfigMapper.insert(druidCaseConfig);
    }

    @Override
    public void delete(List<Long> caseIds) {
        this.druidCaseConfigMapper.delete(
            Wrappers.<DruidCaseConfig>lambdaQuery()
                .in(DruidCaseConfig::getCaseId, caseIds));
    }
}
