package com.github.qingquanlv.testflow_service.service.impl;

import com.github.qingquanlv.testflow_service.entity.request.RequestRequest;
import com.github.qingquanlv.testflow_service.entity.request.RequestResponce;
import com.github.qingquanlv.testflow_service.service.StepService;
import com.github.qingquanlv.testflow_service.testflow.TestFlowManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return responce;

    }

}
