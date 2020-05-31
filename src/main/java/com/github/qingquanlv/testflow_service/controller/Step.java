package com.github.qingquanlv.testflow_service.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.qingquanlv.testflow_service.entity.feature.FeatureRequest;
import com.github.qingquanlv.testflow_service.entity.feature.FeatureResponse;
import com.github.qingquanlv.testflow_service.service.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

}
