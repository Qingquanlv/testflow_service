package com.github.qingquanlv.testflow_service_api.service;

import com.github.qingquanlv.testflow_service_api.entity.generate.generatecasebyyapi.GenerateCaseByYapiRequest;
import com.github.qingquanlv.testflow_service_api.entity.generate.generatecasebyyapi.GenerateCaseByYapiResponse;

/**
 * @Author: qingquan.lv
 */
public interface GenerateService {

        GenerateCaseByYapiResponse generateCaseByYapi(GenerateCaseByYapiRequest req);
}
