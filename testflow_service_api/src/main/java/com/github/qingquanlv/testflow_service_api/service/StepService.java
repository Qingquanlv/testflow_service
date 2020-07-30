package com.github.qingquanlv.testflow_service_api.service;

import com.github.qingquanlv.testflow_service_api.entity.feature.FeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature.FeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.savefeature.SaveFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.savefeature.SaveFeatureResponse;

public interface StepService {

    /**
     * 执行Feature
     *
     * @param request
     */
    FeatureResponse executeFeature(FeatureRequest request);


    /**
     * 保存feature
     *
     * @param request
     * @return
     */
    SaveFeatureResponse saveFeature(SaveFeatureRequest request);
}
