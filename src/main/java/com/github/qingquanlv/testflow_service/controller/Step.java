package com.github.qingquanlv.testflow_service.controller;

import com.github.qingquanlv.testflow_service.service.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Step {

    @Qualifier("StepServiceImpl")
    private StepService serviceImpl;

}
