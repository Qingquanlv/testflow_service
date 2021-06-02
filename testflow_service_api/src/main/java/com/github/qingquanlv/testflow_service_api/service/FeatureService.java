package com.github.qingquanlv.testflow_service_api.service;

import com.github.qingquanlv.testflow_service_api.entity.feature.createfeature.CreateFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature.createfeature.CreateFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature.deletefeature.DeleteFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature.execasyncfeature.ExecAsyncFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature.execasyncfeature.ExecAsyncFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature.execfeature.ExecFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature.execfeature.ExecFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature.queryallfeature.QueryAllFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature.queryfeature.QueryFeatureResponse;

import java.util.concurrent.CompletableFuture;


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
     * 同步执行feature
     *
     * @param id
     * @return
     */
    ExecFeatureResponse execFeature(ExecFeatureRequest id);


    /**
     * 异步执行feature
     *
     * @param id
     * @return
     */
    CompletableFuture<ExecAsyncFeatureResponse> execFeatureAsync(ExecAsyncFeatureRequest id);

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
    QueryAllFeatureResponse getFeatureAll();

    /**
     * 删除feature
     *
     * @param id
     * @return
     */
    DeleteFeatureResponse deleteFeature(Long id);

}

