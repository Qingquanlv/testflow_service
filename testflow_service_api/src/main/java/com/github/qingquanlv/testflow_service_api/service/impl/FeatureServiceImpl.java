package com.github.qingquanlv.testflow_service_api.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.qingquanlv.testflow_service_api.common.Constants;
import com.github.qingquanlv.testflow_service_api.common.Utils;
import com.github.qingquanlv.testflow_service_api.dao.EnvTabMapper;
import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.*;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.createfeature.CreateFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.createfeature.CreateFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.deletefeature.DeleteFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.execfeature.ExecFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.execfeature.ExecFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.queryallfeaure.QueryAllFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.queryallfeaure.QueryAllFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.queryallfeaure.QueryFeature;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.queryfeature.QueryFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.Parameter;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.*;
import com.github.qingquanlv.testflow_service_api.mapper.*;
import com.github.qingquanlv.testflow_service_api.model.po.EnvTabPo;
import com.github.qingquanlv.testflow_service_api.service.FeatureService;
import com.github.qingquanlv.testflow_service_api.service.IDeleteCase;
import com.github.qingquanlv.testflow_service_api.service.IInsertCase;
import com.github.qingquanlv.testflow_service_api.service.IQueryCase;
import com.github.qingquanlv.testflow_service_biz.TestFlowManager;
import com.github.qingquanlv.testflow_service_biz.utilities.FastJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author Qingquan Lv
 * @Version 1.0
 */
@Service
public class FeatureServiceImpl implements FeatureService {

        @Autowired
        FeatureMapper featureMapper;

        @Autowired
        CazeMapper caseMapper;

        @Autowired
        EnvTabMapper envConfigMapper;

        @Autowired
        CaseNextCaseMapper featureCaseNextCaseMapper;

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

        @Autowired
        IfConditionCaseConfigMapper ifConditionCaseConfigMapper;

        @Autowired
        ForConditionCaseConfigMapper forConditionCaseConfigMapper;

        @Autowired
        CaseFactory caseFactory;


        /**
         * query all feature
         * @param request request
         * @return response
         */
        @Override
        public QueryAllFeatureResponse queryFeatureAll(QueryAllFeatureRequest request) {
                Status status = new Status();
                status.setSuccess(true);
                String filter
                        = null == request.getFilter() ? "" : request.getFilter();
                long pageNum
                        = null == request.getPageNum() ? 1L : request.getPageNum();
                long pageSize
                        = null == request.getPageSize() ? 10L : request.getPageSize();
                Page<FeatureTab> features
                        = featureMapper.selectPage(
                        new Page<FeatureTab>(pageNum, pageSize),
                        Wrappers.<FeatureTab>lambdaQuery()
                                .like(FeatureTab::getFeature_name, filter)
                                .orderByDesc(FeatureTab::getFeature_id));
                List<QueryFeature> queryFeatures = new ArrayList<>();
                for (FeatureTab feature : features.getRecords()) {
                        QueryFeature queryFeature
                                = QueryFeature.builder()
                                .feature_id(feature.getFeature_id())
                                .featureName(feature.getFeature_name())
                                .description(feature.getDescription()).build();
                        queryFeatures.add(queryFeature);
                }
                return QueryAllFeatureResponse.builder()
                        .status(status)
                        .features(queryFeatures)
                        .total(features.getTotal()).build();
        }

        /**
         * query one feature details
         * @param id feature id
         * @return response
         */
        @Override
        public QueryFeatureResponse getFeature(Long id) {
                FeatureTab feature = featureMapper.selectById(id);
                //根据featureId查询所有case
                List<Caze> cazeList
                        = caseMapper.selectList(Wrappers.<Caze>lambdaQuery()
                        .eq(Caze::getFeatureId, id));
                List<Nodes> nodes = new ArrayList<>();
                if (!CollectionUtils.isEmpty(cazeList)) {
                        for (Caze featureCase : cazeList) {
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
                Map<String, List<Long>> caseMap = getCazeMap(cazeList);
                List<Config> configs = new ArrayList<>();
                List<Edges> edges = new ArrayList<>();

                for (String key : caseMap.keySet()) {
                        List<Long> ids = caseMap.get(key);
                        if (CollectionUtils.isEmpty(ids) || key.equals(Constants.FEATURE)) {
                                continue;
                        }
                        IQueryCase iQueryCase = caseFactory.reflactCaze(
                            getCaseNameByType(key),
                            getCaseMapperByType(key));
                        List<Map<String,String>> lm = iQueryCase.select(ids);
                        for (Map<String, String> m : lm) {
                                Nodes node = nodes
                                        .stream()
                                        .filter(item -> m.get("caseId").equals(item.getId().toString()))
                                        .findFirst().orElse(null);
                                Config config = Config.builder()
                                        .id(null == node ? null : node.getId())
                                        .label(null == node ? "" : node.getLabel())
                                        .exec_params(m)
                                        .build();
                                configs.add(config);
                        }
                }
                //get sub feature config
                configs.addAll(getSubFeatureConfig(cazeList, nodes));
                if (!CollectionUtils.isEmpty(caseIds)) {
                        //根据featureId查询所有连线
                        List<CaseNextCaseTab> featureCaseNextCases
                                = featureCaseNextCaseMapper.selectList(
                                Wrappers.<CaseNextCaseTab>lambdaQuery()
                                        .in(CaseNextCaseTab::getSourceCaseId, caseIds));
                        if (!CollectionUtils.isEmpty(featureCaseNextCases)) {
                                for (CaseNextCaseTab featureCaseNextCase : featureCaseNextCases) {
                                        Edges edge = Edges.builder()
                                                .source(String.valueOf(featureCaseNextCase.getSourceCaseId()))
                                                .target(String.valueOf(featureCaseNextCase.getTargetCaseId()))
                                                .sourceAnchor(featureCaseNextCase.getSourceanchor())
                                                .targetAnchor(featureCaseNextCase.getTargetanchor()).build();
                                        edges.add(edge);
                                }
                        }
                }
                return QueryFeatureResponse.builder()
                    .featureId(id)
                    .featureName(null == feature ? "" : feature.getFeature_name())
                    .description(null == feature ? "" : feature.getDescription())
                    .config(configs)
                    .edges(edges)
                    .nodes(nodes)
                    .build();
        }

        /**
         * create feature
         *
         * @param request
         * @return
         */
        @Override
        public CreateFeatureResponse createFeature(CreateFeatureRequest request) {
                CreateFeatureResponse response = new CreateFeatureResponse();
                Status status = validateRequest(request);
                if (!status.getSuccess()) {
                        response.setStatus(status);
                        return response;
                }
                FeatureTab feature =
                        FeatureTab.builder()
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
                        //delete
                        if (!CollectionUtils.isEmpty(CaseList)) {
                                deleteCaseConfig(feature.getFeature_id(), CaseList);
                        }
                        //insert
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
                                //insert node config
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
                                                CaseNextCaseTab featureCaseNextCase =
                                                        CaseNextCaseTab
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

        /**
         * 删除feature
         *
         * @param id featureId
         * @return DeleteFeatureResponse
         */
        @Override
        public DeleteFeatureResponse deleteFeature(Long id) {
                Status status = new Status();
                status.setSuccess(true);
                //根据featureId查询所有case
                List<Caze> cazeList
                        = caseMapper.selectList(Wrappers.<Caze>lambdaQuery()
                        .eq(Caze::getFeatureId, id));

                Map<String, List<Long>> caseMap = getCazeMap(cazeList);
                for (Map.Entry<String, List<Long>> entry : caseMap.entrySet()) {
                        List<Long> ids = entry.getValue();
                        if (CollectionUtils.isEmpty(ids)) {
                                continue;
                        }
                        IDeleteCase baseCaze = caseFactory.reflactCaze(
                            getCaseNameByType(entry.getKey()), getCaseMapperByType(entry.getKey()));
                        baseCaze.delete(ids);
                }
                List<Long> caseIds = cazeList
                    .stream()
                    .map(Caze::getId).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(caseIds)) {
                        caseMapper.delete(
                            Wrappers.<Caze>lambdaQuery()
                                .in(Caze::getId, caseIds));
                }
                featureMapper.delete(
                        Wrappers.<FeatureTab>lambdaQuery()
                                .eq(FeatureTab::getFeature_id, id));
                return DeleteFeatureResponse.builder()
                .status(status)
                .build();
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
                List<Parameter> parameters = new ArrayList<>();
                status.setSuccess(true);

                List<Caze> featureCases
                        = caseMapper.selectList(
                        Wrappers.<Caze>lambdaQuery()
                                .eq(Caze::getFeatureId, request.getFeatureId()));
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
                FeatureTaskResult taskResult
                        = execFeature(parameters, featureCases);
                return ExecFeatureResponse.builder()
                .info(FastJsonUtil.toListBean(
                        taskResult.getLogs(),
                        CaseInfo.class))
                .parameters(request.getParameters())
                .status(status).build();
        }

        /**
         * 执行feature方法
         *
         * @param parameters
         * @param featureCaseList
         */
        public FeatureTaskResult execFeature(List<Parameter> parameters, List<Caze> featureCaseList) {
                //buffer 公共key
                String publicKey = Utils.hashKeyForDisk(String.format("%s%s",
                        System.currentTimeMillis(),
                        UUID.randomUUID().toString()));
                TestFlowManager testFlowManager = new TestFlowManager(publicKey);
                List<EnvTabPo> envConfigs
                        = envConfigMapper.selectList(
                        Wrappers.emptyWrapper());
                //输入配置
                if (!CollectionUtils.isEmpty(envConfigs)) {
                        for (EnvTabPo config : envConfigs) {
                                testFlowManager.addBuffer(
                                        String.format("env$$config$$%s$$%s",
                                                config.getCaseType(),
                                                config.getConfigKey()),
                                        config.getConfigVal());
                        }
                }
                //输入参数
                if (!CollectionUtils.isEmpty(parameters)) {
                        for (Parameter item : parameters) {
                                testFlowManager.addBuffer(item.getParameter_key(), item.getParameter_value());
                        }
                }
                return execFeatureCore(testFlowManager, featureCaseList);
        }

        /**
         * 执行Feature核心逻辑
         *
         * @param testFlowManager
         * @param featureCaseList
         * @return
         */
        public FeatureTaskResult execFeatureCore(TestFlowManager testFlowManager, List<Caze> featureCaseList) {
                Integer featureStatus = 1;
                //储存入度
                Map<Long, Integer> inDegreeMap = new HashMap<>();
                initMap(featureCaseList, inDegreeMap);
                //主要流程
                Queue<Long> s1 = new ArrayDeque<>();
                Set<Long> idSet
                        = featureCaseList.stream().map(Caze::getId)
                        .collect(Collectors.toSet());
                //获取入度为0的case, 并且插入队列
                idSet.removeAll(inDegreeMap.keySet());
                for (Long item : idSet) {
                        s1.offer(item);
                }
                List<CaseInfo> caseInfoList = new ArrayList<>();
                //遍历执行入度为0的case
                while (!s1.isEmpty()) {
                        //抛出队头，并且执行
                        CaseInfo caseInfo = new CaseInfo();
                        Long n = s1.poll();
                        Caze featureCase = featureCaseList
                                .stream()
                                .filter(item -> item.getId().equals(n))
                                .findFirst().orElse(null);
                        if (null == featureCase) {
                                break;
                        }
                        //执行测试用例
                        if (Constants.FEATURE.equals(featureCase.getCaseType())) {
                                List<Caze> subFeatureList
                                        = caseMapper.selectList(
                                        Wrappers.<Caze>lambdaQuery()
                                                .eq(Caze::getFeatureId, featureCase.getSubTaskId()));
                                FeatureTaskResult result
                                        = execFeatureCore(testFlowManager, subFeatureList);
                                featureStatus = result.getStatus()
                                        & featureStatus;
                                List<CaseInfo> subCaseInfoList
                                        = FastJsonUtil.toListBean(result.getLogs(), CaseInfo.class);
                                caseInfoList.addAll(subCaseInfoList);
                        } else {
                                executeCase(testFlowManager, featureCase);
                                //插入执行结果
                                caseInfo = setCaseResult(testFlowManager, featureCase);
                                featureStatus = caseInfo.getStatus()
                                        & featureStatus;
                                caseInfoList.add(caseInfo);
                        }
                        List<Long> TargetCaseIds = new ArrayList<>();
                        if (Constants.CONDITION_IF.equals(featureCase.getCaseType())) {
                                String cazeName = caseInfo.getData();
                                Caze caze = featureCaseList
                                        .stream()
                                        .filter(item -> item.getLabel().equals(cazeName)).findFirst().orElse(null);
                                if (null != caze) {
                                        TargetCaseIds.add(caze.getId());
                                }
                        } else if (Constants.CONDITION_FOR.equals(featureCase.getCaseType())) {
                                ForConditionCaseConfig config
                                        = forConditionCaseConfigMapper.selectOne(
                                        Wrappers.<ForConditionCaseConfig>lambdaQuery()
                                                .eq(ForConditionCaseConfig::getCaseId, featureCase.getId()));
                                if (Constants.STATUS_LOOP.equalsIgnoreCase(caseInfo.getData())) {
                                        Caze caze = featureCaseList
                                                .stream()
                                                .filter(item -> item.getLabel().equals(config.getLoopNodeName())).findFirst().orElse(null);
                                        List<Caze> loopFeatureCaseList = getLoopCases(caze.getId(), featureCase.getId(), featureCaseList);
                                        initMap(loopFeatureCaseList, inDegreeMap);
                                        s1.offer(caze.getId());
                                } else {
                                        Caze caze = featureCaseList
                                                .stream()
                                                .filter(item -> item.getLabel().equals(config.getNextNodeName())).findFirst().orElse(null);
                                        if (null != caze) {
                                                TargetCaseIds.add(caze.getId());
                                        }
                                }
                        } else {
                                List<CaseNextCaseTab> nextCaseKeys
                                        = featureCaseNextCaseMapper.selectList(
                                        Wrappers.<CaseNextCaseTab>lambdaQuery()
                                                .in(CaseNextCaseTab::getSourceCaseId, featureCase.getId()));
                                TargetCaseIds
                                        = nextCaseKeys
                                        .stream()
                                        .map(CaseNextCaseTab::getTargetCaseId)
                                        .collect(Collectors.toList());
                        }
                        for (Long caseId : TargetCaseIds) {
                                //入度减一
                                inDegreeMap.put(caseId,
                                        null == inDegreeMap.get(caseId)
                                                ? 0
                                                : inDegreeMap.get(caseId) - 1);
                                //如果入度为0
                                if (inDegreeMap.get(caseId) == 0) {
                                        s1.offer(caseId);
                                }
                        }
                }
                FeatureTaskResult taskResult
                        = FeatureTaskResult.builder()
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
                List<Caze> list = featureCaseList
                        .stream()
                        .filter(item -> !Constants.CONDITION_FOR.equals(item.getCaseType()))
                        .collect(Collectors.toList());
                List<Caze> loopList = featureCaseList
                        .stream()
                        .filter(item -> Constants.CONDITION_FOR.equals(item.getCaseType()))
                        .collect(Collectors.toList());
                for (Caze featureCase : list) {
                        List<CaseNextCaseTab> nextCaseKeys
                                = featureCaseNextCaseMapper.selectList(
                                Wrappers.<CaseNextCaseTab>lambdaQuery()
                                        .in(CaseNextCaseTab::getSourceCaseId, featureCase.getId()));
                        for (CaseNextCaseTab caseKey : nextCaseKeys) {
                                inDegreeMap.put(caseKey.getTargetCaseId(),
                                        null == inDegreeMap.get(caseKey.getTargetCaseId())
                                                ? 1
                                                : inDegreeMap.get(caseKey.getTargetCaseId()) + 1);
                        }
                }
                for (Caze featureCase : loopList) {
                        ForConditionCaseConfig config
                                = forConditionCaseConfigMapper.selectOne(
                                Wrappers.<ForConditionCaseConfig>lambdaQuery()
                                        .eq(ForConditionCaseConfig::getCaseId, featureCase.getId()));
                        if (null != config) {
                                Caze caze = featureCaseList
                                        .stream()
                                        .filter(item -> item.getLabel().equals(config.getNextNodeName())).findFirst().orElse(null);
                                if (null != caze) {
                                        inDegreeMap.put(caze.getId(),
                                                null == inDegreeMap.get(caze.getId())
                                                        ? 1
                                                        : inDegreeMap.get(caze.getId()) + 1);
                                }
                        }
                }
        }

        /**
         * 执行测试用例方法
         *
         * @param testFlowManager
         * @param featureCase
         */
        private void executeCase(TestFlowManager testFlowManager, Caze featureCase) {
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
                                RequestCaseConfigTab requestCaseConfig
                                        = requestCaseConfigMapper.selectOne(
                                        Wrappers.<RequestCaseConfigTab>lambdaQuery()
                                                .eq(RequestCaseConfigTab::getCaseId, featureCase.getId()));
                                testFlowManager.sendRequest(featureCase.getLabel(),
                                        "null".equals(requestCaseConfig.getRequestBody()) || null == requestCaseConfig.getRequestBody() ? null : requestCaseConfig.getRequestBody(),
                                        "null".equals(requestCaseConfig.getRequestConfigs()) || null == requestCaseConfig.getRequestConfigs() ? null : requestCaseConfig.getRequestConfigs(),
                                        "null".equals(requestCaseConfig.getRequestHeaders()) || null == requestCaseConfig.getRequestHeaders() ? null : requestCaseConfig.getRequestHeaders(),
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
                                        compareobjCaseConfig.getNoCompareItemMap(),
                                        compareobjCaseConfig.getThresholdMap());
                                break;
                        }
                        case Constants.CONDITION_IF: {
                                IfConditionCaseConfig ifConditionCaseConfig
                                        = ifConditionCaseConfigMapper.selectOne(
                                        Wrappers.<IfConditionCaseConfig>lambdaQuery()
                                                .eq(IfConditionCaseConfig::getCaseId, featureCase.getId()));
                                testFlowManager.ifCondition(featureCase.getLabel(),
                                        ifConditionCaseConfig.getPath(),
                                        ifConditionCaseConfig.getOperator(),
                                        ifConditionCaseConfig.getValue(),
                                        ifConditionCaseConfig.getTrueNodeName(),
                                        ifConditionCaseConfig.getFalseNodeName());
                                break;
                        }
                        case Constants.CONDITION_FOR: {
                                ForConditionCaseConfig forConditionCaseConfig
                                        = forConditionCaseConfigMapper.selectOne(
                                        Wrappers.<ForConditionCaseConfig>lambdaQuery()
                                                .eq(ForConditionCaseConfig::getCaseId, featureCase.getId()));
                                testFlowManager.forCondition(featureCase.getLabel(),
                                        forConditionCaseConfig.getPath(),
                                        forConditionCaseConfig.getOperator(),
                                        forConditionCaseConfig.getValue(),
                                        forConditionCaseConfig.getMaxLoop(),
                                        forConditionCaseConfig.getWaitTime());
                                break;
                        }
                        default: {
                        }
                }
        }

        /**
         * 查询两个节点中的节点
         *
         * @param startCaseId
         * @param endCaseId
         * @param featureCaseList
         * @return
         */
        private List<Caze> getLoopCases(Long startCaseId, Long endCaseId, List<Caze> featureCaseList) {
                Caze startCase = featureCaseList.stream().filter(i -> i.getId().equals(startCaseId)).findFirst().orElse(null);
                Caze endCase = featureCaseList.stream().filter(i -> i.getId().equals(endCaseId)).findFirst().orElse(null);
                List<Caze> nodes = new ArrayList<>();
                Queue<Caze> targetNodes = new ArrayDeque<>();
                targetNodes.offer(startCase);

                while (!targetNodes.isEmpty()) {
                        Caze caze = targetNodes.poll();
                        nodes.add(caze);
                        List<CaseNextCaseTab> nextCaseKeys
                                = new ArrayList<>();
                        if (!caze.getId().equals(endCase.getId())) {
                                for (CaseNextCaseTab item : nextCaseKeys) {
                                        Caze targetCaze
                                                = featureCaseList
                                                .stream()
                                                .filter(i -> i.getId().equals(item.getTargetCaseId()))
                                                .findFirst().orElse(null);
                                        if (null != targetCaze) {
                                                if (!nodes.contains(targetCaze)) {
                                                        targetNodes.offer(targetCaze);
                                                }
                                        }
                                }
                        }
                }
                return nodes;
        }

        /**
         * 插入测试结果
         *
         * @param testFlowManager
         * @param featureCase
         * @return
         */
        private CaseInfo setCaseResult(TestFlowManager testFlowManager, Caze featureCase) {
                CaseInfo caseInfo = new CaseInfo();
                caseInfo.setClazz(featureCase.getCaseType());
                caseInfo.setConfig(testFlowManager
                        .getBuffer(String.format("config$%s",
                                featureCase.getLabel())));
                caseInfo.setLabel(featureCase.getLabel());
                caseInfo.setData(testFlowManager
                        .getBuffer(featureCase.getLabel()));
                String st = testFlowManager
                        .getBuffer(String.format("status$%s",
                                featureCase.getLabel()));
                caseInfo.setStatus(null == st
                        ? 0
                        : Integer.parseInt(st));
                return caseInfo;
        }

        /**
         * validate create feature Request
         *
         * @param request
         * @return
         */
        private Status validateRequest(CreateFeatureRequest request) {
                Status status = new Status();
                status.setSuccess(true);
                if (StringUtils.isEmpty(request.getFeatureName())) {
                        status.setMessage("feature name is empty");
                        status.setSuccess(false);
                        status.setErrorCode(1);
                        return status;
                }
                if (StringUtils.isEmpty(request.getDescription())) {
                        status.setMessage("feature description is empty");
                        status.setSuccess(false);
                        status.setErrorCode(2);
                        return status;
                }
                if (null != request.getNodes()) {
                        List<String> collect = request.getNodes().stream()
                                .map(Nodes::getLabel)
                                .distinct()
                                .collect(Collectors.toList());
                        if (collect.size() != request.getNodes().size()) {
                                status.setMessage("duplicate node name");
                                status.setSuccess(false);
                                status.setErrorCode(3);
                                return status;
                        }
                }
                return status;
        }

        private void deleteCaseConfig(Long featureId, List<Caze> cazeList) {
                Map<String, List<Long>> caseMap = getCazeMap(cazeList);
                for (Map.Entry<String, List<Long>> entry : caseMap.entrySet()) {
                        List<Long> ids = entry.getValue();
                        if (CollectionUtils.isEmpty(ids)) {
                                continue;
                        }
                        IDeleteCase baseCaze = caseFactory.reflactCaze(
                            getCaseNameByType(entry.getKey()), getCaseMapperByType(entry.getKey()));
                        baseCaze.delete(ids);
                }
                Set<Long> cazesIds = cazeList
                        .stream()
                        .map(Caze::getId)
                        .collect(Collectors.toSet());
                featureCaseNextCaseMapper.delete(
                        Wrappers.<CaseNextCaseTab>lambdaQuery()
                                .in(CaseNextCaseTab::getSourceCaseId, cazesIds)
                                .or()
                                .in(CaseNextCaseTab::getTargetCaseId, cazesIds));
                //case数据全删全插
                caseMapper.delete(Wrappers.<Caze>lambdaQuery()
                        .eq(Caze::getFeatureId, featureId));

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
                        if (Constants.FEATURE.equals(type)) {
                                Caze caze =
                                    Caze.builder(
                                    ).id(caseId
                                    ).subTaskId(Long.valueOf(config.getExec_params()
                                        .get("featureId"))
                                    ).build();
                                caseMapper.updateById(caze);
                                return;
                        }
                        IInsertCase iInsertCase = caseFactory.reflactCaze(
                            getCaseNameByType(type),
                            getCaseMapperByType(type));
                        iInsertCase.insert(caseId, config);

                }
        }


        private Map<String, List<Long>> getCazeMap(List<Caze> cazeList) {
                Map<String, List<Long>> cazeMap = new HashMap<>();
                for (Caze caze : cazeList) {
                        if (cazeMap.containsKey(caze.getCaseType())) {
                                cazeMap.get(caze.getCaseType()).add(caze.getId());
                        } else {
                                List<Long> caseIds = new ArrayList<>();
                                caseIds.add(caze.getId());
                                cazeMap.put(caze.getCaseType(), caseIds);
                        }
                }
                return cazeMap;
        }


        private List<Config> getSubFeatureConfig(List<Caze> cazeList, List<Nodes> nodes) {
                List<Config> configList = new ArrayList<>();
                Set<Caze> subFeatureCases
                    = cazeList
                    .stream()
                    .filter(item->Constants.FEATURE.equals(item.getCaseType()))
                    .collect(Collectors.toSet());
                if (!CollectionUtils.isEmpty(subFeatureCases)) {
                        for (Caze subFeatureCase : subFeatureCases) {
                                FeatureTab subFeature
                                    = featureMapper.selectOne(
                                    Wrappers.<FeatureTab>lambdaQuery()
                                        .eq(FeatureTab::getFeature_id, subFeatureCase.getSubTaskId()));
                                if (null != subFeature) {
                                        HashMap<String, String> params = new HashMap<>();
                                        Nodes node = nodes
                                            .stream()
                                            .filter(item -> subFeatureCase.getId().equals(item.getId()))
                                            .findFirst().orElse(null);
                                        params.put("featureId", String.valueOf(subFeature.getFeature_id()));
                                        params.put("featureName", subFeature.getFeature_name());
                                        Config config = Config.builder()
                                            .id(null == node ? null : node.getId())
                                            .label(null == node ? "" : node.getLabel())
                                            .exec_params(params)
                                            .build();
                                        configList.add(config);
                                }
                        }
                }
                return configList;
        }

        private String getCaseNameByType(String type) {
                switch (type) {
                        case "request":
                                return "RequestCaze";
                        case "database":
                                return "DatabaseCase";
                        case "tidb":
                                return "TidbCase";
                        case "druid":
                                return "DruidCase";
                        case "parse":
                                return "ParseCase";
                        case "compare_val":
                                return "ComparevalCase";
                        case "compare_path":
                                return "ComparepathCase";
                        case "compare_obj":
                                return "CompareobjCase";
                        case "condition_if":
                                return "IfConditionCase";
                        case "condition_for":
                                return "ForConditionCase";
                        default:
                                return "RequestCaze";
                }
        }

        private Object getCaseMapperByType(String type) {
                switch (type) {
                        case "request":
                                return requestCaseConfigMapper;
                        case "database":
                                return databaseCaseConfigMapper;
                        case "tidb":
                                return tidbCaseConfigMapper;
                        case "druid":
                                return druidCaseConfigMapper;
                        case "parse":
                                return parseCaseConfigMapper;
                        case "compare_val":
                                return comparevalCaseConfigMapper;
                        case "compare_path":
                                return comparepathCaseConfigMapper;
                        case "compare_obj":
                                return compareobjCaseConfigMapper;
                        case "condition_if":
                                return ifConditionCaseConfigMapper;
                        case "condition_for":
                                return forConditionCaseConfigMapper;
                        default:
                                return requestCaseConfigMapper;
                }
        }

}
