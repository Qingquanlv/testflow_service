package com.github.qingquanlv.testflow_service_api.service.impl;

import com.github.qingquanlv.testflow_service_api.DAO.*;
import com.github.qingquanlv.testflow_service_api.common.Lang;
import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.cases.Cases;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.DataBaseCases;
import com.github.qingquanlv.testflow_service_api.entity.cases.parse.ParseCases;
import com.github.qingquanlv.testflow_service_api.entity.cases.verification.VerificationCases;
import com.github.qingquanlv.testflow_service_api.entity.feature.FeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature.FeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.cases.request.*;
import com.github.qingquanlv.testflow_service_api.entity.savefeature.OperationEnum;
import com.github.qingquanlv.testflow_service_api.entity.savefeature.SaveFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.savefeature.SaveFeatureResponse;
import com.github.qingquanlv.testflow_service_api.service.StepService;
import com.github.qingquanlv.testflow_service_biz.TestFlowManager;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StepServiceImpl implements StepService {

    @Autowired
    private FeatureDao featureDao;

    @Autowired
    private DatabaseCaseDao databaseCaseDao;

    @Autowired
    private MethodCaseDao methodCaseDao;

    @Autowired
    private PaserCaseDao paserCaseDao;

    @Autowired
    private RequestCasesDao requestCasesDao;

    /**
     *
     * @param request
     * @return
     */
    @Override
    public FeatureResponse executeFeature(FeatureRequest request) {
        FeatureResponse FeatureRsp = new FeatureResponse();
        TestFlowManager testFlowManager = new TestFlowManager();
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
                    int finalI = i;
                    if (!CollectionUtils.isEmpty(cases.getRequestCases())
                            && null != cases.getRequestCases().stream().filter(item -> item.getIndex() == indexList.get(finalI)).findFirst().orElse(null)) {
                        RequestCases requestCase = cases.getRequestCases().stream().filter(item -> item.getIndex() == indexList.get(finalI)).findFirst().orElse(null);
                        //拼装configMap
                        HashMap<String, String> configMap = new HashMap<>();
                        for (RequestConfig config : requestCase.getConfigs()) {
                            configMap.put(config.getKey(), config.getValue());
                        }
                        //拼装headerMap
                        HashMap<String, String> headerMap = new HashMap<>();
                        for (RequestConfig config : requestCase.getConfigs()) {
                            headerMap.put(config.getKey(), config.getValue());
                        }
                        //使用testflow执行request请求
                        String name = requestCase.getName() + finalI;
                        testFlowManager.addBuffer(name, testFlowManager.sendRequest(requestCase.getRequestId(),
                                requestCase.getRequestBody(),
                                configMap,
                                headerMap,
                                requestCase.getRequestType(),
                                requestCase.getContentType(),
                                requestCase.getUrl()));

                    } else if (!CollectionUtils.isEmpty(cases.getParserCases())
                            && null != cases.getParserCases().stream().filter(item -> item.getIndex() == indexList.get(finalI)).findFirst().orElse(null)) {
                        ParseCases parseCase = cases.getParserCases().stream().filter(item -> item.getIndex() == indexList.get(finalI)).findFirst().orElse(null);
                        //使用testflow执行request请求
                        String name = parseCase.getName() + finalI;
                        testFlowManager.addBuffer(name, testFlowManager.sourceParse(parseCase.getCvtMethodSource(),
                                parseCase.getCvtMethodName(),
                                parseCase.getReturnType(),
                                parseCase.getParameters()));

                    } else if (!CollectionUtils.isEmpty(cases.getDataBaseCases())
                            && null != cases.getDataBaseCases().stream().filter(item -> item.getIndex() == indexList.get(1)).findFirst().orElse(null)) {
                        DataBaseCases dataBaseCase = cases.getDataBaseCases().stream().filter(item -> item.getIndex() == indexList.get(finalI)).findFirst().orElse(null);
                        //使用testflow执行request请求
                        String name = dataBaseCase.getName() + finalI;
                        //使用testflow查询DB数据
                        testFlowManager.addBuffer(name, testFlowManager.queryDataBase(dataBaseCase.getName()));

                    } else if (!CollectionUtils.isEmpty(cases.getVerificationCases())
                            && null != cases.getVerificationCases().stream().filter(item -> item.getIndex() == indexList.get(1)).findFirst().orElse(null)) {
                        VerificationCases verificationCase = cases.getVerificationCases().stream().filter(item -> item.getIndex() == indexList.get(finalI)).findFirst().orElse(null);
                        //使用testflow执行request请求
                        String name = verificationCase.getName() + finalI;
                        if ("string".equals(verificationCase.getVerifyType())) {
                            testFlowManager.addBuffer(name, testFlowManager.verify(verificationCase.getParameters().get(0), verificationCase.getParameters().get(1)));
                        }
                        if ("key".equals(verificationCase.getVerifyType())) {
                            testFlowManager.addBuffer(name, testFlowManager.verify(verificationCase.getParameters().get(0), verificationCase.getParameters().get(1), verificationCase.getParameters().get(2)));
                        }
                        if ("object".equals(verificationCase.getVerifyType())) {
                            testFlowManager.addBuffer(name, testFlowManager.verify(verificationCase.getParameters().get(0), verificationCase.getParameters().get(1), verificationCase.getParameters().get(2), verificationCase.getParameters().get(3), verificationCase.getParameters().get(4)));
                        }
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
            testFlowManager.deposed();
        }
        FeatureRsp.setStatus(status);
        return FeatureRsp;
    }

    /**
     *
     * @param request
     * @return
     */
    @Override
    public SaveFeatureResponse saveFeature(SaveFeatureRequest request) {
        SaveFeatureResponse response = new SaveFeatureResponse();
        if (OperationEnum.ADD.equals(request.getOperation())) {
            addFeature(request);
        }
        else if (OperationEnum.UPDATE.equals(request.getOperation())) {
            featureDao.addTestFeatures();
        }
        else if (OperationEnum.DELETE.equals(request.getOperation())) {
            featureDao.addTestFeatures();
        }
        return response;
    }

    /**
     *
     * @param request
     */
    private void addFeature(SaveFeatureRequest request) {
        if (!CollectionUtils.isEmpty(request.getCases().getRequestCases())) {
            requestCasesDao.addRequestCases(request.getCases().getRequestCases());
        }


    }

    private SqlSession getSession() {
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory.openSession();
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

    public void doxxxxx(){
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        FeatureDao userDao=getSession().getMapper(FeatureDao.class);
        Transaction newTransaction=transactionFactory.newTransaction(getSession().getConnection());
        try {
            userDao.insert(xxx);
            userDao.update(xxx);
        } catch (Exception e) {
            newTransaction.rollback();
            e.printStackTrace();
        } finally {
            newTransaction.close();
        }
    }
}
