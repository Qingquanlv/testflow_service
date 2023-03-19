package com.github.qingquanlv.testflow_service_api.service;


import com.github.qingquanlv.testflow_service_api.entity.feature_v2.Config;

/**
 * @Author: qingquan.lv
 * @Date: 2023/3/6 18:38
 */
public interface IInsertCase {
    void insert(Long caseId, Config p);
}
