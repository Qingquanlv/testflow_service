package com.github.qingquanlv.testflow_service_api.service;

import com.github.qingquanlv.testflow_service_api.entity.cases_v2.Config;

/**
 * @Author: qingquan.lv
 * @Date: 2023/3/6 17:35
 */
public interface IExecCase {
    String execute(String publicKey, Config config);
}
