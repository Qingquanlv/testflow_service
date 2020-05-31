package com.github.qingquanlv.testflow_service.service.impl;

import com.github.qingquanlv.testflow_service.common.Lang;
import com.github.qingquanlv.testflow_service.entity.Status;
import com.github.qingquanlv.testflow_service.entity.cases.Cases;
import com.github.qingquanlv.testflow_service.entity.cases.database.DataBaseCases;
import com.github.qingquanlv.testflow_service.entity.cases.parse.ParseCases;
import com.github.qingquanlv.testflow_service.entity.cases.verification.VerificationCases;
import com.github.qingquanlv.testflow_service.entity.feature.FeatureRequest;
import com.github.qingquanlv.testflow_service.entity.feature.FeatureResponse;
import com.github.qingquanlv.testflow_service.entity.cases.request.*;
import com.github.qingquanlv.testflow_service.service.StepService;
import com.github.qingquanlv.testflow_service.testflow.TestFlowManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StepServiceImpl implements StepService {

    @Autowired
    private TestFlowManager testFlowManager;

    /**
     *
     * @param request
     * @return
     */
    @Override
    public FeatureResponse executeFeature(FeatureRequest request) {
        FeatureResponse FeatureRsp = new FeatureResponse();
        Status status = new Status();

        try {
            //校验报文入参
            status = assertion(request);
            //如果request报文入参没有问题
            if (status.getSuccess()) {
                Cases cases = request.getCases();
                //获取执行顺序index
                List<Integer> indexList = getIndexSort(cases);
                Collections.sort(indexList);
                //开始执行case
                for (int i = 0; i < indexList.size(); i++) {
                    if (!CollectionUtils.isEmpty(cases.getRequestCases())
                            && null != cases.getRequestCases().stream().filter(item -> item.getIndex() == indexList.get(1)).findFirst().orElse(null)) {

                    } else if (!CollectionUtils.isEmpty(cases.getParserCases())
                            && null != cases.getParserCases().stream().filter(item -> item.getIndex() == indexList.get(1)).findFirst().orElse(null)) {

                    } else if (!CollectionUtils.isEmpty(cases.getDataBaseCases())
                            && null != cases.getDataBaseCases().stream().filter(item -> item.getIndex() == indexList.get(1)).findFirst().orElse(null)) {

                    } else if (!CollectionUtils.isEmpty(cases.getVerificationCases())
                            && null != cases.getVerificationCases().stream().filter(item -> item.getIndex() == indexList.get(1)).findFirst().orElse(null)) {

                    }
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            status.setSuccess(false);
            status.setErrorCode(110);
            status.setMessage(Lang.getErrorCode(110));
        }
        finally {
            TestFlowManager.runner().deposed();
        }
        FeatureRsp.setStatus(status);
        return FeatureRsp;
    }

    /**
     * 根据Cases获取Index List
     *
     * @param cases
     * @return
     */
    private List<Integer> getIndexSort(Cases cases) {
        //拼装requestCases的index
        List<RequestCases> requestCases = cases.getRequestCases();
        List<Integer> indexSort = requestCases.stream().map(RequestCases::getIndex).collect(Collectors.toList());
        //拼装ParseCases的index
        List<ParseCases> parserCases = cases.getParserCases();
        indexSort.addAll(parserCases.stream().map(ParseCases::getIndex).collect(Collectors.toList()));
        //拼装requestCases的index
        List<DataBaseCases> dataBaseCases = cases.getDataBaseCases();
        indexSort.addAll(dataBaseCases.stream().map(DataBaseCases::getIndex).collect(Collectors.toList()));
        //拼装requestCases的index
        List<VerificationCases> verificationCases = cases.getVerificationCases();
        indexSort.addAll(verificationCases.stream().map(VerificationCases::getIndex).collect(Collectors.toList()));
        return indexSort;
    }

    /**
     * 校验报文入参
     *
     * @param request
     * @return
     * @throws Exception
     */
    private Status assertion(FeatureRequest request) throws Exception {
        Status status = new Status();
        Cases cases = request.getCases();
        //获取执行顺序index
        List<Integer> indexList =  getIndexSort(cases);
        HashSet<Integer> set = new HashSet<>(indexList);
        if (indexList.size() == set.size()) {
            status.setSuccess(false);
            status.setErrorCode(110);
            status.setMessage(Lang.getErrorCode(110));
        }
        return status;
    }

}
