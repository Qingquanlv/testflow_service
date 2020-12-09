package com.github.qingquanlv.testflow_service_api.DAO;

import com.github.qingquanlv.testflow_service_api.entity.cases.request.RequestCasesRequest;

/**
 * @Author Qingquan Lv
 * @Date 2020/11/29 11:55
 * @Version 1.0
 */
public interface RequestCases {
    int inserRequestCases(RequestCasesRequest user);
}
