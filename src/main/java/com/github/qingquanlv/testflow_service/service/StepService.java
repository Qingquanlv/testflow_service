package com.github.qingquanlv.testflow_service.service;

import com.github.qingquanlv.testflow_service.entity.request.RequestRequest;
import com.github.qingquanlv.testflow_service.entity.request.RequestResponce;
import com.github.qingquanlv.testflow_service.entity.testflowrequest.TestFlowRequest;
import com.github.qingquanlv.testflow_service.entity.testflowrequest.TestFlowResponce;

public interface StepService {

    /**
     * 发送请求并且保存到返回到redis
     *
     * @param request
     */
    RequestResponce request(RequestRequest request);

    /**
     * 执行TestFlow
     *
     * @param request
     */
    TestFlowResponce TestFlow(TestFlowRequest request);
}
