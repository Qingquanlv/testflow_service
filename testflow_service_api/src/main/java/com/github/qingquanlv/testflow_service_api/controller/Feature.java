package com.github.qingquanlv.testflow_service_api.controller;

import com.github.qingquanlv.testflow_service_api.entity.feature_v2.createfeature.CreateFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.createfeature.CreateFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.deletefeature.DeleteFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.execfeature.ExecFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.execfeature.ExecFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.queryallfeaure.QueryAllFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.queryallfeaure.QueryAllFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.queryfeature.QueryFeatureResponse;
import com.github.qingquanlv.testflow_service_api.service.impl.FeatureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Qingquan Lv
 * @Version 1.0
 */
@RestController
@RequestMapping("feature")
public class Feature {

    @Autowired
    private FeatureServiceImpl featureService;

    @PostMapping("/queryAll")
    public QueryAllFeatureResponse getFeatureAll(
            @RequestBody QueryAllFeatureRequest request) {
        return featureService.queryFeatureAll(request);
    }

    @RequestMapping("/get/{id}")
    public QueryFeatureResponse getFeature(
            @PathVariable Long id){
        return featureService.getFeature(id);
    }

    @PostMapping(path = "/create")
    public CreateFeatureResponse createFeature(
            @RequestBody CreateFeatureRequest request) {
        return featureService.createFeature(request);
    }

    @RequestMapping("/delete/{id}")
    public DeleteFeatureResponse deleteFeature(
            @PathVariable Long id){
        return featureService.deleteFeature(id);
    }

    @RequestMapping("/exec")
    public ExecFeatureResponse execFeature(
            @RequestBody ExecFeatureRequest request){
        return featureService.execFeature(request);
    }
}
