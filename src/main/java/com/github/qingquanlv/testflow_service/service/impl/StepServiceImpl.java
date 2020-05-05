package com.github.qingquanlv.testflow_service.service.impl;

import com.github.qingquanlv.testflow_service.entity.Status;
import com.github.qingquanlv.testflow_service.entity.request.*;
import com.github.qingquanlv.testflow_service.entity.testflowrequest.TestFlowRequest;
import com.github.qingquanlv.testflow_service.entity.testflowrequest.TestFlowResponce;
import com.github.qingquanlv.testflow_service.service.StepService;
import com.github.qingquanlv.testflow_service.testflow.TestFlowManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;

@Service
public class StepServiceImpl implements StepService {

    @Autowired
    private TestFlowManager testFlowManager;

    /**
     * 发送请求并且保存到返回到redis
     *
     * @param request 更新的step实体
     * @return UpdateFeatureInfoResponce 返回response
     */
    @Override
    public RequestResponce request(RequestRequest request) {
        RequestResponce responce = new RequestResponce();
        Status status = new Status();
        String errorMsg = "";
        String responceStr = "";
        try {
            //如果headers不为空
            HashMap<String, String> headerMap = new HashMap<>();
            for (RequestHeader header : request.getHeaders()) {
                if (headerMap.containsKey(header.getKey())) {
                    headerMap.put(header.getKey(), header.getValue());
                }
            }
            //responceStr = TestFlowManager.runner().sendRequest(request.get, request.getBody(), headerMap, request.getRequestType(), request.getContentType(), request.getUrl(), request.getKey());
            //缓存到redis
            //BufferManager.addBufferByKey(responce, responceStr);
        }
        catch (Exception ex) {
            TestFlowManager.runner().deposed();
            errorMsg = String.format("send Request failed: %s", ex);
        }
        if (StringUtils.isEmpty(errorMsg)) {
            status.setSuccess(true);
        }
        else {
            status.setSuccess(false);
            status.setErrorCode(001);
            status.setMessage(errorMsg);
        }
        responce.setStatus(status);
        responce.setContent(responceStr);
        return responce;
    }

    /**
     *
     * @param request
     * @return
     */
    @Override
    public TestFlowResponce TestFlow(TestFlowRequest request) {
        TestFlowResponce responce = new TestFlowResponce();
        Status status = new Status();
        String errorMsg = "";
        String responceStr = "";
        try {
            RequestRequest requestItem = request.getRequestRequest().get(0);
            if ("Request".equals(request.getRequestRequest().get(0))) {
                //配置map
                HashMap<String, String> configMap = new HashMap<>();
                for (RequestConfig config : requestItem.getConfigs()) {
                    configMap.put(config.getKey(), config.getValue());
                }
                //请求头map
                HashMap<String, String> headerMap = new HashMap<>();
                for (RequestHeader header : requestItem.getHeaders()) {
                    headerMap.put(header.getKey(), header.getValue());
                }
                //request方法
                TestFlowManager.runner().sendRequest(requestItem.getRequestId(),
                        requestItem.getRequestBody(),
                        configMap, headerMap, requestItem.getRequestType(),
                        requestItem.getContentType(), requestItem.getUrl());
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            TestFlowManager.runner().deposed();
        }
        return responce;
    }

}
