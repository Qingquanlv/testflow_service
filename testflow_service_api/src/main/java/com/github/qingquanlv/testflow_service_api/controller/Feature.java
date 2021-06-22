package com.github.qingquanlv.testflow_service_api.controller;

import com.github.qingquanlv.testflow_service_api.entity.feature_v2.execfeature.ExecFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.execfeature.ExecFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.createfeature.CreateFeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.createfeature.CreateFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.deletefeature.DeleteFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.queryallfeaure.QueryAllFeatureResponse;
import com.github.qingquanlv.testflow_service_api.entity.feature_v2.queryfeature.QueryFeatureResponse;
import com.github.qingquanlv.testflow_service_api.service.impl.FeatureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping("/queryAll")
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
    public CreateFeatureResponse createFeature(@RequestBody CreateFeatureRequest request) {
        CreateFeatureResponse rsp = featureService.createFeature(request);
        return rsp;
    }

    @RequestMapping("/exec")
    public ExecFeatureResponse execFeature(@RequestBody ExecFeatureRequest request){
        ExecFeatureResponse rsp = featureService.execFeature(request);
        return rsp;
    }

    @RequestMapping("/delete/{id}")
    public DeleteFeatureResponse deleteFeature(@PathVariable Long id){
        DeleteFeatureResponse rsp =  featureService.deleteFeature(id);
        return rsp;
    }

}
