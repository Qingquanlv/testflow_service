package com.github.qingquanlv.testflow_service_api.controller;

import com.github.qingquanlv.testflow_service_api.entity.feature.createfeature.CreateFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature.createfeature.CreateFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature.deletefeature.DeleteFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature.execfeature.ExecFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature.execfeature.ExecFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature.queryallfeature.QueryAllFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature.queryfeature.QueryFeatureResponse;
import com.github.qingquanlv.testflow_service_api.service.impl.FeatureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/queryall")
    public QueryAllFeatureResponse getFeatureAll(){
        QueryAllFeatureResponse rsp =  featureService.getFeatureAll();
        return rsp;
    }

    @RequestMapping("/get/{id}")
    public QueryFeatureResponse getFeature(@PathVariable Long id){
        QueryFeatureResponse rsp =  featureService.getFeature(id);
        return rsp;
    }

    @PostMapping(path = "/create")
    public CreateFeatureResponse createCase(@RequestBody CreateFeatureRequest request) {
        CreateFeatureResponse rsp = featureService.createFeature(request);
        return rsp;
    }

    @RequestMapping("/exec/{id}")
    public ExecFeatureResponse execFeature(@RequestBody ExecFeatureRequest request){
        ExecFeatureResponse rsp = new ExecFeatureResponse();
        featureService.execFeature(request);
        return rsp;
    }

    @RequestMapping("/delete/{id}")
    public DeleteFeatureResponse deleteFeature(@PathVariable Long id){
        DeleteFeatureResponse rsp =  featureService.deleteFeature(id);
        return rsp;
    }
}
