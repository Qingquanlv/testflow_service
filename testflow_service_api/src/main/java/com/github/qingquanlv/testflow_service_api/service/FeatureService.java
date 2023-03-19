package com.github.qingquanlv.testflow_service_api.service;

import com.github.qingquanlv.testflow_service_api.entity.feature_v2.execfeature.ExecFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.execfeature.ExecFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.createfeature.CreateFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.createfeature.CreateFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.deletefeature.DeleteFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.queryallfeaure.QueryAllFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.queryallfeaure.QueryAllFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.queryfeature.QueryFeatureResponse;


/**
 * @Author Qingquan Lv
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
     * 同步执行feature
     *
     * @param id
     * @return
     */
    ExecFeatureResponse execFeature(ExecFeatureRequest id);

    /**
     * 获取feature
     *
     * @param id
     * @return
     */
    QueryFeatureResponse getFeature(Long id);

    /**
     * 获取所有feature
     *
     * @return
     */
    QueryAllFeatureResponse queryFeatureAll(QueryAllFeatureRequest request);

    /**
     * 删除feature
     *
     * @param id
     * @return
     */
    DeleteFeatureResponse deleteFeature(Long id);

}

