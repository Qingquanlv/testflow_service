package com.github.qingquanlv.testflow_service_api.service;

import java.util.List;
import java.util.Map;

/**
 * @Author: qingquan.lv
 * @Date: 2023/3/6 17:34
 */
public interface IQueryCase {
    List<Map<String, String>> select(List<Long> caseIds);
}
