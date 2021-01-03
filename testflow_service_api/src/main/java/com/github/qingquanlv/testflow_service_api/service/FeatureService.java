package com.github.qingquanlv.testflow_service_api.service;

import com.github.qingquanlv.testflow_service_api.entity.cases.database.deletedatabase.DeleteDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.deletedatabase.DeleteDataBaseCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature.createfeature.CreateFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature.createfeature.CreateFeatureResponse;
import org.springframework.stereotype.Service;


/**
 * @Author Qingquan Lv
 * @Date 2021/1/2 12:20
 * @Version 1.0
 */
public interface FeatureService {
    /**
     * 删除dataBaseCase
     *
     * @param request
     * @return
     */
    CreateFeatureResponse createFeature(CreateFeatureRequest request);




}

