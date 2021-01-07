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
        status.setSuccess(true);

        try {
            status = assertion(request);
            //如果request报文入参没有问题
            if (status.getSuccess()) {
                List<FeatureCase> featureCaseList = new ArrayList<>();
                List<RequestCase> requestCaseList = new ArrayList<>();
                List<DatabaseCase> databaseCaseList = new ArrayList<>();
                List<PaserCase> paserCaseList = new ArrayList<>();
                List<VerificationCase> verificationCaseList = new ArrayList<>();

                //插入feature
                Feature feature = new Feature();
                feature.setFeature_name(request.getFeatureName());
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
                        FeatureCase featureCase = new FeatureCase();
                        featureCase.setCase_type("request");
                        featureCase.setNext_case(FastJsonUtil.toJson(item.getNextKeys()));
                        featureCaseList.add(featureCase);
                    }
                }
                //database case
                if (null != request.getDataBaseCasesList() && !request.getDataBaseCasesList().isEmpty()) {
                    for (DataBaseCases item : request.getDataBaseCasesList()) {
                        DatabaseCase databaseCase = new DatabaseCase();
                        databaseCase.setCase_name(item.getKey().getCaseName());
                        databaseCase.setSql(item.getSql());
                        databaseCaseList.add(databaseCase);
                        FeatureCase featureCase = new FeatureCase();
                        featureCase.setCase_type("database");
                        featureCase.setNext_case(FastJsonUtil.toJson(item.getNextKeys()));
                        featureCaseList.add(featureCase);
                    }
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
                        FeatureCase featureCase = new FeatureCase();
                        featureCase.setCase_type("parse");
                        featureCase.setNext_case(FastJsonUtil.toJson(item.getNextKeys()));
                        featureCaseList.add(featureCase);
                    }
                }
                //verification case
                if (null != request.getVerificationCasesList() && !request.getVerificationCasesList().isEmpty()) {
                    for (VerificationCases item : request.getVerificationCasesList()) {
                        VerificationCase verificationCase = new VerificationCase();
                        verificationCase.setCase_name(item.getKey().getCaseName());
                        verificationCase.setVerification_type(item.getVerifyType());
                        verificationCase.setParameters(FastJsonUtil.toJson(item.getParameters()));
                        verificationCaseList.add(verificationCase);
                        FeatureCase featureCase = new FeatureCase();
                        featureCase.setCase_type("verificaiton");
                        featureCase.setNext_case(FastJsonUtil.toJson(item.getNextKeys()));
                        featureCaseList.add(featureCase);
                    }
                }
                featureMapper.Insert(feature);
                featureCaseMapper.Insert(featureCaseList);
                dataBaseCaseMapper.Insert(databaseCaseList);
                verificationCaseMapper.Insert(verificationCaseList);
                paserCaseMapper.Insert(paserCaseList);
                requestCaseMapper.Insert(requestCaseList);
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


    /**
     * 执行feature
     *
     * @param id
     * @return
     */
    @Override
    public ExecFeatureResponse execFeature(Long id) {
        ExecFeatureResponse rsp = new ExecFeatureResponse();
        List<FeatureCase> featureCaseList = featureCaseMapper.SelecltByFeatureId(id);
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

            List<CaseKey> caseKeys = FastJsonUtil.toListBean(featureCase.getNext_case(), CaseKey.class);
            for (CaseKey caseKey : caseKeys) {
                //入度减一
                FeatureCase caseItem = featureCaseList.stream().filter(
                        item->item.getNext_case().equals(caseKey.getCaseType())
                                && item.getCase_name().equals(caseKey.getCaseName())).findFirst().orElse(null);
                inDegreeMap.put(caseItem.getId(), inDegreeMap.get(caseItem.getId() - 1));
                //如果入度为0
                if(inDegreeMap.get(caseItem.getId()) == 0){
                    s1.offer(caseItem.getId());
                }
            }
        }
    }

    private void initMap(List<FeatureCase> featureCaseList, Map<Long, Integer> inDegreeMap) {
        for (FeatureCase featureCase : featureCaseList) {
            List<CaseKey> caseKeys = FastJsonUtil.toListBean(featureCase.getNext_case(), CaseKey.class);
            for (CaseKey caseKey : caseKeys) {
                FeatureCase caseItem = featureCaseList.stream().filter(
                        item->item.getNext_case().equals(caseKey.getCaseType())
                                && item.getCase_name().equals(caseKey.getCaseName())).findFirst().orElse(null);
                inDegreeMap.put(caseItem.getId(), inDegreeMap.get(caseItem.getId() + 1));
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
                DatabaseCase databaseCase = dataBaseCaseMapper.SelOne(featureCase.getCase_name());
                testFlowManager.addBuffer(
                        publicKey + Utils.hashKeyForDisk("DATABASE" + databaseCase.getCase_name()),
                        testFlowManager.queryDataBase(databaseCase.getSql())
                );
                break;
            }
            case "PARSE": {
                PaserCase parseCases = paserCaseMapper.SelOne(featureCase.getCase_name());
                testFlowManager.addBuffer(
                        publicKey + Utils.hashKeyForDisk("PARSE" + parseCases.getCase_name()),
                        testFlowManager.sourceParse(parseCases.getCase_name(),
                                parseCases.getCvt_method_source(),
                                parseCases.getReturn_type(),
                                parseCases.getParameters()
                        )
                );
                break;
            }
            case "REQUEST": {
                RequestCase requestCase = requestCaseMapper.SelOne(featureCase.getCase_name());
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
                VerificationCase verificationCase = verificationCaseMapper.SelOne(featureCase.getCase_name());
                if ("COMPARE".equals(verificationCase.getVerification_type())) {
                    testFlowManager.verify(verificationCase.getParameters(),verificationCase.getParameters());
                }
                else if ("XPATHCOMPARE".equals(verificationCase.getVerification_type())) {
                    testFlowManager.verify(verificationCase.getParameters(), verificationCase.getParameters(),verificationCase.getParameters());
                }
                else if ("OBJCOMPARE".equals(verificationCase.getVerification_type())) {
                    testFlowManager.verify(verificationCase.getParameters(), verificationCase.getParameters(), verificationCase.getParameters(), verificationCase.getParameters(), verificationCase.getParameters())
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
        }
        //判断request case name 和 type是否为空
        if (null != request.getRequestCasesList() && !request.getRequestCasesList().isEmpty()) {
            List<String> emptyErrorName = new ArrayList<>();
            List<String> existErrorName = new ArrayList<>();
            List<CaseKey> caseKeyList = request.getRequestCasesList().stream().map(RequestCases::getKey).collect(Collectors.toList());
            for (CaseKey caseKey : caseKeyList) {
                if ("".equals(caseKey.getCaseName()) || "".equals(caseKey.getCaseType())) {
                    emptyErrorName.add(caseKey.getCaseName());
                }
                else {
                    if(!requestCaseMapper.Sel(caseKey.getCaseName()).isEmpty()) {
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
            for (RequestCases requestCases : request.getRequestCasesList()) {
                if (null == requestCases.getNextKeys() || null == requestCases.getNextKeys()) {
                    emptyErrorName.add(requestCases.getKey().getCaseName());
                }
                else {
                    for (CaseKey caseKey : requestCases.getNextKeys()) {
                        if ("".equals(caseKey.getCaseName()) || "".equals(caseKey.getCaseType())) {
                            emptyErrorName.add(requestCases.getKey().getCaseName());
                        }
                        else {
                            if(!requestCaseMapper.Sel(caseKey.getCaseName()).isEmpty()) {
                                existErrorName.add(caseKey.getCaseName());
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
            }
        }
        //判断database case name 和 type是否为空
        if (null != request.getDataBaseCasesList() && !request.getDataBaseCasesList().isEmpty()) {
            List<String> emptyErrorName = new ArrayList<>();
            List<String> existErrorName = new ArrayList<>();
            List<CaseKey> caseKeyList = request.getDataBaseCasesList().stream().map(DataBaseCases::getKey).collect(Collectors.toList());
            for (CaseKey caseKey : caseKeyList) {
                if ("".equals(caseKey.getCaseName()) || "".equals(caseKey.getCaseType())) {
                    emptyErrorName.add(caseKey.getCaseName());
                }
                else {
                    if(!dataBaseCaseMapper.Sel(caseKey.getCaseName()).isEmpty()) {
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
        //判断database next case name 和 type是否为空
        if (null != request.getDataBaseCasesList() && !request.getDataBaseCasesList().isEmpty()) {
            List<String> emptyErrorName = new ArrayList<>();
            List<String> existErrorName = new ArrayList<>();
            for (DataBaseCases dataBaseCase : request.getDataBaseCasesList()) {
                if (null == dataBaseCase.getNextKeys() || null == dataBaseCase.getNextKeys()) {
                    emptyErrorName.add(dataBaseCase.getKey().getCaseName());
                }
                else {
                    for (CaseKey caseKey : dataBaseCase.getNextKeys()) {
                        if ("".equals(caseKey.getCaseName()) || "".equals(caseKey.getCaseType())) {
                            emptyErrorName.add(dataBaseCase.getKey().getCaseName());
                        }
                        else {
                            if(!dataBaseCaseMapper.Sel(caseKey.getCaseName()).isEmpty()) {
                                existErrorName.add(caseKey.getCaseName());
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
            }
        }
        //判断parse case name 和 type是否为空
        if (null != request.getPaserCasesList() && !request.getPaserCasesList().isEmpty()) {
            List<String> emptyErrorName = new ArrayList<>();
            List<String> existErrorName = new ArrayList<>();
            List<CaseKey> caseKeyList = request.getPaserCasesList().stream().map(ParseCases::getKey).collect(Collectors.toList());
            for (CaseKey caseKey : caseKeyList) {
                if ("".equals(caseKey.getCaseName()) || "".equals(caseKey.getCaseType())) {
                    emptyErrorName.add(caseKey.getCaseName());
                }
                else {
                    if(!paserCaseMapper.Sel(caseKey.getCaseName()).isEmpty()) {
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
        //判断parse next case name 和 type是否为空
        if (null != request.getPaserCasesList() && !request.getPaserCasesList().isEmpty()) {
            List<String> emptyErrorName = new ArrayList<>();
            List<String> existErrorName = new ArrayList<>();
            for (ParseCases parseCases : request.getPaserCasesList()) {
                if (null == parseCases.getNextKeys() || null == parseCases.getNextKeys()) {
                    emptyErrorName.add(parseCases.getKey().getCaseName());
                }
                else {
                    for (CaseKey caseKey : parseCases.getNextKeys()) {
                        if ("".equals(caseKey.getCaseName()) || "".equals(caseKey.getCaseType())) {
                            emptyErrorName.add(parseCases.getKey().getCaseName());
                        }
                        else {
                            if(!paserCaseMapper.Sel(caseKey.getCaseName()).isEmpty()) {
                                existErrorName.add(caseKey.getCaseName());
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
            }
        }
        //判断verification case name 和 type是否为空
        if (null != request.getVerificationCasesList() && !request.getVerificationCasesList().isEmpty()) {
            List<String> emptyErrorName = new ArrayList<>();
            List<String> existErrorName = new ArrayList<>();
            List<CaseKey> caseKeyList = request.getVerificationCasesList().stream().map(VerificationCases::getKey).collect(Collectors.toList());
            for (CaseKey caseKey : caseKeyList) {
                if ("".equals(caseKey.getCaseName()) || "".equals(caseKey.getCaseType())) {
                    emptyErrorName.add(caseKey.getCaseName());
                }
                else {
                    if(!verificationCaseMapper.Sel(caseKey.getCaseName()).isEmpty()) {
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
        //判断verification next case name 和 type是否为空
        if (null != request.getVerificationCasesList() && !request.getVerificationCasesList().isEmpty()) {
            List<String> emptyErrorName = new ArrayList<>();
            List<String> existErrorName = new ArrayList<>();
            for (VerificationCases verificationCases : request.getVerificationCasesList()) {
                if (null == verificationCases.getNextKeys() || null == verificationCases.getNextKeys()) {
                    emptyErrorName.add(verificationCases.getKey().getCaseName());
                }
                else {
                    for (CaseKey caseKey : verificationCases.getNextKeys()) {
                        if ("".equals(caseKey.getCaseName()) || "".equals(caseKey.getCaseType())) {
                            emptyErrorName.add(verificationCases.getKey().getCaseName());
                        }
                        else {
                            if(!verificationCaseMapper.Sel(caseKey.getCaseName()).isEmpty()) {
                                existErrorName.add(caseKey.getCaseName());
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
            }
        }
        return status;
    }
}
