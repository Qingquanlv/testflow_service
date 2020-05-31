package com.github.qingquanlv.testflow_service.service;

import com.github.qingquanlv.testflow_service.entity.feature.FeatureRequest;
import com.github.qingquanlv.testflow_service.entity.feature.FeatureResponse;

public interface StepService {

    /**
     * 执行Feature
     *
     * @param request
     */
    FeatureResponse executeFeature(FeatureRequest request);
}
