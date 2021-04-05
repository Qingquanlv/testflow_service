package com.github.qingquanlv.testflow_service_api.service.impl;

import com.github.qingquanlv.testflow_service_api.common.Constants;
import com.github.qingquanlv.testflow_service_api.common.Utils;
import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.cases.CaseKey;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.createdatabase.CreateDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.createdatabase.CreateDataBaseCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.deletedatabase.DeleteDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.deletedatabase.DeleteDataBaseCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.querydatabase.QueryDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.querydatabase.QueryDataBaseCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.updatedatabase.UpdateDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.updatedatabase.UpdateDataBaseCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.cases.execcase.ExecCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.execcase.ExecCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.parameter.Parameter;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.*;
import com.github.qingquanlv.testflow_service_api.mapper.*;
import com.github.qingquanlv.testflow_service_api.service.CaseService;
import com.github.qingquanlv.testflow_service_biz.TestFlowManager;
import com.github.qingquanlv.testflow_service_biz.utilities.FastJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author Qingquan Lv
 * @Date 2020/12/24 22:51
 * @Version 1.0
 */
@Service
public class CaseServiceImpl implements CaseService {

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
     * 执行Case
     *
     * @param request
     * @return
     */
    @Override
    public ExecCaseResponse execCase(ExecCaseRequest request){
        ExecCaseResponse rsp = new ExecCaseResponse();
        Status status = new Status();
        status.setSuccess(true);
        rsp.setStatus(status);
        CaseKey caseKey = new CaseKey();
        caseKey.setCaseId(request.getCase_id());
        caseKey.setCaseType(request.getCase_type());
        //buffer 公共key
        String publicKey = Utils.hashKeyForDisk(String.format("%s%s",
                System.currentTimeMillis(),
                UUID.randomUUID().toString()));
        TestFlowManager testFlowManager = new TestFlowManager(publicKey);
        List<Parameter> parameterList = new ArrayList<>();
        //输入参数
        if (!CollectionUtils.isEmpty(request.getParameters())) {
            for (String key : request.getParameters().keySet()) {
                Parameter parameter = new Parameter();
                testFlowManager.addBuffer(key, request.getParameters().get(key));
                parameter.setParameter_value(key);
                parameter.setParameter_key(request.getParameters().get(key));
                parameterList.add(parameter);
            }
        }
        rsp.setParameters(parameterList);
        //忽略大小写
        switch (request.getCase_type().toLowerCase()) {
            case Constants.DATABASE: {
                DatabaseCase databaseCase = dataBaseCaseMapper.SelOne(request.getCase_id());
                caseKey.setCaseName(databaseCase.getCase_name());
                rsp.setResult(testFlowManager.queryDataBase(databaseCase.getCase_name(),
                                databaseCase.getSql()));
                break;
            }
            case Constants.PARSE: {
                PaserCase parseCases = paserCaseMapper.SelOne(request.getCase_id());
                caseKey.setCaseName(parseCases.getCase_name());
                rsp.setResult(testFlowManager.sourceParse(parseCases.getCase_name(),
                                parseCases.getCvt_method_source(),
                                FastJsonUtil.toList(parseCases.getParameters())));
                break;
            }
            case Constants.REQUEST: {
                RequestCase requestCase = requestCaseMapper.SelOne(request.getCase_id());
                caseKey.setCaseName(requestCase.getCase_name());
                rsp.setResult(testFlowManager.sendRequest(requestCase.getCase_name(),
                                requestCase.getRequest_body(),
                                "null".equals(requestCase.getRequest_configs())||null == requestCase.getRequest_configs() ? null : FastJsonUtil.toMap(requestCase.getRequest_configs()),
                                "null".equals(requestCase.getRequest_headers())|| null == requestCase.getRequest_headers() ? null : FastJsonUtil.toMap(requestCase.getRequest_headers()),
                                requestCase.getRequest_type(),
                                requestCase.getContent_type(),
                                requestCase.getUrl()));
                break;
            }
            case Constants.VERIFICATION: {
                VerificationCase verificationCase = verificationCaseMapper.SelOne(request.getCase_id());
                caseKey.setCaseName(verificationCase.getCase_name());
                List<String> parameters = FastJsonUtil.toList(verificationCase.getParameters());
                if (Constants.COMPARE.equals(verificationCase.getVerification_type())) {
                    FastJsonUtil.toList(verificationCase.getParameters());
                    rsp.setResult(testFlowManager.verify(parameters.get(0), parameters.get(1)));
                }
                else if (Constants.XPATHCOMPARE.equals(verificationCase.getVerification_type())) {
                    rsp.setResult(testFlowManager.verify(parameters.get(0), parameters.get(1), parameters.get(2)));
                }
                else if (Constants.OBJCOMPARE.equals(verificationCase.getVerification_type())) {
                    rsp.setResult(testFlowManager.verify(parameters.get(0), parameters.get(1), parameters.get(2), parameters.get(3)));
                }
                break;
            }
        }
        rsp.setCaseKey(caseKey);
        testFlowManager.deposed();
        return rsp;
    }

}
