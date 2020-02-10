package com.github.qingquanlv.testflow_service.service;

import com.github.qingquanlv.testflow_service.entity.request.RequestRequest;
import com.github.qingquanlv.testflow_service.entity.request.RequestResponce;

public interface StepService {

    /**
     * 发送请求并且保存到返回到redis
     *
     * @param request
     */
    RequestResponce request(RequestRequest request);
}
