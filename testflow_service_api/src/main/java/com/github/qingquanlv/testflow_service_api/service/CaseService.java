package com.github.qingquanlv.testflow_service_api.service;

import com.github.qingquanlv.testflow_service_api.entity.cases_v2.execcase.ExecCaseResponse;
import com.github.qingquanlv.testflow_service_api.entity.cases_v2.execcase.ExecCaseRequest;

/**
 * @Author Qingquan Lv
 * @Date 2020/12/24 22:37
 * @Version 1.0
 */
public interface CaseService {

    /**
     * 执行Case
     *
     * @param id
     * @return
     */
    ExecCaseResponse execCase(ExecCaseRequest id);
}
