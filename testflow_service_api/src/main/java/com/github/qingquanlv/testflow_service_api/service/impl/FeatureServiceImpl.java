package com.github.qingquanlv.testflow_service_api.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.qingquanlv.testflow_service_api.common.Constants;
import com.github.qingquanlv.testflow_service_api.common.Utils;
import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.feature.execfeature.ExecFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature.execfeature.ExecFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.Config;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.Edges;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.Nodes;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.createfeature.CreateFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.createfeature.CreateFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.deletefeature.DeleteFeatureResponse;
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
    VerificationCaseConfigMapper verificationCaseConfigMapper;

    @Autowired
    ParameterMapper parameterMapper;

    @Autowired
    TaskMapper taskMapper;

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
        //遍历node节点，如果存在id和type相同节点则更新
        if (!CollectionUtils.isEmpty(request.getNodes())) {
            for (Nodes node : request.getNodes()) {
                Caze featureCase =
                        Caze.builder()
                                .label(node.getLabel())
                                .featureId(feature.getFeature_id())
                                .caseType(node.getClazz())
                                .x(node.getX())
                                .y(node.getY())
                                .build();
                //判断caseId在db中存在，更新数据
                if (null != node.getId()
                        && !CollectionUtils.isEmpty(
                        caseMapper.selectList(
                                Wrappers.<Caze>lambdaQuery()
                                        .eq(Caze::getId, node.getId())))) {
                    featureCase.setId(node.getId());
                    caseMapper.updateById(featureCase);
                    insertCaseConfig(request,
                            node.getClazz(),
                            featureCase.getId(),
                            featureCase.getLabel());
                } else {
                    caseMapper.insert(featureCase);
                    insertCaseConfig(request,
                            node.getClazz(),
                            featureCase.getId(),
                            featureCase.getLabel());
                }
                cases.add(featureCase);
                //case的下一节点，直接删除并且重新insert
                featureCaseNextCaseMapper.delete(
                        Wrappers.<FeatureCaseNextCase>lambdaQuery()
                                .eq(FeatureCaseNextCase::getSourceCaseId, featureCase.getId()));
            }
        }
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
                            .filter(item->edge.getSource().equals(item.getLabel()))
                            .findFirst().orElse(null);
                    Caze targetCase = cases.stream()
                            .filter(item->edge.getTarget().equals(item.getLabel()))
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
                    .filter(item -> !caseLabel.equals(item.getLabel()))
                    .findFirst().orElse(null);
            if ("request".equals(type)) {
                RequestCaseConfig requestCaseConfig =
                        RequestCaseConfig.builder(
                        ).caseId(caseId
                        ).requestBody(config.getParams()
                                .get("request_body")
                        ).requestType(config.getParams()
                                .get("request_type")
                        ).url(config.getParams()
                                .get("url")
                        ).contentType(config.getParams()
                                .get("content_type")
                        ).requestConfigs(config.getParams()
                                .get("request_configs")
                        ).requestHeaders(config.getParams()
                                .get("request_headers")
                        ).build();
                requestCaseConfigMapper.delete(
                        Wrappers.<RequestCaseConfig>lambdaQuery()
                                .eq(RequestCaseConfig::getCaseId, caseId));
                requestCaseConfigMapper.insert(requestCaseConfig);
            }
            else if ("parse".equals(type)) {
                ParseCaseConfig parseCaseConfig =
                        ParseCaseConfig.builder(
                        ).caseId(caseId
                        ).parameters(config.getParams()
                                .get("parameters")
                        ).cvtMethodSource(config.getParams()
                                .get("cvt_method_source")
                        ).build();
                parseCaseConfigMapper.delete(
                        Wrappers.<ParseCaseConfig>lambdaQuery()
                                .eq(ParseCaseConfig::getCaseId, caseId));
                parseCaseConfigMapper.insert(parseCaseConfig);
            }
            else if ("database".equals(type)) {
                DatabaseCaseConfig databaseCaseConfig =
                        DatabaseCaseConfig.builder(
                        ).caseId(caseId
                        ).sqlStr(config.getParams()
                                .get("sql_str")
                        ).build();
                databaseCaseConfigMapper.delete(
                        Wrappers.<DatabaseCaseConfig>lambdaQuery()
                                .eq(DatabaseCaseConfig::getCaseId, caseId));
                databaseCaseConfigMapper.insert(databaseCaseConfig);
            }
            else if ("verification".equals(type)) {
                VerificationCaseConfig verificationCaseConfig =
                        VerificationCaseConfig.builder(
                        ).caseId(caseId
                        ).verificationType(config.getParams()
                                .get("verification_type")
                        ).parameters(config.getParams()
                                .get("parameters")
                        ).build();
                verificationCaseConfigMapper.delete(
                        Wrappers.<VerificationCaseConfig>lambdaQuery()
                                .eq(VerificationCaseConfig::getCaseId, caseId));
                verificationCaseConfigMapper.insert(verificationCaseConfig);
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
            verificationCaseConfigMapper.delete(
                    Wrappers.<VerificationCaseConfig>lambdaQuery()
                            .in(VerificationCaseConfig::getCaseId, caseIds));
            featureCaseNextCaseMapper.delete(
                    Wrappers.<FeatureCaseNextCase>lambdaQuery()
                            .in(FeatureCaseNextCase::getSourceCaseId, caseIds));
            caseMapper.delete(
                    Wrappers.<Caze>lambdaQuery()
                            .in(Caze::getId, caseIds));
            featureMapper.delete(
                    Wrappers.<Feature>lambdaQuery()
                            .eq(Feature::getFeature_id, id));
        }
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
                .info(taskResult.getLogs())
                .assertion(taskResult.getAssertions())
                .parameters(parameters)
                .status(status).build();
        return rsp;
    }

    public TaskResult execFeature(Long featureId, String paramName, List<Long> paramIndexList) {
        TaskResult taskResult = TaskResult.builder().build();
        List<Caze> featureCases
                = caseMapper.selectList(
                Wrappers.<Caze>lambdaQuery()
                        .eq(Caze::getFeatureId, featureId));
        //获取对应name的parameter list
        List<com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Parameter> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(paramIndexList)) {
            list = parameterMapper.selectList(
                    Wrappers.<com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Parameter>lambdaQuery()
                            .eq(com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Parameter::getParameterName, paramName));
        } else {
            list = parameterMapper.selectList(
                    Wrappers.<com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Parameter>lambdaQuery()
                            .eq(com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Parameter::getParameterName, paramName)
                            .in(com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Parameter::getParameterValueIndex, paramIndexList));
        }
        //输入参数
        if (!CollectionUtils.isEmpty(list)) {
                //获取parameter index set
                Set<Long> indexList = list
                        .stream()
                        .map(com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Parameter::getParameterValueIndex)
                        .collect(Collectors.toSet());
                if (!CollectionUtils.isEmpty(indexList)) {
                    for (Long index : indexList) {
                        List<Parameter> parameters = new ArrayList<>();
                        List<com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Parameter> parameterCases
                                = list.stream()
                                .filter(item -> index.equals(item.getParameterValueIndex()))
                                .collect(Collectors.toList());
                        for (com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Parameter param : parameterCases) {
                            Parameter parameter = new Parameter();
                            parameter.setParameter_key(param.getParameterKey());
                            parameter.setParameter_value(param.getParameterValue());
                            parameters.add(parameter);
                        }
                        //执行feature
                        taskResult.setLogs(
                                String.format("%s%s",
                                        taskResult.getLogs(),
                                        execFeature(parameters, featureCases).getLogs()));
                        taskResult.setAssertions(
                                String.format("%s%s",
                                        taskResult.getAssertions(),
                                        execFeature(parameters, featureCases).getAssertions()));
                    }
                }
            }
        return taskResult;
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
        List<Long> caseIds = nodes
                .stream()
                .map(Nodes::getId).collect(Collectors.toList());
        List<Config> configs = new ArrayList<>();
        List<Edges> edges = new ArrayList<>();

        if (!CollectionUtils.isEmpty(caseIds)) {
            //根据featureId查询所有caseConfig
            List<RequestCaseConfig> requestCaseConfigs
                    = requestCaseConfigMapper.selectList(
                    Wrappers.<RequestCaseConfig>lambdaQuery()
                    .in(RequestCaseConfig::getCaseId, caseIds));
            if (!CollectionUtils.isEmpty(requestCaseConfigs)) {
                for (RequestCaseConfig requestCaseConfig : requestCaseConfigs) {
                    HashMap<String, String> params = new HashMap<>();
                    Nodes node = nodes
                            .stream()
                            .filter(item->requestCaseConfig.getId().equals(item.getId()))
                            .findFirst().orElse(null);
                    params.put("url", requestCaseConfig.getUrl());
                    params.put("request_body", requestCaseConfig.getRequestBody());
                    params.put("request_type",requestCaseConfig.getRequestType());
                    params.put("content_type", requestCaseConfig.getContentType());
                    params.put("request_configs", requestCaseConfig.getRequestConfigs());
                    params.put("request_headers", requestCaseConfig.getRequestHeaders());
                    Config config = Config.builder()
                            .label(null == node ? "" : node.getLabel())
                            .params(params)
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
                            .filter(item->databaseCaseConfig.getId().equals(item.getId()))
                            .findFirst().orElse(null);
                    params.put("sql_str", databaseCaseConfig.getSqlStr());
                    Config config = Config.builder()
                            .label(null == node ? "" : node.getLabel())
                            .params(params)
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
                            .filter(item->parseCaseConfig.getId().equals(item.getId()))
                            .findFirst().orElse(null);
                    params.put("parameters", parseCaseConfig.getParameters());
                    params.put("cvt_method_source", parseCaseConfig.getCvtMethodSource());
                    Config config = Config.builder()
                            .label(null == node ? "" : node.getLabel())
                            .params(params)
                            .build();
                    configs.add(config);
                }
            }
            List<VerificationCaseConfig> verificationCaseConfigs
                    = verificationCaseConfigMapper.selectList(
                    Wrappers.<VerificationCaseConfig>lambdaQuery()
                            .in(VerificationCaseConfig::getCaseId, caseIds));
            if (!CollectionUtils.isEmpty(verificationCaseConfigs)) {
                for (VerificationCaseConfig verificationCaseConfig : verificationCaseConfigs) {
                    HashMap<String, String> params = new HashMap<>();
                    Nodes node = nodes
                            .stream()
                            .filter(item->verificationCaseConfig.getId().equals(item.getId()))
                            .findFirst().orElse(null);
                    params.put("verification_type", verificationCaseConfig.getVerificationType());
                    params.put("parameters", verificationCaseConfig.getParameters());
                    Config config = Config.builder()
                            .label(null == node ? "" : node.getLabel())
                            .params(params)
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
    private TaskResult execFeature(List<Parameter> parameters, List<Caze> featureCaseList) {
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
            Caze featureCase = featureCaseList.stream().filter(item->item.getId().equals(n)).findFirst().orElse(null);
            if (null == featureCase) {
                break;
            }
            //执行测试用例
            executeCase(testFlowManager, publicKey, featureCase);
            //List<FeatureCaseNextCase> nextCaseKeys = featureCaseNextCaseMapper.SelByFCId(featureCase.getId());
            List<FeatureCaseNextCase> nextCaseKeys = new ArrayList<>();
            for (FeatureCaseNextCase caseKey : nextCaseKeys) {
                //入度减一
                inDegreeMap.put(caseKey.getSourceCaseId(), null==inDegreeMap.get(caseKey.getSourceCaseId()) ? 0 :inDegreeMap.get(caseKey.getSourceCaseId()) - 1);
                //如果入度为0
                if(inDegreeMap.get(caseKey.getSourceCaseId()) == 0){
                    s1.offer(caseKey.getSourceCaseId());
                }
            }
        }
        TaskResult taskResult
                = TaskResult.builder()
                .logs(testFlowManager.getBuffer("tf_log"))
                .assertions(null == testFlowManager.getBuffer("tf_assertion") ?
                        "passed":
                        testFlowManager.getBuffer("tf_assertion"))
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
                inDegreeMap.put(caseKey.getSourceCaseId(),
                        null == inDegreeMap.get(caseKey.getSourceCaseId())
                                ? 1
                                : inDegreeMap.get(caseKey.getSourceCaseId()) + 1);
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
                testFlowManager.addBuffer(
                        featureCase.getLabel(),
                        testFlowManager.queryDataBase(featureCase.getLabel(),
                                databaseCaseConfig.getSqlStr())
                );
                break;
            }
            case Constants.PARSE: {
                ParseCaseConfig parseCaseConfig
                        = parseCaseConfigMapper.selectOne(
                        Wrappers.<ParseCaseConfig>lambdaQuery()
                                .eq(ParseCaseConfig::getCaseId, featureCase.getId()));
                testFlowManager.addBuffer(
                        featureCase.getLabel(),
                        testFlowManager.sourceParse(featureCase.getLabel(),
                                parseCaseConfig.getCvtMethodSource(),
                                FastJsonUtil.toList(parseCaseConfig.getParameters())
                        )
                );
                break;
            }
            case Constants.REQUEST: {
                RequestCaseConfig requestCaseConfig
                        = requestCaseConfigMapper.selectOne(
                        Wrappers.<RequestCaseConfig>lambdaQuery()
                                .eq(RequestCaseConfig::getCaseId, featureCase.getId()));
                testFlowManager.addBuffer(
                        featureCase.getLabel(),
                        testFlowManager.sendRequest(featureCase.getLabel(),
                                requestCaseConfig.getRequestBody(),
                                "null".equals(requestCaseConfig.getRequestConfigs())||null == requestCaseConfig.getRequestConfigs() ? null : FastJsonUtil.toMap(requestCaseConfig.getRequestConfigs()),
                                "null".equals(requestCaseConfig.getRequestHeaders())|| null == requestCaseConfig.getRequestHeaders() ? null : FastJsonUtil.toMap(requestCaseConfig.getRequestHeaders()),
                                requestCaseConfig.getRequestType(),
                                requestCaseConfig.getContentType(),
                                requestCaseConfig.getUrl()
                        )
                );
                break;
            }
            case Constants.VERIFICATION: {
                VerificationCaseConfig verificationCaseConfig
                        = verificationCaseConfigMapper.selectOne(
                        Wrappers.<VerificationCaseConfig>lambdaQuery()
                                .eq(VerificationCaseConfig::getCaseId, featureCase.getId()));
                List<String> parameters = FastJsonUtil.toList(verificationCaseConfig.getParameters());
                if (Constants.COMPARE.equals(verificationCaseConfig.getVerificationType())) {
                    FastJsonUtil.toList(verificationCaseConfig.getParameters());
                    testFlowManager.addBuffer(
                            "tf_assertion",
                    testFlowManager.verify(parameters.get(0), parameters.get(1)));
                }
                else if (Constants.XPATHCOMPARE.equals(verificationCaseConfig.getVerificationType())) {
                    testFlowManager.addBuffer(
                            "tf_assertion",
                    testFlowManager.verify(parameters.get(0), parameters.get(1), parameters.get(2)));
                }
                else if (Constants.OBJCOMPARE.equals(verificationCaseConfig.getVerificationType())) {
                    testFlowManager.addBuffer(
                            "tf_assertion",
                    testFlowManager.verify(parameters.get(0), parameters.get(1), parameters.get(2), parameters.get(3)));
                }
                break;
            }
            default: {}
        }
    }
}
