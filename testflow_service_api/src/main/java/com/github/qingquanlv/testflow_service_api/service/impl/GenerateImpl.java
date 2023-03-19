package com.github.qingquanlv.testflow_service_api.service.impl;

import com.github.qingquanlv.testflow_service_api.dao.EnvTabMapper;
import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.generate.generatecasebyyapi.GenerateCaseByYapiRequest;
import com.github.qingquanlv.testflow_service_api.entity.generate.generatecasebyyapi.GenerateCaseByYapiResponse;
import com.github.qingquanlv.testflow_service_api.mapper.AppMapper;
import com.github.qingquanlv.testflow_service_api.mapper.UserMapper;
import com.github.qingquanlv.testflow_service_api.service.GenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: qingquan.lv
 */
@Service
public class GenerateImpl implements GenerateService {

        @Autowired
        AppMapper appMapper;

        @Autowired
        EnvTabMapper envConfigMapper;

        @Autowired
        UserMapper userMapper;

        @Autowired
        private FeatureServiceImpl featureService;

        @Override
        public GenerateCaseByYapiResponse generateCaseByYapi(GenerateCaseByYapiRequest req) {
                GenerateCaseByYapiResponse rsp = new GenerateCaseByYapiResponse();
                Status status = new Status();
                status.setSuccess(true);
                rsp.setStatus(status);
                return rsp;

        }
}
