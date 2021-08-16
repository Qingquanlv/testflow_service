package com.github.qingquanlv.testflow_service_api.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.qingquanlv.testflow_service_api.common.Constants;
import com.github.qingquanlv.testflow_service_api.common.Utils;
import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.CaseInfo;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.Config;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.Edges;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.Nodes;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.createfeature.CreateFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.createfeature.CreateFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.deletefeature.DeleteFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.execfeature.ExecFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.execfeature.ExecFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.queryallfeaure.QueryAllFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.queryallfeaure.QueryFeature;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.queryfeature.QueryFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.Parameter;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.*;
import com.github.qingquanlv.testflow_service_api.mapper.*;
import com.github.qingquanlv.testflow_service_api.service.FeatureService;
import com.github.qingquanlv.testflow_service_biz.TestFlowManager;
import com.github.qingquanlv.testflow_service_biz.utilities.FastJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/2 9:39
 * @Version 1.0
 */
@Service
public class FeatureServiceImpl implements FeatureService {

    @Autowired
    FeatureMapper featureMapper;

    @Autowired
    CazeMapper caseMapper;

    @Autowired
    FeatureCaseNextCaseMapper featureCaseNextCaseMapper;

    @Autowired
    RequestCaseConfigMapper requestCaseConfigMapper;

    @Autowired
    DatabaseCaseConfigMapper databaseCaseConfigMapper;

    @Autowired
    ParseCaseConfigMapper parseCaseConfigMapper;

    @Autowired
    ComparevalCaseConfigMapper comparevalCaseConfigMapper;

    @Autowired
    CompareobjCaseConfigMapper compareobjCaseConfigMapper;

    @Autowired
    ComparepathCaseConfigMapper comparepathCaseConfigMapper;

    @Autowired
    DruidCaseConfigMapper druidCaseConfigMapper;

    @Autowired
    TidbCaseConfigMapper tidbCaseConfigMapper;


    /**
     * 创建feature
     *
     * @param request
     * @return
     */
    @Override
    public CreateFeatureResponse createFeature(CreateFeatureRequest request) {
        CreateFeatureResponse response = new CreateFeatureResponse();
        Status status = new Status();
        status.setSuccess(true);
        Feature feature =
                Feature.builder()
                        .feature_name(request.getFeatureName())
                        .description(request.getDescription())
                        .build();
        //if feature exist, update feature
        if (null != request.getFeatureId()
                && null != featureMapper.selectById(request.getFeatureId())) {
            feature.setFeature_id(request.getFeatureId());
            featureMapper.updateById(feature);
        } else {
            featureMapper.insert(feature);
        }
        List<Caze> cases = new ArrayList<>();
        //遍历node信息，先删除在插入node和config信息
        if (!CollectionUtils.isEmpty(request.getNodes())) {
            List<Caze> CaseList = caseMapper.selectList(
                    Wrappers.<Caze>lambdaQuery()
                            .in(Caze::getFeatureId, feature.getFeature_id()));
            if (!CollectionUtils.isEmpty(CaseList)) {
                List<Long> caseIds = CaseList
                        .stream()
                        .map(Caze::getId)
                        .collect(Collectors.toList());
                requestCaseConfigMapper.delete(
                        Wrappers.<RequestCaseConfig>lambdaQuery()
                                .in(RequestCaseConfig::getCaseId, caseIds));
                parseCaseConfigMapper.delete(
                        Wrappers.<ParseCaseConfig>lambdaQuery()
                                .in(ParseCaseConfig::getCaseId, caseIds));
                databaseCaseConfigMapper.delete(
                        Wrappers.<DatabaseCaseConfig>lambdaQuery()
                                .in(DatabaseCaseConfig::getCaseId, caseIds));
                compareobjCaseConfigMapper.delete(
                        Wrappers.<CompareobjCaseConfig>lambdaQuery()
                                .in(CompareobjCaseConfig::getCaseId, caseIds));
                comparepathCaseConfigMapper.delete(
                        Wrappers.<ComparepathCaseConfig>lambdaQuery()
                                .in(ComparepathCaseConfig::getCaseId, caseIds));
                comparevalCaseConfigMapper.delete(
                        Wrappers.<ComparevalCaseConfig>lambdaQuery()
                                .in(ComparevalCaseConfig::getCaseId, caseIds));
                druidCaseConfigMapper.delete(
                        Wrappers.<DruidCaseConfig>lambdaQuery()
                                .in(DruidCaseConfig::getCaseId, caseIds));
                tidbCaseConfigMapper.delete(
                        Wrappers.<TidbCaseConfig>lambdaQuery()
                                .in(TidbCaseConfig::getCaseId, caseIds));
                //case的连线信息，直接删除
                List<Caze> cazes = caseMapper.selectList(Wrappers.<Caze>lambdaQuery()
                        .eq(Caze::getFeatureId, feature.getFeature_id()));
                Set<Long> cazesIds = cazes
                        .stream()
                        .map(Caze::getId)
                        .collect(Collectors.toSet());
                featureCaseNextCaseMapper.delete(
                        Wrappers.<FeatureCaseNextCase>lambdaQuery()
                                .in(FeatureCaseNextCase::getSourceCaseId, cazesIds)
                                .or()
                                .in(FeatureCaseNextCase::getTargetCaseId, cazesIds));
                //case数据全删全插
                caseMapper.delete(Wrappers.<Caze>lambdaQuery()
                        .eq(Caze::getFeatureId, feature.getFeature_id()));
            }
            for (Nodes node : request.getNodes()) {
                Caze featureCase =
                        Caze.builder()
                                .label(node.getLabel())
                                .featureId(feature.getFeature_id())
                                .caseType(node.getClazz())
                                .x(node.getX())
                                .y(node.getY())
                                .build();
                caseMapper.insert(featureCase);
                insertCaseConfig(request,
                        node.getClazz(),
                        featureCase.getId(),
                        featureCase.getLabel());
                cases.add(featureCase);
            }
            //插入连线信息
            if (!CollectionUtils.isEmpty(request.getEdges())) {
                for (Edges edge : request.getEdges()) {
                    //判断当前线的源节点和目标节点存在
                    if (!CollectionUtils.isEmpty(request.getNodes())
                            && cases
                            .stream()
                            .anyMatch(item ->
                                    item.getLabel().equals(edge.getSource()))
                            && cases
                            .stream()
                            .anyMatch(item ->
                                    item.getLabel().equals(edge.getSource()))) {
                        Caze sourceCase = cases.stream()
                                .filter(item -> edge.getSource().equals(item.getLabel()))
                                .findFirst().orElse(null);
                        Caze targetCase = cases.stream()
                                .filter(item -> edge.getTarget().equals(item.getLabel()))
                                .findFirst().orElse(null);
                        FeatureCaseNextCase featureCaseNextCase =
                                FeatureCaseNextCase
                                        .builder()
                                        .sourceCaseId(sourceCase.getId())
                                        .targetCaseId(targetCase.getId())
                                        .sourceanchor(edge.getSourceAnchor())
                                        .targetanchor(edge.getTargetAnchor())
                                        .build();
                        featureCaseNextCaseMapper.insert(featureCaseNextCase);
                    }
                }
            }
        }
        response.setFeatureId(null == feature ? null : feature.getFeature_id());
        response.setStatus(status);
        return response;
    }

    private void insertCaseConfig(CreateFeatureRequest request, String type, Long caseId, String caseLabel) {
        //判断相关case的配置在request中是否存在，若存在插入db
        if (!CollectionUtils.isEmpty(request.getConfig())
                && null != caseLabel
                && null != request.getConfig()
                .stream()
                .filter(item -> !caseLabel.equals(item.getLabel()))
                .findFirst().orElse(null)) {
            Config config = request.getConfig()
                    .stream()
                    .filter(item -> caseLabel.equals(item.getLabel()))
                    .findFirst().orElse(null);
            if (Constants.REQUEST.equals(type)) {
                RequestCaseConfig requestCaseConfig =
                        RequestCaseConfig.builder(
                        ).caseId(caseId
                        ).requestBody(config.getExec_params()
                                .get("request_body")
                        ).requestType(config.getExec_params()
                                .get("request_type")
                        ).url(config.getExec_params()
                                .get("url")
                        ).contentType(config.getExec_params()
                                .get("content_type")
                        ).requestConfigs(config.getExec_params()
                                .get("request_configs")
                        ).requestHeaders(config.getExec_params()
                                .get("request_headers")
                        ).build();
                requestCaseConfigMapper.insert(requestCaseConfig);
            }
            else if (Constants.PARSE.equals(type)) {
                ParseCaseConfig parseCaseConfig =
                        ParseCaseConfig.builder(
                        ).caseId(caseId
                        ).parameters(FastJsonUtil.toJson(
                                Utils.toListStr(
                                        config.getExec_params()
                                                .get("parameters")))
                        ).cvtMethodSource(config.getExec_params()
                                .get("cvt_method_source")
                        ).build();
                parseCaseConfigMapper.insert(parseCaseConfig);
            }
            else if (Constants.DATABASE.equals(type)) {
                DatabaseCaseConfig databaseCaseConfig =
                        DatabaseCaseConfig.builder(
                        ).caseId(caseId
                        ).env(config.getExec_params()
                                .get("env")
                        ).sqlStr(config.getExec_params()
                                .get("sql_str")
                        ).build();
                databaseCaseConfigMapper.insert(databaseCaseConfig);
            }
            else if (Constants.TIDB.equals(type)) {
                TidbCaseConfig tidbCaseConfig =
                        TidbCaseConfig.builder(
                        ).caseId(caseId
                        ).env(config.getExec_params()
                                .get("env")
                        ).sqlStr(config.getExec_params()
                                .get("sql_str")
                        ).build();
                tidbCaseConfigMapper.insert(tidbCaseConfig);
            }
            else if (Constants.DRUID.equals(type)) {
                DruidCaseConfig druidCaseConfig =
                        DruidCaseConfig.builder(
                        ).caseId(caseId
                        ).env(config.getExec_params()
                                .get("env")
                        ).sqlStr(config.getExec_params()
                                .get("sql_str")
                        ).build();
                druidCaseConfigMapper.insert(druidCaseConfig);
            }
            else if (Constants.COMPARE_VAL.equals(type)) {
                ComparevalCaseConfig comparepathCaseConfig =
                        ComparevalCaseConfig.builder(
                        ).caseId(caseId
                        ).expVal(config.getExec_params()
                                .get("expVal")
                        ).atlVal(config.getExec_params()
                                .get("atlVal")
                        ).build();
                comparevalCaseConfigMapper.insert(comparepathCaseConfig);
            }
            else if (Constants.COMPARE_PATH.equals(type)) {
                ComparepathCaseConfig comparepathCaseConfig =
                        ComparepathCaseConfig.builder(
                        ).caseId(caseId
                        ).atlObj(config.getExec_params()
                                .get("atlObj")
                        ).JsonFilter(config.getExec_params()
                                .get("jsonFilter")
                        ).expVal(config.getExec_params()
                                .get("expVal")
                        ).build();
                comparepathCaseConfigMapper.insert(comparepathCaseConfig);
            }
            else if (Constants.COMPARE_OBJ.equals(type)) {
                CompareobjCaseConfig compareobjCaseConfig =
                        CompareobjCaseConfig.builder(
                        ).caseId(caseId
                        ).expObj(config.getExec_params()
                                .get("expObj")
                        ).atlObj(config.getExec_params()
                                .get("atlObj")
                        ).pkMap(config.getExec_params()
                                .get("pkMap")
                        ).noCompareItemMap(config.getExec_params()
                                .get("noCompareItemMap")
                        ).build();
                compareobjCaseConfigMapper.insert(compareobjCaseConfig);
            }
        }
    }

    /**
     * 删除feature
     *
     * @param id
     * @return
     */
    @Override
    public DeleteFeatureResponse deleteFeature(Long id) {
        Status status = new Status();
        status.setSuccess(true);
        //根据featureId查询所有case
        List<Caze> featureCaseList
                = caseMapper.selectList(Wrappers.<Caze>lambdaQuery()
                .eq(Caze::getFeatureId, id));
        if (!CollectionUtils.isEmpty(featureCaseList)) {
            List<Long> caseIds = featureCaseList
                    .stream()
                    .map(Caze::getId).collect(Collectors.toList());
            requestCaseConfigMapper.delete(
                    Wrappers.<RequestCaseConfig>lambdaQuery()
                            .in(RequestCaseConfig::getCaseId, caseIds));
            parseCaseConfigMapper.delete(
                    Wrappers.<ParseCaseConfig>lambdaQuery()
                            .in(ParseCaseConfig::getCaseId, caseIds));
            databaseCaseConfigMapper.delete(
                    Wrappers.<DatabaseCaseConfig>lambdaQuery()
                            .in(DatabaseCaseConfig::getCaseId, caseIds));
            compareobjCaseConfigMapper.delete(
                    Wrappers.<CompareobjCaseConfig>lambdaQuery()
                            .in(CompareobjCaseConfig::getCaseId, caseIds));
            comparepathCaseConfigMapper.delete(
                    Wrappers.<ComparepathCaseConfig>lambdaQuery()
                            .in(ComparepathCaseConfig::getCaseId, caseIds));
            comparevalCaseConfigMapper.delete(
                    Wrappers.<ComparevalCaseConfig>lambdaQuery()
                            .in(ComparevalCaseConfig::getCaseId, caseIds));
            druidCaseConfigMapper.delete(
                    Wrappers.<DruidCaseConfig>lambdaQuery()
                            .in(DruidCaseConfig::getCaseId, caseIds));
            tidbCaseConfigMapper.delete(
                    Wrappers.<TidbCaseConfig>lambdaQuery()
                            .in(TidbCaseConfig::getCaseId, caseIds));
            featureCaseNextCaseMapper.delete(
                    Wrappers.<FeatureCaseNextCase>lambdaQuery()
                            .in(FeatureCaseNextCase::getSourceCaseId, caseIds));
            caseMapper.delete(
                    Wrappers.<Caze>lambdaQuery()
                            .in(Caze::getId, caseIds));
        }
        featureMapper.delete(
                Wrappers.<Feature>lambdaQuery()
                        .eq(Feature::getFeature_id, id));
        DeleteFeatureResponse rsp
                = DeleteFeatureResponse.builder()
                .status(status)
                .build();
        return rsp;
    }

    /**
     * 同步执行feature
     *
     * @param request
     * @return
     */
    @Override
    public ExecFeatureResponse execFeature(ExecFeatureRequest request) {
        Status status = new Status();
        status.setSuccess(true);
        List<Caze> featureCases
                = caseMapper.selectList(
                Wrappers.<Caze>lambdaQuery()
                        .eq(Caze::getFeatureId, request.getFeatureId()));
        List<Parameter> parameters = new ArrayList<>();
        //输入参数
        if (!CollectionUtils.isEmpty(request.getParameters())) {
            for (String key : request.getParameters().keySet()) {
                Parameter parameter = new Parameter();
                parameter.setParameter_key(key);
                parameter.setParameter_value(request.getParameters().get(key));
                parameters.add(parameter);
            }
        }
        //执行feature
        TaskResult taskResult
                = execFeature(parameters, featureCases);
        ExecFeatureResponse rsp
                = ExecFeatureResponse.builder()
                .info(FastJsonUtil.toListBean(
                        taskResult.getLogs(),
                        CaseInfo.class))
                .parameters(request.getParameters())
                .status(status).build();
        return rsp;
    }

    @Override
    public QueryFeatureResponse getFeature(Long id) {
        Feature feature = featureMapper.selectById(id);
        //根据featureId查询所有case
        List<Caze> featureCaseList
                = caseMapper.selectList(Wrappers.<Caze>lambdaQuery()
                .eq(Caze::getFeatureId, id));
        List<Nodes> nodes = new ArrayList<>();
        if (!CollectionUtils.isEmpty(featureCaseList)) {
            for (Caze featureCase : featureCaseList) {
                Nodes node = Nodes.builder()
                        .id(featureCase.getId())
                        .label(featureCase.getLabel())
                        .clazz(featureCase.getCaseType())
                        .x(featureCase.getX())
                        .y(featureCase.getY())
                        .build();
                nodes.add(node);
            }
        }
        Set<Long> caseIds = nodes
                .stream()
                .map(Nodes::getId).collect(Collectors.toSet());
        List<Config> configs = new ArrayList<>();
        List<Edges> edges = new ArrayList<>();

        if (!CollectionUtils.isEmpty(caseIds)) {
            //根据caseId查询所有caseConfig
            List<RequestCaseConfig> requestCaseConfigs
                    = requestCaseConfigMapper.selectList(
                    Wrappers.<RequestCaseConfig>lambdaQuery()
                    .in(RequestCaseConfig::getCaseId, caseIds));
            if (!CollectionUtils.isEmpty(requestCaseConfigs)) {
                for (RequestCaseConfig requestCaseConfig : requestCaseConfigs) {
                    HashMap<String, String> params = new HashMap<>();
                    Nodes node = nodes
                            .stream()
                            .filter(item->requestCaseConfig.getCaseId().equals(item.getId()))
                            .findFirst().orElse(null);
                    params.put("url", requestCaseConfig.getUrl());
                    params.put("request_body", requestCaseConfig.getRequestBody());
                    params.put("request_type",requestCaseConfig.getRequestType());
                    params.put("content_type", requestCaseConfig.getContentType());
                    params.put("request_configs", requestCaseConfig.getRequestConfigs());
                    params.put("request_headers", requestCaseConfig.getRequestHeaders());
                    Config config = Config.builder()
                            .id(null == node ? null : node.getId())
                            .label(null == node ? "" : node.getLabel())
                            .exec_params(params)
                            .build();
                    configs.add(config);
                }
            }
            List<DatabaseCaseConfig> databaseCaseConfigs
                    = databaseCaseConfigMapper.selectList(
                    Wrappers.<DatabaseCaseConfig>lambdaQuery()
                            .in(DatabaseCaseConfig::getCaseId, caseIds));
            if (!CollectionUtils.isEmpty(databaseCaseConfigs)) {
                for (DatabaseCaseConfig databaseCaseConfig : databaseCaseConfigs) {
                    HashMap<String, String> params = new HashMap<>();
                    Nodes node = nodes
                            .stream()
                            .filter(item->databaseCaseConfig.getCaseId().equals(item.getId()))
                            .findFirst().orElse(null);
                    params.put("env", databaseCaseConfig.getEnv());
                    params.put("sql_str", databaseCaseConfig.getSqlStr());
                    Config config = Config.builder()
                            .id(null == node ? null : node.getId())
                            .label(null == node ? "" : node.getLabel())
                            .exec_params(params)
                            .build();
                    configs.add(config);
                }
            }
            List<ParseCaseConfig> parseCaseConfigs
                    = parseCaseConfigMapper.selectList(
                    Wrappers.<ParseCaseConfig>lambdaQuery()
                            .in(ParseCaseConfig::getCaseId, caseIds));
            if (!CollectionUtils.isEmpty(parseCaseConfigs)) {
                for (ParseCaseConfig parseCaseConfig : parseCaseConfigs) {
                    HashMap<String, String> params = new HashMap<>();
                    Nodes node = nodes
                            .stream()
                            .filter(item->parseCaseConfig.getCaseId().equals(item.getId()))
                            .findFirst().orElse(null);
                    params.put("parameters", Utils.listToStr(FastJsonUtil.toList(parseCaseConfig.getParameters())));
                    params.put("cvt_method_source", parseCaseConfig.getCvtMethodSource());
                    Config config = Config.builder()
                            .id(null == node ? null : node.getId())
                            .label(null == node ? "" : node.getLabel())
                            .exec_params(params)
                            .build();
                    configs.add(config);
                }
            }
            List<TidbCaseConfig> tidbCaseConfigs
                    = tidbCaseConfigMapper.selectList(
                    Wrappers.<TidbCaseConfig>lambdaQuery()
                            .in(TidbCaseConfig::getCaseId, caseIds));
            if (!CollectionUtils.isEmpty(tidbCaseConfigs)) {
                for (TidbCaseConfig tidbCaseConfig : tidbCaseConfigs) {
                    HashMap<String, String> params = new HashMap<>();
                    Nodes node = nodes
                            .stream()
                            .filter(item->tidbCaseConfig.getCaseId().equals(item.getId()))
                            .findFirst().orElse(null);
                    params.put("env", tidbCaseConfig.getEnv());
                    params.put("sql_str", tidbCaseConfig.getSqlStr());
                    Config config = Config.builder()
                            .id(null == node ? null : node.getId())
                            .label(null == node ? "" : node.getLabel())
                            .exec_params(params)
                            .build();
                    configs.add(config);
                }
            }
            List<DruidCaseConfig> druidCaseConfigs
                    = druidCaseConfigMapper.selectList(
                    Wrappers.<DruidCaseConfig>lambdaQuery()
                            .in(DruidCaseConfig::getCaseId, caseIds));
            if (!CollectionUtils.isEmpty(druidCaseConfigs)) {
                for (DruidCaseConfig druidCaseConfig : druidCaseConfigs) {
                    HashMap<String, String> params = new HashMap<>();
                    Nodes node = nodes
                            .stream()
                            .filter(item->druidCaseConfig.getCaseId().equals(item.getId()))
                            .findFirst().orElse(null);
                    params.put("env", druidCaseConfig.getEnv());
                    params.put("sql_str", druidCaseConfig.getSqlStr());
                    Config config = Config.builder()
                            .id(null == node ? null : node.getId())
                            .label(null == node ? "" : node.getLabel())
                            .exec_params(params)
                            .build();
                    configs.add(config);
                }
            }
            List<ComparevalCaseConfig> comparevalCaseConfigs
                    = comparevalCaseConfigMapper.selectList(
                    Wrappers.<ComparevalCaseConfig>lambdaQuery()
                            .in(ComparevalCaseConfig::getCaseId, caseIds));
            if (!CollectionUtils.isEmpty(comparevalCaseConfigs)) {
                for (ComparevalCaseConfig comparevalCaseConfig : comparevalCaseConfigs) {
                    HashMap<String, String> params = new HashMap<>();
                    Nodes node = nodes
                            .stream()
                            .filter(item->comparevalCaseConfig.getCaseId().equals(item.getId()))
                            .findFirst().orElse(null);
                    params.put("expVal", comparevalCaseConfig.getExpVal());
                    params.put("atlVal", comparevalCaseConfig.getAtlVal());
                    Config config = Config.builder()
                            .id(null == node ? null : node.getId())
                            .label(null == node ? "" : node.getLabel())
                            .exec_params(params)
                            .build();
                    configs.add(config);
                }
            }
            List<ComparepathCaseConfig> comparepathCaseConfigs
                    = comparepathCaseConfigMapper.selectList(
                    Wrappers.<ComparepathCaseConfig>lambdaQuery()
                            .in(ComparepathCaseConfig::getCaseId, caseIds));
            if (!CollectionUtils.isEmpty(comparepathCaseConfigs)) {
                for (ComparepathCaseConfig comparepathCaseConfig : comparepathCaseConfigs) {
                    HashMap<String, String> params = new HashMap<>();
                    Nodes node = nodes
                            .stream()
                            .filter(item->comparepathCaseConfig.getCaseId().equals(item.getId()))
                            .findFirst().orElse(null);
                    params.put("atlObj", comparepathCaseConfig.getAtlObj());
                    params.put("jsonFilter", comparepathCaseConfig.getJsonFilter());
                    params.put("expVal", comparepathCaseConfig.getExpVal());
                    Config config = Config.builder()
                            .id(null == node ? null : node.getId())
                            .label(null == node ? "" : node.getLabel())
                            .exec_params(params)
                            .build();
                    configs.add(config);
                }
            }
            List<CompareobjCaseConfig> compareobjCaseConfigs
                    = compareobjCaseConfigMapper.selectList(
                    Wrappers.<CompareobjCaseConfig>lambdaQuery()
                            .in(CompareobjCaseConfig::getCaseId, caseIds));
            if (!CollectionUtils.isEmpty(compareobjCaseConfigs)) {
                for (CompareobjCaseConfig compareobjCaseConfig : compareobjCaseConfigs) {
                    HashMap<String, String> params = new HashMap<>();
                    Nodes node = nodes
                            .stream()
                            .filter(item->compareobjCaseConfig.getCaseId().equals(item.getId()))
                            .findFirst().orElse(null);
                    params.put("expObj", compareobjCaseConfig.getExpObj());
                    params.put("atlObj", compareobjCaseConfig.getAtlObj());
                    params.put("pkMap", compareobjCaseConfig.getPkMap());
                    params.put("noCompareItemMap", compareobjCaseConfig.getNoCompareItemMap());
                    Config config = Config.builder()
                            .id(null == node ? null : node.getId())
                            .label(null == node ? "" : node.getLabel())
                            .exec_params(params)
                            .build();
                    configs.add(config);
                }
            }
            //根据featureId查询所有连线
            List<FeatureCaseNextCase> featureCaseNextCases
                    = featureCaseNextCaseMapper.selectList(
                    Wrappers.<FeatureCaseNextCase>lambdaQuery()
                            .in(FeatureCaseNextCase::getSourceCaseId, caseIds));
            if (!CollectionUtils.isEmpty(featureCaseNextCases)) {
                for (FeatureCaseNextCase featureCaseNextCase : featureCaseNextCases) {
                    Edges edge = Edges.builder()
                            .source(String.valueOf(featureCaseNextCase.getSourceCaseId()))
                            .target(String.valueOf(featureCaseNextCase.getTargetCaseId()))
                            .sourceAnchor(featureCaseNextCase.getSourceanchor())
                            .targetAnchor(featureCaseNextCase.getTargetanchor()).build();
                    edges.add(edge);
                }
            }
        }
        QueryFeatureResponse rsp
                = QueryFeatureResponse.builder()
                .featureId(id)
                .featureName(null == feature ? "" : feature.getFeature_name())
                .description(null == feature ? "" : feature.getDescription())
                .config(configs)
                .edges(edges)
                .nodes(nodes)
                .build();
        return rsp;
    }

    @Override
    public QueryAllFeatureResponse getFeatureAll() {
        Status status = new Status();
        status.setSuccess(true);
        List<Feature> features
                = featureMapper.selectList(Wrappers.emptyWrapper());
        List<QueryFeature> queryFeatures = new ArrayList<>();
        for (Feature feature : features) {
            QueryFeature queryFeature
                    = QueryFeature.builder()
                    .feature_id(feature.getFeature_id())
                    .featureName(feature.getFeature_name())
                    .description(feature.getDescription()).build();
            queryFeatures.add(queryFeature);
        }
        QueryAllFeatureResponse rsp =
            QueryAllFeatureResponse.builder()
                    .status(status)
                    .features(queryFeatures).build();
        return rsp;
    }

    /**
     * 执行feature方法
     *
     * @param parameters
     * @param featureCaseList
     */
    public TaskResult execFeature(List<Parameter> parameters, List<Caze> featureCaseList) {
        //buffer 公共key
        String publicKey = Utils.hashKeyForDisk(String.format("%s%s",
                System.currentTimeMillis(),
                UUID.randomUUID().toString()));
        TestFlowManager testFlowManager = new TestFlowManager(publicKey);
        //输入参数
        if (!CollectionUtils.isEmpty(parameters)) {
            for (Parameter item : parameters) {
                testFlowManager.addBuffer(item.getParameter_key(), item.getParameter_value());
            }
        }
        //储存入度
        Map<Long, Integer> inDegreeMap = new HashMap<>();
        initMap(featureCaseList, inDegreeMap);
        //主要流程
        Queue<Long> s1 = new ArrayDeque<>();
        Set<Long> idSet = featureCaseList.stream().map(Caze::getId).collect(Collectors.toSet());
        //获取入度为0的case, 并且插入队列
        idSet.removeAll(inDegreeMap.keySet());
        for(Long item : idSet) {
            s1.offer(item);
        }
        //遍历执行入度为0的case
        while(!s1.isEmpty())
        {
            //抛出队头，并且执行
            Long n = s1.poll();
            Caze featureCase = featureCaseList
                    .stream()
                    .filter(item->item.getId().equals(n))
                    .findFirst().orElse(null);
            if (null == featureCase) {
                break;
            }
            //执行测试用例
            executeCase(testFlowManager, publicKey, featureCase);
            List<FeatureCaseNextCase> nextCaseKeys
                    = featureCaseNextCaseMapper.selectList(
                            Wrappers.<FeatureCaseNextCase>lambdaQuery()
                            .in(FeatureCaseNextCase::getSourceCaseId, featureCase.getId()));
            for (FeatureCaseNextCase caseKey : nextCaseKeys) {
                //入度减一
                inDegreeMap.put(caseKey.getTargetCaseId(),
                        null==inDegreeMap.get(caseKey.getTargetCaseId())
                                ? 0
                                : inDegreeMap.get(caseKey.getTargetCaseId()) - 1);
                //如果入度为0
                if(inDegreeMap.get(caseKey.getTargetCaseId()) == 0){
                    s1.offer(caseKey.getTargetCaseId());
                }
            }
        }
        Integer featureStatus = 1;
        List<CaseInfo> caseInfoList = new ArrayList<>();
        for (Caze caze : featureCaseList) {
            CaseInfo caseInfo = new CaseInfo();
            caseInfo.setClazz(caze.getCaseType());
            caseInfo.setConfig(testFlowManager
                            .getBuffer(String.format("config$%s",
                                    caze.getLabel())));
            caseInfo.setLabel(caze.getLabel());
            caseInfo.setData(testFlowManager
                            .getBuffer(caze.getLabel()));
            caseInfo.setStatus(null == testFlowManager
                    .getBuffer(String.format("status$%s",
                            caze.getLabel()))
                    ? 0
                    : Integer.parseInt(testFlowManager
                    .getBuffer(String.format("status$%s",
                            caze.getLabel()))));
            featureStatus = caseInfo.getStatus()
                    & featureStatus;
            caseInfoList.add(caseInfo);
        }
        TaskResult taskResult
                = TaskResult.builder()
                .status(featureStatus)
                .logs(FastJsonUtil.toJson(caseInfoList))
                .build();
        testFlowManager.deposed();
        return taskResult;
    }

    /**
     * 生成入度Map
     *
     * @param featureCaseList
     * @param inDegreeMap
     */
    private void initMap(List<Caze> featureCaseList, Map<Long, Integer> inDegreeMap) {
        for (Caze featureCase : featureCaseList) {
            List<FeatureCaseNextCase> nextCaseKeys
                    = featureCaseNextCaseMapper.selectList(
                            Wrappers.<FeatureCaseNextCase>lambdaQuery()
                                    .in(FeatureCaseNextCase::getSourceCaseId, featureCase.getId()));
            for (FeatureCaseNextCase caseKey : nextCaseKeys) {
                inDegreeMap.put(caseKey.getTargetCaseId(),
                        null == inDegreeMap.get(caseKey.getTargetCaseId())
                                ? 1
                                : inDegreeMap.get(caseKey.getTargetCaseId()) + 1);
            }
        }
    }

    /**
     * 执行测试用例方法
     *
     * @param testFlowManager
     * @param publicKey
     * @param featureCase
     */
    private void executeCase(TestFlowManager testFlowManager, String publicKey, Caze featureCase) {
        //忽略大小写
        switch (featureCase.getCaseType().toLowerCase()) {
            case Constants.DATABASE: {
                DatabaseCaseConfig databaseCaseConfig
                        = databaseCaseConfigMapper.selectOne(
                                Wrappers.<DatabaseCaseConfig>lambdaQuery()
                                .eq(DatabaseCaseConfig::getCaseId, featureCase.getId()));
                testFlowManager.queryDataBase(
                        featureCase.getLabel(),
                        databaseCaseConfig.getEnv(),
                        databaseCaseConfig.getSqlStr());
                break;
            }
            case Constants.TIDB: {
                DatabaseCaseConfig databaseCaseConfig
                        = databaseCaseConfigMapper.selectOne(
                        Wrappers.<DatabaseCaseConfig>lambdaQuery()
                                .eq(DatabaseCaseConfig::getCaseId, featureCase.getId()));
                testFlowManager.queryTIDB(
                        featureCase.getLabel(),
                        databaseCaseConfig.getEnv(),
                        databaseCaseConfig.getSqlStr());
                break;
            }
            case Constants.DRUID: {
                DatabaseCaseConfig databaseCaseConfig
                        = databaseCaseConfigMapper.selectOne(
                        Wrappers.<DatabaseCaseConfig>lambdaQuery()
                                .eq(DatabaseCaseConfig::getCaseId, featureCase.getId()));
                testFlowManager.queryDruid(
                        featureCase.getLabel(),
                        databaseCaseConfig.getEnv(),
                        databaseCaseConfig.getSqlStr());
                break;
            }
            case Constants.PARSE: {
                ParseCaseConfig parseCaseConfig
                        = parseCaseConfigMapper.selectOne(
                        Wrappers.<ParseCaseConfig>lambdaQuery()
                                .eq(ParseCaseConfig::getCaseId, featureCase.getId()));
                testFlowManager.sourceParse(featureCase.getLabel(),
                        parseCaseConfig.getCvtMethodSource(),
                        FastJsonUtil.toList(parseCaseConfig.getParameters()));
                break;
            }
            case Constants.REQUEST: {
                RequestCaseConfig requestCaseConfig
                        = requestCaseConfigMapper.selectOne(
                        Wrappers.<RequestCaseConfig>lambdaQuery()
                                .eq(RequestCaseConfig::getCaseId, featureCase.getId()));
                testFlowManager.sendRequest(featureCase.getLabel(),
                        "null".equals(requestCaseConfig.getRequestBody()) || null == requestCaseConfig.getRequestBody() ? null : requestCaseConfig.getRequestBody(),
                        "null".equals(requestCaseConfig.getRequestConfigs()) || null == requestCaseConfig.getRequestConfigs() ? null : FastJsonUtil.toMap(requestCaseConfig.getRequestConfigs()),
                        "null".equals(requestCaseConfig.getRequestHeaders()) || null == requestCaseConfig.getRequestHeaders() ? null : FastJsonUtil.toMap(requestCaseConfig.getRequestHeaders()),
                        requestCaseConfig.getRequestType(),
                        requestCaseConfig.getContentType(),
                        requestCaseConfig.getUrl());
                break;
            }
            case Constants.COMPARE_VAL: {
                ComparevalCaseConfig comparevalCaseConfig
                        = comparevalCaseConfigMapper.selectOne(
                        Wrappers.<ComparevalCaseConfig>lambdaQuery()
                                .eq(ComparevalCaseConfig::getCaseId, featureCase.getId()));

                testFlowManager.verify(featureCase.getLabel(),
                        comparevalCaseConfig.getExpVal(),
                        comparevalCaseConfig.getAtlVal());
                break;
            }
            case Constants.COMPARE_PATH: {
                ComparepathCaseConfig comparepathCaseConfig
                        = comparepathCaseConfigMapper.selectOne(
                        Wrappers.<ComparepathCaseConfig>lambdaQuery()
                                .eq(ComparepathCaseConfig::getCaseId, featureCase.getId()));
                testFlowManager.verify(featureCase.getLabel(),
                        comparepathCaseConfig.getAtlObj(),
                        comparepathCaseConfig.getJsonFilter(),
                        comparepathCaseConfig.getExpVal());
                break;
            }
            case Constants.COMPARE_OBJ: {
                CompareobjCaseConfig compareobjCaseConfig
                        = compareobjCaseConfigMapper.selectOne(
                        Wrappers.<CompareobjCaseConfig>lambdaQuery()
                                .eq(CompareobjCaseConfig::getCaseId, featureCase.getId()));
                testFlowManager.verify(featureCase.getLabel(),
                        compareobjCaseConfig.getExpObj(),
                        compareobjCaseConfig.getAtlObj(),
                        compareobjCaseConfig.getPkMap(),
                        compareobjCaseConfig.getNoCompareItemMap());
                break;
            }
            default: {}
        }
    }
}
