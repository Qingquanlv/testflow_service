package com.github.qingquanlv.testflow_service_api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.qingquanlv.testflow_service_api.common.Constants;
import com.github.qingquanlv.testflow_service_api.common.Lang;
import com.github.qingquanlv.testflow_service_api.common.Utils;
import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.cases.CaseKey;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.DataBaseCases;
import com.github.qingquanlv.testflow_service_api.entity.cases.parse.ParseCases;
import com.github.qingquanlv.testflow_service_api.entity.cases.request.RequestCases;
import com.github.qingquanlv.testflow_service_api.entity.cases.verification.VerificationCases;
import com.github.qingquanlv.testflow_service_api.entity.feature.createfeature.CreateFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature.createfeature.CreateFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature.deletefeature.DeleteFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature.execasyncfeature.ExecAsyncFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature.execasyncfeature.ExecAsyncFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature.execfeature.ExecFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature.execfeature.ExecFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature.queryallfeature.QueryAllFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature.queryallfeature.QueryFeature;
import com.github.qingquanlv.testflow_service_api.entity.feature.queryfeature.QueryFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature.resultfeature.ResultCase;
import com.github.qingquanlv.testflow_service_api.entity.feature.resultfeature.ResultFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.Parameter;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.*;
import com.github.qingquanlv.testflow_service_api.mapper.*;
import com.github.qingquanlv.testflow_service_api.service.FeatureService;
import com.github.qingquanlv.testflow_service_biz.TestFlowManager;
import com.github.qingquanlv.testflow_service_biz.utilities.FastJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/2 9:39
 * @Version 1.0
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FeatureServiceImpl implements FeatureService {

    @Autowired
    FeatureMapper featureMapper;

    @Autowired
    FeatureCaseMapper featureCaseMapper;

    @Autowired
    FeatureCaseNextCaseMapper featureCaseNextCaseMapper;

    @Autowired
    FeatureResultMapper featureResultMapper;

    @Autowired
    DataBaseCaseMapper dataBaseCaseMapper;

    @Autowired
    VerificationCaseMapper verificationCaseMapper;

    @Autowired
    PaserCaseMapper paserCaseMapper;

    @Autowired
    RequestCaseMapper requestCaseMapper;

    @Autowired
    ParameterMapper parameterMapper;

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

        try {
            //status = assertion(request);
            //如果request报文入参没有问题
            if (true || status.getSuccess()) {
                List<FeatureCaseNextCase> featureCaseNextCaseList = new ArrayList<>();
                //插入request
                List<RequestCase> requestCaseList = new ArrayList<>();
                //更新request
                List<RequestCase> requestUpdateCaseList = new ArrayList<>();
                //插入database
                List<DatabaseCase> databaseCaseList = new ArrayList<>();
                //更新database
                List<DatabaseCase> databaseUpdateCaseList = new ArrayList<>();
                //插入parse
                List<PaserCase> paserCaseList = new ArrayList<>();
                //删除parse
                List<PaserCase> paserCaseUpdateList = new ArrayList<>();
                //插入VerificationCase
                List<VerificationCase> verificationCaseList = new ArrayList<>();
                //插入VerificationCase
                List<VerificationCase> verificationUpdateCaseList = new ArrayList<>();
                //feature case
                List<FeatureCase> featureCaseList = featureCaseMapper.Sel(request.getFeatureId());

                List<FeatureCase> featureCaseRequstList = featureCaseList
                        .stream()
                        .filter(item -> Constants.REQUEST.equals(item.getCase_type()))
                        .collect(Collectors.toList());

                List<FeatureCase> featureCaseDatabaseList = featureCaseList
                        .stream()
                        .filter(item -> Constants.DATABASE.equals(item.getCase_type()))
                        .collect(Collectors.toList());

                List<FeatureCase> featureCaseParseList = featureCaseList
                        .stream()
                        .filter(item -> Constants.PARSE.equals(item.getCase_type()))
                        .collect(Collectors.toList());

                List<FeatureCase> featureCaseVerificationList = featureCaseList
                        .stream()
                        .filter(item -> Constants.VERIFICATION.equals(item.getCase_type()))
                        .collect(Collectors.toList());

                //插入feature
                Feature feature = new Feature();
                feature.setFeature_name(request.getFeatureName());
                feature.setDesctription(request.getDescription());
                featureMapper.Ins(feature);
                //插入request case
                if (!CollectionUtils.isEmpty(request.getRequestCasesList())) {
                    for (RequestCases item : request.getRequestCasesList()) {
                        RequestCase requstCase = new RequestCase();
                        requstCase.setCase_name(item.getKey().getCaseName());
                        requstCase.setContent_type(item.getContentType());
                        requstCase.setRequest_type(item.getRequestType());
                        requstCase.setRequest_body(item.getRequestBody());
                        requstCase.setUrl(item.getUrl());
                        requstCase.setRequest_configs(FastJsonUtil.toJson(item.getConfigs()));
                        requstCase.setRequest_headers(FastJsonUtil.toJson(item.getHeaders()));
                        //保存更新case
                        if (!CollectionUtils.isEmpty(featureCaseRequstList)
                                && null != featureCaseRequstList
                                .stream()
                                .filter(i -> i.getCase_id().equals(item.getKey().getCaseId()))
                                .findFirst().orElse(null)) {
                            requstCase.setCase_id(item.getKey().getCaseId());
                            requestUpdateCaseList.add(requstCase);
                            //保存删除case
                            featureCaseRequstList.remove(featureCaseRequstList
                                    .stream()
                                    .filter(i -> i.getCase_id().equals(item.getKey().getCaseId()))
                                    .findFirst().orElse(null));
                        } else {
                            //保存插入case
                            requestCaseList.add(requstCase);
                        }
                    }
                    //删除request list
                    Set<Long> delIds = featureCaseRequstList.stream().map(FeatureCase::getCase_id).collect(Collectors.toSet());
                    if (!CollectionUtils.isEmpty(delIds)) {
                        requestCaseMapper.Del(delIds);
                    }
                    //更新requset list
                    if (!CollectionUtils.isEmpty(requestUpdateCaseList)) {
                        requestCaseMapper.Upd(requestUpdateCaseList);
                    }
                    //插入request list
                    if (!CollectionUtils.isEmpty(requestCaseList)) {
                        requestCaseMapper.Ins(requestCaseList);
                    }
                    //删除 featureCase list
                    Set<Long> delfcIds = featureCaseRequstList.stream().map(FeatureCase::getId).collect(Collectors.toSet());
                    if (!CollectionUtils.isEmpty(delfcIds)) {
                        featureCaseMapper.DelByFId(delfcIds);
                    }
                    //删除全部featureCase next case
                    Set<Long> fdIds = featureCaseRequstList.stream().map(FeatureCase::getId).collect(Collectors.toSet());
                    if (!CollectionUtils.isEmpty(fdIds)) {
                        featureCaseNextCaseMapper.Del(fdIds);
                    }
                    //当前request list
                    requestCaseList.addAll(requestUpdateCaseList);
                }
                //database case
                if (!CollectionUtils.isEmpty(request.getDataBaseCasesList())) {
                    for (DataBaseCases item : request.getDataBaseCasesList()) {
                        DatabaseCase databaseCase = new DatabaseCase();
                        databaseCase.setCase_name(item.getKey().getCaseName());
                        databaseCase.setSql(item.getSql());
                        //保存更新case
                        if (!CollectionUtils.isEmpty(featureCaseDatabaseList)
                                && null != featureCaseDatabaseList
                                .stream()
                                .filter(i -> i.getCase_id().equals(item.getKey().getCaseId()))
                                .findFirst().orElse(null)) {
                            databaseCase.setCase_id(item.getKey().getCaseId());
                            databaseUpdateCaseList.add(databaseCase);
                            //保存删除case
                            featureCaseDatabaseList.remove(featureCaseDatabaseList
                                    .stream()
                                    .filter(i -> i.getCase_id().equals(item.getKey().getCaseId()))
                                    .findFirst().orElse(null));
                        } else {
                            //保存插入case
                            databaseCaseList.add(databaseCase);
                        }
                    }
                    //删除database list
                    Set<Long> delIds = featureCaseDatabaseList.stream().map(FeatureCase::getId).collect(Collectors.toSet());
                    if (!CollectionUtils.isEmpty(delIds)) {
                        dataBaseCaseMapper.Del(delIds);
                    }
                    //更新database list
                    if (!CollectionUtils.isEmpty(databaseUpdateCaseList)) {
                        dataBaseCaseMapper.Upd(databaseUpdateCaseList);
                    }
                    //插入database list
                    if (!CollectionUtils.isEmpty(databaseCaseList)) {
                        dataBaseCaseMapper.Ins(databaseCaseList);
                    }
                    //删除 featureCase list
                    Set<Long> delfcIds = featureCaseDatabaseList.stream().map(FeatureCase::getId).collect(Collectors.toSet());
                    if (!CollectionUtils.isEmpty(delfcIds)) {
                        featureCaseMapper.DelByFId(delfcIds);
                    }
                    //删除全部featureCase next case
                    Set<Long> fdIds = featureCaseDatabaseList.stream().map(FeatureCase::getId).collect(Collectors.toSet());
                    if (!CollectionUtils.isEmpty(fdIds)) {
                        featureCaseNextCaseMapper.Del(fdIds);
                    }
                    //当前request list
                    databaseCaseList.addAll(databaseUpdateCaseList);
                }
                //parse case
                if (null != request.getPaserCasesList() && !request.getPaserCasesList().isEmpty()) {
                    for (ParseCases item : request.getPaserCasesList()) {
                        PaserCase paserCase = new PaserCase();
                        paserCase.setCase_name(item.getKey().getCaseName());
                        paserCase.setCvt_method_source(item.getCvtMethodSource());
                        paserCase.setParameters(FastJsonUtil.toJson(item.getParameters()));
                        //保存更新case
                        if (!CollectionUtils.isEmpty(featureCaseParseList)
                                && null != featureCaseParseList
                                .stream()
                                .filter(i -> i.getCase_id().equals(item.getKey().getCaseId()))
                                .findFirst().orElse(null)) {
                            paserCase.setCase_id(item.getKey().getCaseId());
                            paserCaseUpdateList.add(paserCase);
                            //保存删除case
                            featureCaseParseList.remove(featureCaseParseList
                                    .stream()
                                    .filter(i -> i.getCase_id().equals(item.getKey().getCaseId()))
                                    .findFirst().orElse(null));
                        } else {
                            //保存插入case
                            paserCaseList.add(paserCase);
                        }
                    }
                    //删除parse list
                    Set<Long> delIds = featureCaseParseList.stream().map(FeatureCase::getCase_id).collect(Collectors.toSet());
                    if (!CollectionUtils.isEmpty(delIds)) {
                        paserCaseMapper.Del(delIds);
                    }
                    //更新parse list
                    if (!CollectionUtils.isEmpty(paserCaseUpdateList)) {
                        paserCaseMapper.Upd(paserCaseUpdateList);
                    }
                    //插入parse list
                    if (!CollectionUtils.isEmpty(paserCaseList)) {
                        paserCaseMapper.Ins(paserCaseList);
                    }
                    //删除 featureCase list
                    Set<Long> delfcIds = featureCaseParseList.stream().map(FeatureCase::getId).collect(Collectors.toSet());
                    if (!CollectionUtils.isEmpty(delfcIds)) {
                        featureCaseMapper.DelByFId(delfcIds);
                    }
                    //删除全部featureCase next case
                    Set<Long> fdIds = featureCaseParseList.stream().map(FeatureCase::getId).collect(Collectors.toSet());
                    if (!CollectionUtils.isEmpty(fdIds)) {
                        featureCaseNextCaseMapper.Del(fdIds);
                    }
                    //当前request list
                    paserCaseList.addAll(paserCaseUpdateList);
                }
                //verification case
                if (null != request.getVerificationCasesList() && !request.getVerificationCasesList().isEmpty()) {
                    for (VerificationCases item : request.getVerificationCasesList()) {
                        VerificationCase verificationCase = new VerificationCase();
                        verificationCase.setCase_name(item.getKey().getCaseName());
                        verificationCase.setVerification_type(item.getVerifyType());
                        verificationCase.setParameters(FastJsonUtil.toJson(item.getParameters()));
                        //保存更新case
                        if (!CollectionUtils.isEmpty(featureCaseVerificationList)
                                && null != featureCaseVerificationList
                                .stream()
                                .filter(i -> i.getCase_id().equals(item.getKey().getCaseId()))
                                .findFirst().orElse(null)) {
                            verificationCase.setCase_id(item.getKey().getCaseId());
                            verificationUpdateCaseList.add(verificationCase);
                            //保存删除case
                            featureCaseVerificationList.remove(featureCaseVerificationList
                                    .stream()
                                    .filter(i -> i.getCase_id().equals(item.getKey().getCaseId()))
                                    .findFirst().orElse(null));
                        } else {
                            //保存插入case
                            verificationCaseList.add(verificationCase);
                        }
                    }
                    //删除verification list
                    Set<Long> delIds = featureCaseVerificationList.stream().map(FeatureCase::getCase_id).collect(Collectors.toSet());
                    if (!CollectionUtils.isEmpty(delIds)) {
                        verificationCaseMapper.Del(delIds);
                    }
                    //更新verification list
                    if (!CollectionUtils.isEmpty(verificationUpdateCaseList)) {
                        verificationCaseMapper.Upd(verificationUpdateCaseList);
                    }
                    //插入verification list
                    if (!CollectionUtils.isEmpty(verificationCaseList)) {
                        verificationCaseMapper.Ins(verificationCaseList);
                    }
                    //删除 featureCase list
                    Set<Long> delfcIds = featureCaseVerificationList.stream().map(FeatureCase::getId).collect(Collectors.toSet());
                    if (!CollectionUtils.isEmpty(delfcIds)) {
                        featureCaseMapper.DelByFId(delfcIds);
                    }
                    //删除全部featureCase next case
                    Set<Long> fdIds = featureCaseVerificationList.stream().map(FeatureCase::getId).collect(Collectors.toSet());
                    if (!CollectionUtils.isEmpty(fdIds)) {
                        featureCaseNextCaseMapper.Del(fdIds);
                    }
                    //当前request list
                    verificationCaseList.addAll(verificationCaseList);
                }
                //feature-case
                if (null != request.getRequestCasesList() && !request.getRequestCasesList().isEmpty()) {
                    for (RequestCases item : request.getRequestCasesList()) {
                        FeatureCase featureCase = new FeatureCase();
                        featureCase.setFeature_id(feature.getFeature_id());
                        RequestCase cs = requestCaseList.stream().filter(i -> item.getKey().getCaseName().equals(i.getCase_name())).findFirst().orElse(null);
                        featureCase.setCase_id(cs.getCase_id());
                        featureCase.setCase_type("request");
                        featureCaseMapper.InsOne(featureCase);
                        if (null != item.getNextKeys()) {
                            for (CaseKey caseKey : item.getNextKeys()) {
                                FeatureCaseNextCase featureCaseNextCase = new FeatureCaseNextCase();
                                featureCaseNextCase.setFeature_case_id(featureCase.getId());
                                featureCaseNextCase.setCase_id(getCaseId(caseKey.getCaseType(), caseKey.getCaseName(), requestCaseList, databaseCaseList, paserCaseList, verificationCaseList));
                                featureCaseNextCase.setCase_type(caseKey.getCaseType());
                                featureCaseNextCaseList.add(featureCaseNextCase);
                            }
                        }
                    }
                }
                if (null != request.getDataBaseCasesList() && !request.getDataBaseCasesList().isEmpty()) {
                    for (DataBaseCases item : request.getDataBaseCasesList()) {
                        FeatureCase featureCase = new FeatureCase();
                        featureCase.setFeature_id(feature.getFeature_id());
                        DatabaseCase cs = databaseCaseList.stream().filter(i -> item.getKey().getCaseName().equals(i.getCase_name())).findFirst().orElse(null);
                        featureCase.setCase_id(cs.getCase_id());
                        featureCase.setCase_type("database");
                        featureCaseMapper.InsOne(featureCase);
                        for (CaseKey caseKey : item.getNextKeys()) {
                            FeatureCaseNextCase featureCaseNextCase = new FeatureCaseNextCase();
                            featureCaseNextCase.setFeature_case_id(featureCase.getId());
                            featureCaseNextCase.setCase_id(getCaseId(caseKey.getCaseType(), caseKey.getCaseName(), requestCaseList, databaseCaseList, paserCaseList, verificationCaseList));
                            featureCaseNextCase.setCase_type(caseKey.getCaseType());
                            featureCaseNextCaseList.add(featureCaseNextCase);
                        }
                    }
                }
                if (null != request.getPaserCasesList() && !request.getPaserCasesList().isEmpty()) {
                    for (ParseCases item : request.getPaserCasesList()) {
                        FeatureCase featureCase = new FeatureCase();
                        featureCase.setFeature_id(feature.getFeature_id());
                        PaserCase cs = paserCaseList.stream().filter(i -> item.getKey().getCaseName().equals(i.getCase_name())).findFirst().orElse(null);
                        featureCase.setCase_id(cs.getCase_id());
                        featureCase.setCase_type("parse");
                        featureCaseMapper.InsOne(featureCase);
                        for (CaseKey caseKey : item.getNextKeys()) {
                            FeatureCaseNextCase featureCaseNextCase = new FeatureCaseNextCase();
                            featureCaseNextCase.setFeature_case_id(featureCase.getId());
                            featureCaseNextCase.setCase_id(getCaseId(caseKey.getCaseType(), caseKey.getCaseName(), requestCaseList, databaseCaseList, paserCaseList, verificationCaseList));
                            featureCaseNextCase.setCase_type(caseKey.getCaseType());
                            featureCaseNextCaseList.add(featureCaseNextCase);
                        }
                    }
                }
                if (null != request.getVerificationCasesList() && !request.getVerificationCasesList().isEmpty()) {
                    for (VerificationCases item : request.getVerificationCasesList()) {
                        FeatureCase featureCase = new FeatureCase();
                        featureCase.setFeature_id(feature.getFeature_id());
                        VerificationCase cs = verificationCaseList.stream().filter(i -> item.getKey().getCaseName().equals(i.getCase_name())).findFirst().orElse(null);
                        featureCase.setCase_id(cs.getCase_id());
                        featureCase.setCase_type("verification");
                        featureCaseMapper.InsOne(featureCase);
                        for (CaseKey caseKey : item.getNextKeys()) {
                            FeatureCaseNextCase featureCaseNextCase = new FeatureCaseNextCase();
                            featureCaseNextCase.setFeature_case_id(featureCase.getId());
                            featureCaseNextCase.setCase_id(getCaseId(caseKey.getCaseType(), caseKey.getCaseName(), requestCaseList, databaseCaseList, paserCaseList, verificationCaseList));
                            featureCaseNextCase.setCase_type(caseKey.getCaseType());
                            featureCaseNextCaseList.add(featureCaseNextCase);
                        }
                    }
                }

                //插入next feature case关联关系
                featureCaseNextCaseMapper.Ins(featureCaseNextCaseList);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            status.setSuccess(false);
            status.setErrorCode(109);
            status.setMessage(Lang.getErrorCode(109));
        }
        response.setStatus(status);
        return response;
    }

    private Long getCaseId(String caseType, String caseName, List<RequestCase> requestCaseList, List<DatabaseCase> databaseCaseList, List<PaserCase> paserCaseList, List<VerificationCase> verificationCaseList) {

        try {
            if ("request".equals(caseType.toLowerCase())) {
                RequestCase rcs = requestCaseList.stream().filter(i -> caseName.equals(i.getCase_name())).findFirst().orElse(null);
                return rcs.getCase_id();
            } else if ("database".equals(caseType.toLowerCase())) {
                DatabaseCase dcs = databaseCaseList.stream().filter(i -> caseName.equals(i.getCase_name())).findFirst().orElse(null);
                return dcs.getCase_id();
            } else if ("parse".equals(caseType.toLowerCase())) {
                PaserCase pcs = paserCaseList.stream().filter(i -> caseName.equals(i.getCase_name())).findFirst().orElse(null);
                return pcs.getCase_id();
            } else if ("verification".equals(caseType.toLowerCase())) {
                VerificationCase vcs = verificationCaseList.stream().filter(i -> caseName.equals(i.getCase_name())).findFirst().orElse(null);
                return vcs.getCase_id();
            }
        }
        catch (Exception ex) {
            return null;
        }
        return null;
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
        DeleteFeatureResponse rsp = new DeleteFeatureResponse();
        //根据featureId获取所有featureCase
        List<FeatureCase> featureCases = featureCaseMapper.SelByFId(id);
        if (!CollectionUtils.isEmpty(featureCases)) {
            Set<Long> fcIds = featureCases.stream()
                    .map(FeatureCase::getId)
                    .collect(Collectors.toSet());
            //删除request case
            List<FeatureCase> requestFeatureCaseRequsts = featureCases
                    .stream()
                    .filter(i -> Constants.REQUEST.equals(i.getCase_type()))
                    .collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(requestFeatureCaseRequsts)) {
                Set<Long> requestIds = requestFeatureCaseRequsts
                        .stream()
                        .map(FeatureCase::getCase_id)
                        .collect(Collectors.toSet());
                requestCaseMapper.Del(requestIds);
            }
            //删除parse case
            List<FeatureCase> parseFeatureCaseRequsts = featureCases
                    .stream()
                    .filter(i -> Constants.PARSE.equals(i.getCase_type()))
                    .collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(parseFeatureCaseRequsts)) {
                Set<Long> parseIds = parseFeatureCaseRequsts
                        .stream()
                        .map(FeatureCase::getCase_id)
                        .collect(Collectors.toSet());
                paserCaseMapper.Del(parseIds);
            }
            //删除database case
            List<FeatureCase> databaseFeatureCaseRequsts = featureCases
                    .stream()
                    .filter(i -> Constants.DATABASE.equals(i.getCase_type()))
                    .collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(databaseFeatureCaseRequsts)) {
                Set<Long> databaseIds = databaseFeatureCaseRequsts
                        .stream()
                        .map(FeatureCase::getCase_id)
                        .collect(Collectors.toSet());
                paserCaseMapper.Del(databaseIds);
            }
            //删除verification case
            List<FeatureCase> verificationFeatureCaseRequsts = featureCases
                    .stream()
                    .filter(i -> Constants.DATABASE.equals(i.getCase_type()))
                    .collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(verificationFeatureCaseRequsts)) {
                Set<Long> verificationIds = verificationFeatureCaseRequsts
                        .stream()
                        .map(FeatureCase::getCase_id)
                        .collect(Collectors.toSet());
                paserCaseMapper.Del(verificationIds);
            }
            //删除faetureCase和featureNextCase
            featureCaseMapper.Del(id);
            if (!CollectionUtils.isEmpty(fcIds)) {
                featureCaseNextCaseMapper.Del(fcIds);
            }
        }
        featureMapper.Del(id);
        rsp.setStatus(status);
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
        ExecFeatureResponse rsp = new ExecFeatureResponse();
        Status status = new Status();
        status.setSuccess(true);
        List<FeatureCase> featureCaseList = featureCaseMapper.SelByFId(request.getFeatureId());
        List<Parameter> parameters = new ArrayList<>();
        //输入参数
        if (!CollectionUtils.isEmpty(request.getParameters())) {
            for (String key : request.getParameters().keySet()) {
                Parameter parameter = new Parameter();
                parameter.setParameter_value(key);
                parameter.setParameter_key(request.getParameters().get(key));
                parameters.add(parameter);
            }
        }
        execFeature(null, parameters, featureCaseList);
        rsp.setParameters(parameters);
        rsp.setStatus(status);
        return rsp;
    }

    /**
     * 异步执行feature
     *
     * @param request
     * @return
     */
    @Async("threadPoolTaskExecutor")
    @Override
    public CompletableFuture<ExecAsyncFeatureResponse> execFeatureAsync(ExecAsyncFeatureRequest request) {
        ExecAsyncFeatureResponse rsp = new ExecAsyncFeatureResponse();
        Status status = new Status();
        status.setSuccess(true);
        List<FeatureCase> featureCaseList = featureCaseMapper.SelByFId(request.getFeatureId());
        //获取对应name的parameter list
        List<ParameterCase> list = parameterMapper.Sel(request.getParameter_names());
        List<HashMap<String, String>> parameterList = new ArrayList<>();
        //获取parameter index set
        Set<Long> indexList = list.stream().map(ParameterCase::getParameter_value_index).collect(Collectors.toSet());
        if (!CollectionUtils.isEmpty(indexList)) {
            for (Long index : indexList) {
                List<Parameter> parameters = new ArrayList<>();
                HashMap<String, String> parameterMap = new HashMap<>();
                //获取对应index下所有parameter key-value
                List<ParameterCase> parameterCases = list.stream()
                        .filter(item->index.equals(item.getParameter_value_index()))
                        .collect(Collectors.toList());
                for (ParameterCase parameterCase : parameterCases) {
                    if (!parameterMap.containsKey(parameterCase.getParameter_key())) {
                        Parameter parameter = new Parameter();
                        parameter.setParameter_key(parameterCase.getParameter_key());
                        parameter.setParameter_value(parameterCase.getParameter_value());
                        parameters.add(parameter);
                        parameterMap.put(parameterCase.getParameter_key(),
                                parameterCase.getParameter_key());
                    }
                }
                //根据key-value 执行feature
                execFeature(index, parameters, featureCaseList);
                parameterList.add(parameterMap);
            }
        }
        rsp.setStatus(status);
        //rsp.setParameters(parameterList);
        return CompletableFuture.completedFuture(rsp);
    }

    @Override
    public QueryFeatureResponse getFeature(Long id) {
        QueryFeatureResponse rsp = new QueryFeatureResponse();
        Feature feature = featureMapper.Sel(id);
        List<FeatureCase> featureCaseList = featureCaseMapper.SelByFId(id);
        //获取featureCaseId
        Set<Long> featureCaseId = featureCaseList.stream().map(FeatureCase::getId).collect(Collectors.toSet());
        List<FeatureCaseNextCase> featureCaseNextCases = featureCaseNextCaseMapper.SelListByFCId(featureCaseId);
        //获取requestCase
        Set<FeatureCase> requestFeatureCases = featureCaseList
                .stream()
                .filter(item->item.getCase_type().equals(Constants.REQUEST))
                .collect(Collectors.toSet());
        Set<Long> requestCasesIds = requestFeatureCases.stream().map(FeatureCase::getCase_id).collect(Collectors.toSet());
        List<RequestCase> requestCases = CollectionUtils.isEmpty(requestCasesIds)
                ? new ArrayList<>()
                : requestCaseMapper.SelList(requestCasesIds);
        //获取databaseCase
        Set<FeatureCase> databaseFeatureCases = featureCaseList
                .stream()
                .filter(item->item.getCase_type().equals(Constants.DATABASE))
                .collect(Collectors.toSet());
        Set<Long> databaseCasesIds = databaseFeatureCases.stream().map(FeatureCase::getCase_id).collect(Collectors.toSet());
        List<DatabaseCase> databaseCases = CollectionUtils.isEmpty(databaseCasesIds)
                ? new ArrayList<>()
                : dataBaseCaseMapper.SelList(databaseCasesIds);
        //获取parseCase
        Set<FeatureCase> parseFeatureCases = featureCaseList
                .stream()
                .filter(item->item.getCase_type().equals(Constants.PARSE))
                .collect(Collectors.toSet());
        Set<Long> parsetCasesIds = parseFeatureCases.stream().map(FeatureCase::getCase_id).collect(Collectors.toSet());
        List<PaserCase> parseCases = CollectionUtils.isEmpty(parsetCasesIds)
                ? new ArrayList<>()
                : paserCaseMapper.SelList(parsetCasesIds);
        //获取verifyCase
        Set<FeatureCase> verifytFeatureCases = featureCaseList
                .stream()
                .filter(item->item.getCase_type().equals(Constants.VERIFICATION))
                .collect(Collectors.toSet());
        Set<Long> verifyCasesIds = verifytFeatureCases.stream().map(FeatureCase::getCase_id).collect(Collectors.toSet());
        List<VerificationCase> verifyCases = CollectionUtils.isEmpty(verifyCasesIds)
                ? new ArrayList<>()
                : verificationCaseMapper.SelList(verifyCasesIds);

        List<RequestCases> requestCasesList = new ArrayList<>();
        for (RequestCase item : requestCases) {
            RequestCases requestCase = new RequestCases();
            requestCase.setUrl(item.getUrl());
            requestCase.setContentType(item.getContent_type());
            requestCase.setRequestBody(item.getRequest_body());
            requestCase.setRequestType(item.getRequest_type());
            CaseKey caseKey = new CaseKey();
            caseKey.setCaseId(item.getCase_id());
            caseKey.setCaseName(item.getCase_name());
            caseKey.setCaseType(Constants.REQUEST);
            requestCase.setKey(caseKey);
            List<CaseKey> newCaseKeys = new ArrayList<>();
            //获取nextCaseList
            List<FeatureCaseNextCase> nextCases = featureCaseNextCases.stream().filter(i->requestCasesIds.contains(i.getFeature_case_id())).collect(Collectors.toList());
            for (FeatureCaseNextCase featureCaseNextCase : nextCases) {
                CaseKey caseKeyItem = new CaseKey();
                caseKeyItem.setCaseType(featureCaseNextCase.getCase_type());
                caseKeyItem.setCaseId(featureCaseNextCase.getCase_id());
                caseKeyItem.setCaseName(getCaseName(featureCaseNextCase.getCase_id(),
                        featureCaseNextCase.getCase_type(),
                        requestCases,
                        databaseCases,
                        parseCases,
                        verifyCases));
                newCaseKeys.add(caseKeyItem);
            }
            requestCase.setNextKeys(newCaseKeys);
            requestCasesList.add(requestCase);
        }
        List<DataBaseCases> databaseCasesList = new ArrayList<>();
        for (DatabaseCase item : databaseCases) {
            DataBaseCases databaseCase = new DataBaseCases();
            databaseCase.setSql(item.getSql());
            CaseKey caseKey = new CaseKey();
            caseKey.setCaseId(item.getCase_id());
            caseKey.setCaseName(item.getCase_name());
            caseKey.setCaseType(Constants.DATABASE);
            databaseCase.setKey(caseKey);
            List<CaseKey> newCaseKeys = new ArrayList<>();
            //获取nextCaseList
            List<FeatureCaseNextCase> nextCases = featureCaseNextCases.stream().filter(i->databaseCasesIds.contains(i.getFeature_case_id())).collect(Collectors.toList());
            for (FeatureCaseNextCase featureCaseNextCase : nextCases) {
                CaseKey caseKeyItem = new CaseKey();
                caseKeyItem.setCaseType(featureCaseNextCase.getCase_type());
                caseKeyItem.setCaseId(featureCaseNextCase.getCase_id());
                caseKeyItem.setCaseName(getCaseName(featureCaseNextCase.getCase_id(),
                        featureCaseNextCase.getCase_type(),
                        requestCases,
                        databaseCases,
                        parseCases,
                        verifyCases));
                newCaseKeys.add(caseKeyItem);
            }
            databaseCase.setNextKeys(newCaseKeys);
            databaseCasesList.add(databaseCase);
        }
        List<ParseCases> parseCasesList = new ArrayList<>();
        for (PaserCase item : parseCases) {
            ParseCases parseCase = new ParseCases();
            parseCase.setCvtMethodSource(item.getCvt_method_source());
            List<String> parameterList = FastJsonUtil.toList(item.getParameters());
            parseCase.setParameters(parameterList);
            CaseKey caseKey = new CaseKey();
            caseKey.setCaseId(item.getCase_id());
            caseKey.setCaseName(item.getCase_name());
            caseKey.setCaseType(Constants.PARSE);
            parseCase.setKey(caseKey);
            List<CaseKey> newCaseKeys = new ArrayList<>();
            //获取nextCaseList
            List<FeatureCaseNextCase> nextCases = featureCaseNextCases.stream().filter(i->parsetCasesIds.contains(i.getFeature_case_id())).collect(Collectors.toList());
            for (FeatureCaseNextCase featureCaseNextCase : nextCases) {
                CaseKey caseKeyItem = new CaseKey();
                caseKeyItem.setCaseType(featureCaseNextCase.getCase_type());
                caseKeyItem.setCaseId(featureCaseNextCase.getCase_id());
                caseKeyItem.setCaseName(getCaseName(featureCaseNextCase.getCase_id(),
                        featureCaseNextCase.getCase_type(),
                        requestCases,
                        databaseCases,
                        parseCases,
                        verifyCases));
                newCaseKeys.add(caseKeyItem);
            }
            parseCase.setNextKeys(newCaseKeys);
            parseCasesList.add(parseCase);
        }
        List<VerificationCases> verificationCasesList = new ArrayList<>();
        for (VerificationCase item : verifyCases) {
            VerificationCases verificationCase = new VerificationCases();
            verificationCase.setParameters(FastJsonUtil.toList(item.getParameters()));
            verificationCase.setVerifyType(item.getVerification_type());
            CaseKey caseKey = new CaseKey();
            caseKey.setCaseId(item.getCase_id());
            caseKey.setCaseName(item.getCase_name());
            caseKey.setCaseType(Constants.VERIFICATION);
            verificationCase.setKey(caseKey);
            List<CaseKey> newCaseKeys = new ArrayList<>();
            //获取nextCaseList
            List<FeatureCaseNextCase> nextCases = featureCaseNextCases.stream().filter(i->parsetCasesIds.contains(i.getFeature_case_id())).collect(Collectors.toList());
            for (FeatureCaseNextCase featureCaseNextCase : nextCases) {
                CaseKey caseKeyItem = new CaseKey();
                caseKeyItem.setCaseType(featureCaseNextCase.getCase_type());
                caseKeyItem.setCaseId(featureCaseNextCase.getCase_id());
                caseKeyItem.setCaseName(getCaseName(featureCaseNextCase.getCase_id(),
                        featureCaseNextCase.getCase_type(),
                        requestCases,
                        databaseCases,
                        parseCases,
                        verifyCases));
                newCaseKeys.add(caseKeyItem);
            }
            verificationCase.setNextKeys(newCaseKeys);
            verificationCasesList.add(verificationCase);
        }
        rsp.setRequestCasesList(requestCasesList);
        rsp.setDataBaseCasesList(databaseCasesList);
        rsp.setPaserCasesList(parseCasesList);
        rsp.setVerificationCasesList(verificationCasesList);
        rsp.setFeatureName(feature.getFeature_name());
        rsp.setDescription(feature.getDesctription());
        return rsp;
    }

    @Override
    public QueryAllFeatureResponse getFeatureAll() {
        QueryAllFeatureResponse rsp = new QueryAllFeatureResponse();
        Status status = new Status();
        status.setSuccess(true);
        rsp.setStatus(status);
        List<QueryFeature> queryFeaturesRsp = new ArrayList<>();
        List<Feature> features = featureMapper.SelAll();
        for (Feature feature : features) {
            QueryFeature queryFeatureRsp = new QueryFeature();
            queryFeatureRsp.setFeature_id(feature.getFeature_id());
            queryFeatureRsp.setFeatureName(feature.getFeature_name());
            queryFeatureRsp.setDescription(feature.getDesctription());
            queryFeaturesRsp.add(queryFeatureRsp);
        }
        rsp.setFeatures(queryFeaturesRsp);
        return rsp;
    }

    /**
     * 根据caseId caseType获取caseType
     *
     * @param caseId
     * @param caseType
     * @param requestCases
     * @param databaseCases
     * @param parseCases
     * @param verifyCases
     * @return
     */
    private String getCaseName(Long caseId, String caseType, List<RequestCase> requestCases, List<DatabaseCase> databaseCases, List<PaserCase> parseCases, List<VerificationCase> verifyCases) {
        //忽略大小写
        String caseName = "";
        switch (caseType.toLowerCase()) {
            case Constants.REQUEST: {
                RequestCase tCase = requestCases.stream().filter(item -> item.getCase_id().equals(caseId)).findFirst().orElse(null);
                if (null != tCase) {
                    caseName = tCase.getCase_name();
                }
                break;
            }
            case Constants.DATABASE: {
                DatabaseCase tCase = databaseCases.stream().filter(item -> item.getCase_id().equals(caseId)).findFirst().orElse(null);
                if (null != tCase) {
                    caseName = tCase.getCase_name();
                }
                break;
            }
            case Constants.PARSE: {
                PaserCase tCase = parseCases.stream().filter(item -> item.getCase_id().equals(caseId)).findFirst().orElse(null);
                if (null != tCase) {
                    caseName = tCase.getCase_name();
                }
                break;
            }
            case Constants.VERIFICATION: {
                VerificationCase tCase = verifyCases.stream().filter(item -> item.getCase_id().equals(caseId)).findFirst().orElse(null);
                if (null != tCase) {
                    caseName = tCase.getCase_name();
                }
                break;
            }
        }
        return caseName;
    }

    /**
     * 执行feature方法
     *
     * @param featureCaseList
     */
    private void execFeature(Long index, List<Parameter> parameters, List<FeatureCase> featureCaseList) {

        //buffer 公共key
        String publicKey = Utils.hashKeyForDisk(String.format("%s%s",
                System.currentTimeMillis(),
                UUID.randomUUID().toString()));
        TestFlowManager testFlowManager = new TestFlowManager(publicKey);
        //Feature feature = featureMapper.Sel(id);
        //输入参数
        if (!CollectionUtils.isEmpty(parameters)) {
            for (Parameter item : parameters) {
                testFlowManager.addBuffer(String.format("%s:%s", publicKey, item.getParameter_key()), item.getParameter_value());
            }
        }
        //储存入度
        Map<Long, Integer> inDegreeMap = new HashMap<>();
        initMap(featureCaseList, inDegreeMap);
        //主要流程
        Queue<Long> s1 = new ArrayDeque<>();
        Set<Long> idSet = featureCaseList.stream().map(FeatureCase::getId).collect(Collectors.toSet());
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
            FeatureCase featureCase = featureCaseList.stream().filter(item->item.getId().equals(n)).findFirst().orElse(null);
            if (null == featureCase) {
                break;
            }
            //执行测试用例
            executeCase(testFlowManager, publicKey, featureCase);
            List<FeatureCaseNextCase> nextCaseKeys = featureCaseNextCaseMapper.SelByFCId(featureCase.getId());
            for (FeatureCaseNextCase caseKey : nextCaseKeys) {
                //入度减一
                inDegreeMap.put(caseKey.getCase_id(), null==inDegreeMap.get(caseKey.getCase_id()) ? 0 :inDegreeMap.get(caseKey.getCase_id()) - 1);
                //如果入度为0
                if(inDegreeMap.get(caseKey.getCase_id()) == 0){
                    s1.offer(caseKey.getCase_id());
                }
            }
        }
        if (null != index && -1 != index) {
            FeatureResult featureResult = new FeatureResult();
            featureResult.setFeature_id(featureCaseList.get(0).getFeature_id());
            featureResult.setParameter_value_index(index);
            featureResult.setLog_key(String.format("%s_%s",index, testFlowManager.getBuffer("tf_log")));
            featureResult.setAssertion_key(String.format("%s_%s",index, testFlowManager.getBuffer("tf_assertion")));
            featureResultMapper.ins(featureResult);
        }
        testFlowManager.deposed();
    }

    /**
     * 生成入度Map
     *
     * @param featureCaseList
     * @param inDegreeMap
     */
    private void initMap(List<FeatureCase> featureCaseList, Map<Long, Integer> inDegreeMap) {
        for (FeatureCase featureCase : featureCaseList) {
            List<FeatureCaseNextCase> nextCaseKeys = featureCaseNextCaseMapper.SelByFCId(featureCase.getId());
            for (FeatureCaseNextCase caseKey : nextCaseKeys) {
                inDegreeMap.put(caseKey.getCase_id(), null == inDegreeMap.get(caseKey.getCase_id()) ? 1 : inDegreeMap.get(caseKey.getCase_id()) + 1);
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
    private void executeCase(TestFlowManager testFlowManager, String publicKey, FeatureCase featureCase) {
        //忽略大小写
        switch (featureCase.getCase_type().toLowerCase()) {
            case Constants.DATABASE: {
                DatabaseCase databaseCase = dataBaseCaseMapper.SelOne(featureCase.getCase_id());
                testFlowManager.addBuffer(
                        databaseCase.getCase_name(),
                        testFlowManager.queryDataBase(databaseCase.getCase_name(),
                                databaseCase.getSql())
                );
                break;
            }
            case Constants.PARSE: {
                PaserCase parseCases = paserCaseMapper.SelOne(featureCase.getCase_id());
                testFlowManager.addBuffer(
                        parseCases.getCase_name(),
                        testFlowManager.sourceParse(parseCases.getCase_name(),
                                parseCases.getCvt_method_source(),
                                FastJsonUtil.toList(parseCases.getParameters())
                        )
                );
                break;
            }
            case Constants.REQUEST: {
                RequestCase requestCase = requestCaseMapper.SelOne(featureCase.getCase_id());
                testFlowManager.addBuffer(
                        requestCase.getCase_name(),
                        testFlowManager.sendRequest(requestCase.getCase_name(),
                                requestCase.getRequest_body(),
                                "null".equals(requestCase.getRequest_configs())||null == requestCase.getRequest_configs() ? null : FastJsonUtil.toMap(requestCase.getRequest_configs()),
                                "null".equals(requestCase.getRequest_headers())|| null == requestCase.getRequest_headers() ? null : FastJsonUtil.toMap(requestCase.getRequest_headers()),
                                requestCase.getRequest_type(),
                                requestCase.getContent_type(),
                                requestCase.getUrl()
                        )
                );
                break;
            }
            case Constants.VERIFICATION: {
                VerificationCase verificationCase = verificationCaseMapper.SelOne(featureCase.getCase_id());
                List<String> parameters = FastJsonUtil.toList(verificationCase.getParameters());
                if (Constants.COMPARE.equals(verificationCase.getVerification_type())) {
                    FastJsonUtil.toList(verificationCase.getParameters());
                    testFlowManager.addBuffer(
                            verificationCase.getCase_name(),
                    testFlowManager.verify(parameters.get(0), parameters.get(1)));
                }
                else if (Constants.XPATHCOMPARE.equals(verificationCase.getVerification_type())) {
                    testFlowManager.addBuffer(
                            verificationCase.getCase_name(),
                    testFlowManager.verify(parameters.get(0), parameters.get(1), parameters.get(2)));
                }
                else if (Constants.OBJCOMPARE.equals(verificationCase.getVerification_type())) {
                    testFlowManager.addBuffer(
                            verificationCase.getCase_name(),
                    testFlowManager.verify(parameters.get(0), parameters.get(1), parameters.get(2), parameters.get(3)));
                }
                break;
            }
        }
    }

    /**
     * assertion
     *
     * @param request
     * @return
     * @throws Exception
     */
    private Status assertion(CreateFeatureRequest request) {
        Status status = new Status();
        status.setSuccess(true);
        status.setMessage("Create Success");
        if (null == request) {
            status.setSuccess(false);
            status.setErrorCode(110);
            status.setMessage(Lang.getErrorCode(110));
        } else if (null == request.getFeatureName()) {
            status.setSuccess(false);
            status.setErrorCode(101);
            status.setMessage(Lang.getErrorCode(101));
//        } else if (null != featureMapper.SelByName(request.getFeatureName().trim())) {
//            status.setSuccess(false);
//            status.setErrorCode(102);
//            status.setMessage(Lang.getErrorCode(102));
        } else if (status.getSuccess().equals(true)) {
            //case name 集合
            List<String> caseNameList = new ArrayList<>();
            //判断request case name 和 type是否为空
            if (null != request.getRequestCasesList() && !request.getRequestCasesList().isEmpty()) {
                List<String> emptyErrorName = new ArrayList<>();
                List<String> existErrorName = new ArrayList<>();
                List<CaseKey> caseKeyList = request.getRequestCasesList().stream().map(RequestCases::getKey).collect(Collectors.toList());
                caseNameList.addAll(caseKeyList.stream().map(CaseKey::getCaseName).collect(Collectors.toList()));
                for (CaseKey caseKey : caseKeyList) {
                    if ("".equals(caseKey.getCaseName()) || "".equals(caseKey.getCaseType())) {
                        emptyErrorName.add(caseKey.getCaseName());
                    } else {
                        if (!requestCaseMapper.Sel(caseKey.getCaseName()).isEmpty()) {
                            existErrorName.add(caseKey.getCaseName());
                        }
                    }
                }
                if (!emptyErrorName.isEmpty()) {
                    status.setSuccess(false);
                    status.setErrorCode(103);
                    status.setMessage(Lang.getErrorCode(103) + emptyErrorName);
                } else if (!existErrorName.isEmpty()) {
                    status.setSuccess(false);
                    status.setErrorCode(104);
                    status.setMessage(Lang.getErrorCode(104) + existErrorName);
                }
            }
            //判断database case name 和 type是否为空
            if (null != request.getDataBaseCasesList() && !request.getDataBaseCasesList().isEmpty()) {
                List<String> emptyErrorName = new ArrayList<>();
                List<String> existErrorName = new ArrayList<>();
                List<CaseKey> caseKeyList = request.getDataBaseCasesList().stream().map(DataBaseCases::getKey).collect(Collectors.toList());
                caseNameList.addAll(caseKeyList.stream().map(CaseKey::getCaseName).collect(Collectors.toList()));
                for (CaseKey caseKey : caseKeyList) {
                    if ("".equals(caseKey.getCaseName()) || "".equals(caseKey.getCaseType())) {
                        emptyErrorName.add(caseKey.getCaseName());
                    } else {
                        if (!dataBaseCaseMapper.Sel(caseKey.getCaseName()).isEmpty()) {
                            existErrorName.add(caseKey.getCaseName());
                        }
                    }
                }
                if (!emptyErrorName.isEmpty()) {
                    status.setSuccess(false);
                    status.setErrorCode(103);
                    status.setMessage(Lang.getErrorCode(103) + emptyErrorName);
                } else if (!existErrorName.isEmpty()) {
                    status.setSuccess(false);
                    status.setErrorCode(104);
                    status.setMessage(Lang.getErrorCode(104) + existErrorName);
                }
            }

            //判断parse case name 和 type是否为空
            if (null != request.getPaserCasesList() && !request.getPaserCasesList().isEmpty()) {
                List<String> emptyErrorName = new ArrayList<>();
                List<String> existErrorName = new ArrayList<>();
                List<CaseKey> caseKeyList = request.getPaserCasesList().stream().map(ParseCases::getKey).collect(Collectors.toList());
                caseNameList.addAll(caseKeyList.stream().map(CaseKey::getCaseName).collect(Collectors.toList()));
                for (CaseKey caseKey : caseKeyList) {
                    if ("".equals(caseKey.getCaseName()) || "".equals(caseKey.getCaseType())) {
                        emptyErrorName.add(caseKey.getCaseName());
                    } else {
                        if (!paserCaseMapper.Sel(caseKey.getCaseName()).isEmpty()) {
                            existErrorName.add(caseKey.getCaseName());
                        }
                    }
                }
                if (!emptyErrorName.isEmpty()) {
                    status.setSuccess(false);
                    status.setErrorCode(103);
                    status.setMessage(Lang.getErrorCode(103) + emptyErrorName);
                } else if (!existErrorName.isEmpty()) {
                    status.setSuccess(false);
                    status.setErrorCode(104);
                    status.setMessage(Lang.getErrorCode(104) + existErrorName);
                }
            }

            //判断verification case name 和 type是否为空
            if (null != request.getVerificationCasesList() && !request.getVerificationCasesList().isEmpty()) {
                List<String> emptyErrorName = new ArrayList<>();
                List<String> existErrorName = new ArrayList<>();
                List<CaseKey> caseKeyList = request.getVerificationCasesList().stream().map(VerificationCases::getKey).collect(Collectors.toList());
                caseNameList.addAll(caseKeyList.stream().map(CaseKey::getCaseName).collect(Collectors.toList()));
                for (CaseKey caseKey : caseKeyList) {
                    if ("".equals(caseKey.getCaseName()) || "".equals(caseKey.getCaseType())) {
                        emptyErrorName.add(caseKey.getCaseName());
                    } else {
                        if (!verificationCaseMapper.Sel(caseKey.getCaseName()).isEmpty()) {
                            existErrorName.add(caseKey.getCaseName());
                        }
                    }
                }
                if (!emptyErrorName.isEmpty()) {
                    status.setSuccess(false);
                    status.setErrorCode(103);
                    status.setMessage(Lang.getErrorCode(103) + emptyErrorName);
                } else if (!existErrorName.isEmpty()) {
                    status.setSuccess(false);
                    status.setErrorCode(104);
                    status.setMessage(Lang.getErrorCode(104) + existErrorName);
                }
            }

            //判断request next case name 和 type是否为空
//            if (null != request.getRequestCasesList() && !request.getRequestCasesList().isEmpty()) {
//                List<String> emptyErrorName = new ArrayList<>();
//                List<String> existErrorName = new ArrayList<>();
//                List<String> notExistErrorName = new ArrayList<>();
//                for (RequestCases requestCases : request.getRequestCasesList()) {
//                    if (null == requestCases.getNextKeys() || null == requestCases.getNextKeys()) {
//                        emptyErrorName.add(requestCases.getKey().getCaseName());
//                    } else {
//                        for (CaseKey caseKey : requestCases.getNextKeys()) {
//                            if ("".equals(caseKey.getCaseName()) || "".equals(caseKey.getCaseType())) {
//                                emptyErrorName.add(requestCases.getKey().getCaseName());
//                            } else {
//                                if (!requestCaseMapper.Sel(caseKey.getCaseName()).isEmpty()) {
//                                    existErrorName.add(caseKey.getCaseName());
//                                } else if (!caseNameList.contains(caseKey.getCaseName())) {
//                                    notExistErrorName.add(caseKey.getCaseName());
//                                }
//                            }
//                        }
//                    }
//                }
//                if (!emptyErrorName.isEmpty()) {
//                    status.setSuccess(false);
//                    status.setErrorCode(103);
//                    status.setMessage(Lang.getErrorCode(103) + emptyErrorName);
//                } else if (!existErrorName.isEmpty()) {
//                    status.setSuccess(false);
//                    status.setErrorCode(104);
//                    status.setMessage(Lang.getErrorCode(104) + existErrorName);
//                } else if (!notExistErrorName.isEmpty()) {
//                    status.setSuccess(false);
//                    status.setErrorCode(105);
//                    status.setMessage(Lang.getErrorCode(105) + notExistErrorName);
//                }
//
//            }

            //判断database next case name 和 type是否为空
            if (null != request.getDataBaseCasesList() && !request.getDataBaseCasesList().isEmpty()) {
                List<String> emptyErrorName = new ArrayList<>();
                List<String> existErrorName = new ArrayList<>();
                List<String> notExistErrorName = new ArrayList<>();
                for (DataBaseCases dataBaseCase : request.getDataBaseCasesList()) {
                    if (null == dataBaseCase.getNextKeys() || null == dataBaseCase.getNextKeys()) {
                        emptyErrorName.add(dataBaseCase.getKey().getCaseName());
                    } else {
                        for (CaseKey caseKey : dataBaseCase.getNextKeys()) {
                            if ("".equals(caseKey.getCaseName()) || "".equals(caseKey.getCaseType())) {
                                emptyErrorName.add(dataBaseCase.getKey().getCaseName());
                            } else {
                                if (!dataBaseCaseMapper.Sel(caseKey.getCaseName()).isEmpty()) {
                                    existErrorName.add(caseKey.getCaseName());
                                } else if (!caseNameList.contains(caseKey.getCaseName())) {
                                    notExistErrorName.add(caseKey.getCaseName());
                                }
                            }
                        }
                    }
                }

                if (!emptyErrorName.isEmpty()) {
                    status.setSuccess(false);
                    status.setErrorCode(103);
                    status.setMessage(Lang.getErrorCode(103) + emptyErrorName);
                } else if (!existErrorName.isEmpty()) {
                    status.setSuccess(false);
                    status.setErrorCode(104);
                    status.setMessage(Lang.getErrorCode(104) + existErrorName);
                } else if (!notExistErrorName.isEmpty()) {
                    status.setSuccess(false);
                    status.setErrorCode(105);
                    status.setMessage(Lang.getErrorCode(105) + notExistErrorName);
                }

            }


            //判断parse next case name 和 type是否为空
            if (null != request.getPaserCasesList() && !request.getPaserCasesList().isEmpty()) {
                List<String> emptyErrorName = new ArrayList<>();
                List<String> existErrorName = new ArrayList<>();
                List<String> notExistErrorName = new ArrayList<>();
                for (ParseCases parseCases : request.getPaserCasesList()) {
                    if (null == parseCases.getNextKeys() || null == parseCases.getNextKeys()) {
                        emptyErrorName.add(parseCases.getKey().getCaseName());
                    } else {
                        for (CaseKey caseKey : parseCases.getNextKeys()) {
                            if ("".equals(caseKey.getCaseName()) || "".equals(caseKey.getCaseType())) {
                                emptyErrorName.add(parseCases.getKey().getCaseName());
                            } else {
                                if (!paserCaseMapper.Sel(caseKey.getCaseName()).isEmpty()) {
                                    existErrorName.add(caseKey.getCaseName());
                                } else if (!caseNameList.contains(caseKey.getCaseName())) {
                                    notExistErrorName.add(caseKey.getCaseName());
                                }
                            }
                        }
                    }
                }
                if (!emptyErrorName.isEmpty()) {
                    status.setSuccess(false);
                    status.setErrorCode(103);
                    status.setMessage(Lang.getErrorCode(103) + emptyErrorName);
                } else if (!existErrorName.isEmpty()) {
                    status.setSuccess(false);
                    status.setErrorCode(104);
                    status.setMessage(Lang.getErrorCode(104) + existErrorName);
                } else if (!notExistErrorName.isEmpty()) {
                    status.setSuccess(false);
                    status.setErrorCode(105);
                    status.setMessage(Lang.getErrorCode(105) + notExistErrorName);
                }
            }


            //判断verification next case name 和 type是否为空
            if (null != request.getVerificationCasesList() && !request.getVerificationCasesList().isEmpty()) {
                List<String> emptyErrorName = new ArrayList<>();
                List<String> existErrorName = new ArrayList<>();
                List<String> notExistErrorName = new ArrayList<>();
                for (VerificationCases verificationCases : request.getVerificationCasesList()) {
                    if (null == verificationCases.getNextKeys() || null == verificationCases.getNextKeys()) {
                        emptyErrorName.add(verificationCases.getKey().getCaseName());
                    } else {
                        for (CaseKey caseKey : verificationCases.getNextKeys()) {
                            if ("".equals(caseKey.getCaseName()) || "".equals(caseKey.getCaseType())) {
                                emptyErrorName.add(verificationCases.getKey().getCaseName());
                            } else {
                                if (!verificationCaseMapper.Sel(caseKey.getCaseName()).isEmpty()) {
                                    existErrorName.add(caseKey.getCaseName());
                                } else if (!caseNameList.contains(caseKey.getCaseName())) {
                                    notExistErrorName.add(caseKey.getCaseName());
                                }
                            }
                        }
                    }
                }
                if (!emptyErrorName.isEmpty()) {
                    status.setSuccess(false);
                    status.setErrorCode(103);
                    status.setMessage(Lang.getErrorCode(103) + emptyErrorName);
                } else if (!existErrorName.isEmpty()) {
                    status.setSuccess(false);
                    status.setErrorCode(104);
                    status.setMessage(Lang.getErrorCode(104) + existErrorName);
                } else if (!notExistErrorName.isEmpty()) {
                    status.setSuccess(false);
                    status.setErrorCode(105);
                    status.setMessage(Lang.getErrorCode(105) + notExistErrorName);
                }

            }
        }
        return status;
    }

    /**
     * 获取结果feature
     *
     * @param id
     * @return
     */
    @Override
    public ResultFeatureResponse resultFeature(Long id) {
        TestFlowManager testFlowManager = new TestFlowManager();
        Status status = new Status();
        status.setSuccess(true);
        ResultFeatureResponse rsp = new ResultFeatureResponse();
        List<ResultCase> resultCaseList = new ArrayList<>();
        List<FeatureResult> featureResultList = featureResultMapper.selByFid(id);
        for (int i = 0; i < featureResultList.size(); i ++) {
            ResultCase resultCase = new ResultCase();
            resultCase.setIndex(i);
            resultCase.setAssertion(testFlowManager.getBuffer(featureResultList.get(i).getAssertion_key()));
            resultCase.setInfo(testFlowManager.getBuffer(featureResultList.get(i).getLog_key()));
            resultCaseList.add(resultCase);
        }
        rsp.setStatus(status);
        rsp.setResultCaseList(resultCaseList);
        return rsp;
    }
}
