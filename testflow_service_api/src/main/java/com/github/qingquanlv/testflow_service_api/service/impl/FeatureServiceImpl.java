package com.github.qingquanlv.testflow_service_api.service.impl;

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
import com.github.qingquanlv.testflow_service_api.entity.feature.execfeature.ExecFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.*;
import com.github.qingquanlv.testflow_service_api.mapper.*;
import com.github.qingquanlv.testflow_service_api.service.FeatureService;
import com.github.qingquanlv.testflow_service_biz.TestFlowManager;
import com.github.qingquanlv.testflow_service_biz.utilities.FastJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    FeatureCaseMapper featureCaseMapper;

    @Autowired
    FeatureCaseNextCaseMapper featureCaseNextCaseMapper;

    @Autowired
    DataBaseCaseMapper dataBaseCaseMapper;

    @Autowired
    VerificationCaseMapper verificationCaseMapper;

    @Autowired
    PaserCaseMapper paserCaseMapper;

    @Autowired
    RequestCaseMapper requestCaseMapper;

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
            status = assertion(request);
            //如果request报文入参没有问题
            if (status.getSuccess()) {
                List<FeatureCaseNextCase> featureCaseNextCaseList = new ArrayList<>();
                List<RequestCase> requestCaseList = new ArrayList<>();
                List<DatabaseCase> databaseCaseList = new ArrayList<>();
                List<PaserCase> paserCaseList = new ArrayList<>();
                List<VerificationCase> verificationCaseList = new ArrayList<>();

                //插入feature
                Feature feature = new Feature();
                feature.setFeature_name(request.getFeatureName());
                featureMapper.Ins(feature);
                //插入request case
                if (null != request.getRequestCasesList() && !request.getRequestCasesList().isEmpty()) {
                    for (RequestCases item : request.getRequestCasesList()) {
                        RequestCase requstCase = new RequestCase();
                        requstCase.setCase_name(item.getKey().getCaseName());
                        requstCase.setContent_type(item.getContentType());
                        requstCase.setRequest_type(item.getRequestType());
                        requstCase.setRequest_body(item.getRequestBody());
                        requstCase.setUrl(item.getUrl());
                        requstCase.setRequest_configs(FastJsonUtil.toJson(item.getConfigs()));
                        requstCase.setRequest_headers(FastJsonUtil.toJson(item.getHeaders()));
                        requestCaseList.add(requstCase);
                    }
                    //插入request list
                    requestCaseMapper.Ins(requestCaseList);
                }
                //database case
                if (null != request.getDataBaseCasesList() && !request.getDataBaseCasesList().isEmpty()) {
                    for (DataBaseCases item : request.getDataBaseCasesList()) {
                        DatabaseCase databaseCase = new DatabaseCase();
                        databaseCase.setCase_name(item.getKey().getCaseName());
                        databaseCase.setSql(item.getSql());
                        databaseCaseList.add(databaseCase);
                    }
                    //插入database list
                    dataBaseCaseMapper.Ins(databaseCaseList);
                }
                //parse case
                if (null != request.getPaserCasesList() && !request.getPaserCasesList().isEmpty()) {
                    for (ParseCases item : request.getPaserCasesList()) {
                        PaserCase paserCase = new PaserCase();
                        paserCase.setCase_name(item.getKey().getCaseName());
                        paserCase.setCvt_method_source(item.getCvtMethodSource());
                        paserCase.setParameters(FastJsonUtil.toJson(item.getParameters()));
                        paserCase.setReturn_type(item.getReturnType());
                        paserCaseList.add(paserCase);
                    }
                    //插入Parse list
                    paserCaseMapper.Ins(paserCaseList);
                }
                //verification case
                if (null != request.getVerificationCasesList() && !request.getVerificationCasesList().isEmpty()) {
                    for (VerificationCases item : request.getVerificationCasesList()) {
                        VerificationCase verificationCase = new VerificationCase();
                        verificationCase.setCase_name(item.getKey().getCaseName());
                        verificationCase.setVerification_type(item.getVerifyType());
                        verificationCase.setParameters(FastJsonUtil.toJson(item.getParameters()));
                        verificationCaseList.add(verificationCase);
                    }
                    //插入verification list
                    verificationCaseMapper.Ins(verificationCaseList);
                }
                //feature-case
                for (RequestCases item : request.getRequestCasesList()) {
                    FeatureCase featureCase = new FeatureCase();
                    featureCase.setFeature_id(feature.getFeature_id());
                    RequestCase cs = requestCaseList.stream().filter(i->item.getKey().getCaseName().equals(i.getCase_name())).findFirst().orElse(null);
                    featureCase.setCase_id(cs.getCase_id());
                    featureCase.setCase_type("request");
                    featureCaseMapper.InsOne(featureCase);
                    for (CaseKey caseKey : item.getNextKeys()) {
                        FeatureCaseNextCase featureCaseNextCase = new FeatureCaseNextCase();
                        featureCaseNextCase.setFeature_case_id(featureCase.getId());
                        featureCaseNextCase.setCase_id(getCaseId(caseKey.getCaseType(), caseKey.getCaseName(), requestCaseList, databaseCaseList, paserCaseList,  verificationCaseList));
                        featureCaseNextCase.setCase_type(caseKey.getCaseType());
                        featureCaseNextCaseList.add(featureCaseNextCase);
                    }

                }
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
                        featureCaseNextCase.setCase_id(getCaseId(caseKey.getCaseType(), caseKey.getCaseName(), requestCaseList, databaseCaseList, paserCaseList,  verificationCaseList));
                        featureCaseNextCase.setCase_type(caseKey.getCaseType());
                        featureCaseNextCaseList.add(featureCaseNextCase);
                    }
                }
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
                        featureCaseNextCase.setCase_id(getCaseId(caseKey.getCaseType(), caseKey.getCaseName(), requestCaseList, databaseCaseList, paserCaseList,  verificationCaseList));
                        featureCaseNextCase.setCase_type(caseKey.getCaseType());
                        featureCaseNextCaseList.add(featureCaseNextCase);
                    }
                }
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
                        featureCaseNextCase.setCase_id(getCaseId(caseKey.getCaseType(), caseKey.getCaseName(), requestCaseList, databaseCaseList, paserCaseList,  verificationCaseList));
                        featureCaseNextCase.setCase_type(caseKey.getCaseType());
                        featureCaseNextCaseList.add(featureCaseNextCase);
                    }
                }
                //插入next feature case关联关系
                featureCaseNextCaseMapper.Ins(featureCaseNextCaseList);
            }
        } catch(Exception ex) {
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
     * 执行feature
     *
     * @param id
     * @return
     */
    @Override
    public ExecFeatureResponse execFeature(Long id) {
        ExecFeatureResponse rsp = new ExecFeatureResponse();
        List<FeatureCase> featureCaseList = featureCaseMapper.SelByFId(id);
        execFeature(featureCaseList);
        return rsp;
    }

    private void execFeature(List<FeatureCase> featureCaseList) {

        Map<Long, Integer> inDegreeMap = new HashMap<>();//储存入度
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
            Long n = s1.poll();//抛出队头，并且执行
            FeatureCase featureCase = featureCaseList.stream().filter(item->item.getId().equals(n)).findFirst().orElse(null);
            execCase(featureCase);

            List<FeatureCaseNextCase> nextCaseKeys = featureCaseNextCaseMapper.SelByFCId(featureCase.getId());
            for (FeatureCaseNextCase caseKey : nextCaseKeys) {
                //入度减一
                FeatureCase caseItem = featureCaseList.stream().filter(
                        item->item.getId().equals(caseKey.getFeature_case_id())).findFirst().orElse(null);
                inDegreeMap.put(caseItem.getId(), inDegreeMap.get(caseItem.getId()) - 1);
                //如果入度为0
                if(inDegreeMap.get(caseItem.getId()) == 0){
                    s1.offer(caseItem.getId());
                }
            }
        }
    }

    private void initMap(List<FeatureCase> featureCaseList, Map<Long, Integer> inDegreeMap) {
        for (FeatureCase featureCase : featureCaseList) {
            List<FeatureCaseNextCase> nextCaseKeys = featureCaseNextCaseMapper.SelByFCId(featureCase.getId());
            for (FeatureCaseNextCase caseKey : nextCaseKeys) {
                FeatureCase caseItem = featureCaseList.stream().filter(
                        item->item.getId().equals(caseKey.getFeature_case_id())).findFirst().orElse(null);
                inDegreeMap.put(caseItem.getId(), inDegreeMap.get(caseItem.getId()) + 1);
            }
        }
    }

    /**
     * 执行测试用例方法
     *
     * @param featureCase
     */
    private void execCase(FeatureCase featureCase) {
        TestFlowManager testFlowManager = new TestFlowManager();
        //buffer 公共key
        Long publicKey = System.currentTimeMillis();
        switch (featureCase.getCase_type()) {
            case "DATABASE": {
                DatabaseCase databaseCase = dataBaseCaseMapper.SelOne(featureCase.getCase_id());
                testFlowManager.addBuffer(
                        publicKey + Utils.hashKeyForDisk("DATABASE" + databaseCase.getCase_name()),
                        testFlowManager.queryDataBase(databaseCase.getSql())
                );
                break;
            }
            case "PARSE": {
                PaserCase parseCases = paserCaseMapper.SelOne(featureCase.getCase_id());
                testFlowManager.addBuffer(
                        publicKey + Utils.hashKeyForDisk("PARSE" + parseCases.getCase_name()),
                        testFlowManager.sourceParse(parseCases.getCase_name(),
                                parseCases.getCvt_method_source(),
                                parseCases.getReturn_type(),
                                FastJsonUtil.toList(parseCases.getParameters())
                        )
                );
                break;
            }
            case "REQUEST": {
                RequestCase requestCase = requestCaseMapper.SelOne(featureCase.getCase_id());
                testFlowManager.addBuffer(
                        publicKey + Utils.hashKeyForDisk("REQUEST" + requestCase.getCase_name()),
                        testFlowManager.sendRequest(requestCase.getCase_name(),
                                requestCase.getRequest_body(),
                                FastJsonUtil.toMap(requestCase.getRequest_configs()),
                                FastJsonUtil.toMap(requestCase.getRequest_headers()),
                                requestCase.getRequest_type(),
                                requestCase.getContent_type(),
                                requestCase.getUrl()
                        )
                );
                break;
            }
            case "VERIFICATION": {
                VerificationCase verificationCase = verificationCaseMapper.SelOne(featureCase.getCase_id());
                List<String> parameters = FastJsonUtil.toList(verificationCase.getParameters());
                if ("COMPARE".equals(verificationCase.getVerification_type())) {
                    FastJsonUtil.toList(verificationCase.getParameters());
                    testFlowManager.verify(parameters.get(0), parameters.get(1));
                }
                else if ("XPATHCOMPARE".equals(verificationCase.getVerification_type())) {
                    testFlowManager.verify(parameters.get(0), parameters.get(1), parameters.get(2));
                }
                else if ("OBJCOMPARE".equals(verificationCase.getVerification_type())) {
                    testFlowManager.verify(parameters.get(0), parameters.get(1), parameters.get(2), parameters.get(3), parameters.get(4));
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
    private Status assertion(CreateFeatureRequest request) throws Exception {
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
        } else if (null != featureMapper.Sel(request.getFeatureName().trim())) {
            status.setSuccess(false);
            status.setErrorCode(102);
            status.setMessage(Lang.getErrorCode(102));
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
            if (null != request.getRequestCasesList() && !request.getRequestCasesList().isEmpty()) {
                List<String> emptyErrorName = new ArrayList<>();
                List<String> existErrorName = new ArrayList<>();
                List<String> notExistErrorName = new ArrayList<>();
                for (RequestCases requestCases : request.getRequestCasesList()) {
                    if (null == requestCases.getNextKeys() || null == requestCases.getNextKeys()) {
                        emptyErrorName.add(requestCases.getKey().getCaseName());
                    } else {
                        for (CaseKey caseKey : requestCases.getNextKeys()) {
                            if ("".equals(caseKey.getCaseName()) || "".equals(caseKey.getCaseType())) {
                                emptyErrorName.add(requestCases.getKey().getCaseName());
                            } else {
                                if (!requestCaseMapper.Sel(caseKey.getCaseName()).isEmpty()) {
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
}
