package com.github.qingquanlv.testflow_service_api.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.qingquanlv.testflow_service_api.entity.feature.createfeature.CreateFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature.createfeature.CreateFeatureResponse;
import com.github.qingquanlv.testflow_service_api.service.impl.CaseServiceImpl;
import com.github.qingquanlv.testflow_service_api.service.impl.FeatureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/2 9:37
 * @Version 1.0
 */
@RestController
@RequestMapping("feature")
public class Feature {

    @Autowired
    private FeatureServiceImpl featureService;

    @PostMapping(path = "/create")
    public CreateFeatureResponse createCase(@RequestBody CreateFeatureRequest request) {
        CreateFeatureResponse rsp =  featureService.createFeature(request);
        return rsp;
    }
}
