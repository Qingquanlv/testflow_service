package com.github.qingquanlv.testflow_service_api.controller;

import com.github.qingquanlv.testflow_service_api.entity.generate.generatecasebyyapi.GenerateCaseByYapiRequest;
import com.github.qingquanlv.testflow_service_api.entity.generate.generatecasebyyapi.GenerateCaseByYapiResponse;
import com.github.qingquanlv.testflow_service_api.service.impl.GenerateImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: qingquan.lv
 */
@RestController
@RequestMapping("generate")
public class Generate {

        @Autowired
        private GenerateImpl generateService;

        @RequestMapping("/caseByYapi")
        public GenerateCaseByYapiResponse generateCaseByYapi(@RequestBody GenerateCaseByYapiRequest request){
                return generateService.generateCaseByYapi(request);
        }
}
