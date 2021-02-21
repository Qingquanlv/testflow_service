package com.github.qingquanlv.testflow_service_api.service;

import com.github.qingquanlv.testflow_service_api.entity.cases.database.deletedatabase.DeleteDataBaseCaseRequest;
import com.github.qingquanlv.testflow_service_api.entity.cases.database.deletedatabase.DeleteDataBaseCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature.createfeature.CreateFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature.createfeature.CreateFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature.deletefeature.DeleteFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature.execfeature.ExecFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature.queryfeature.QueryFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature.resultfeature.ResultFeatureResponse;
import org.springframework.stereotype.Service;


/**
 * @Author Qingquan Lv
 * @Date 2021/1/2 12:20
 * @Version 1.0
 */
public interface FeatureService {
    /**
     * 创建feature
     *
     * @param request
     * @return
     */
    CreateFeatureResponse createFeature(CreateFeatureRequest request);

    /**
     * 执行feature
     *
     * @param id
     * @return
     */
    ExecFeatureResponse execFeature(Long id);

    /**
     * 获取feature
     *
     * @param id
     * @return
     */
    QueryFeatureResponse getFeature(Long id);

    /**
     * 删除feature
     *
     * @param id
     * @return
     */
    DeleteFeatureResponse deleteFeature(Long id);

    /**
     * 获取结果feature
     *
     * @param id
     * @return
     */
    ResultFeatureResponse resultFeature(Long id);


}

