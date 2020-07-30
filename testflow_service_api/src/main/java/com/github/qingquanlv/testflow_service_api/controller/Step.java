package com.github.qingquanlv.testflow_service_api.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.qingquanlv.testflow_service_api.entity.feature.FeatureRequest;
import com.github.qingquanlv.testflow_service_api.entity.feature.FeatureResponse;
import com.github.qingquanlv.testflow_service_api.service.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class Step {

    @Autowired
    private StepService serviceImpl;

    @PostMapping(path = "/execute")
    public FeatureResponse executeFeature(@RequestBody FeatureRequest request) {
        FeatureResponse rsp = new FeatureResponse();
        rsp = serviceImpl.executeFeature(request);
        String rspStr = JSONObject.toJSONString(rsp);
        return rsp;
    }

    @PostMapping(path = "/save")
    public FeatureResponse saveFeature(@RequestBody FeatureRequest request) {
        FeatureResponse rsp = new FeatureResponse();
        rsp = serviceImpl.executeFeature(request);
        String rspStr = JSONObject.toJSONString(rsp);
        return rsp;
    }

}
