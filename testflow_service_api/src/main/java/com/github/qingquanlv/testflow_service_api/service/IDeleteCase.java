package com.github.qingquanlv.testflow_service_api.service;

import java.util.List;

/**
 * @Author: qingquan.lv
 * @Date: 2023/3/7 10:04
 */
public interface IDeleteCase {

    void delete(List<Long> caseIds);
}
