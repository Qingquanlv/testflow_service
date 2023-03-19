package com.github.qingquanlv.testflow_service_api.service.impl.cazes;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.qingquanlv.testflow_service_api.common.Utils;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.Config;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.DatabaseCaseConfig;
import com.github.qingquanlv.testflow_service_api.mapper.DatabaseCaseConfigMapper;
import com.github.qingquanlv.testflow_service_api.service.impl.BasicCase;
import com.github.qingquanlv.testflow_service_biz.TestFlowManager;

import java.util.List;
import java.util.Map;

/**
 * @Author: qingquan.lv
 * @Date: 2023/3/9 17:45
 */
public class TidbCase extends BasicCase {


    private final DatabaseCaseConfigMapper databaseCaseConfigMapper;

    public TidbCase() {
        this.databaseCaseConfigMapper = null;
    }

    public TidbCase(Object databaseCaseConfigMapper) {
        this.databaseCaseConfigMapper = (DatabaseCaseConfigMapper)databaseCaseConfigMapper;
    }

    @Override
    public List<Map<String, String>> select(List<Long> caseIds) {
        List<DatabaseCaseConfig> obj = this.databaseCaseConfigMapper.selectList(
            Wrappers.<DatabaseCaseConfig>lambdaQuery()
                .in(DatabaseCaseConfig::getCaseId, caseIds));
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
        DatabaseCaseConfig databaseCaseConfig =
            DatabaseCaseConfig.builder(
            ).caseId(caseId
            ).env(p.getExec_params().get("env")
            ).sqlStr(p.getExec_params().get("sql_str")
            ).build();
        this.databaseCaseConfigMapper.insert(databaseCaseConfig);
    }

    @Override
    public void delete(List<Long> caseIds) {
        this.databaseCaseConfigMapper.delete(
            Wrappers.<DatabaseCaseConfig>lambdaQuery()
                .in(DatabaseCaseConfig::getCaseId, caseIds));
    }
}