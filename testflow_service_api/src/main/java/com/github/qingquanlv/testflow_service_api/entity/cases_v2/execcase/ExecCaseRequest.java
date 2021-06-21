package com.github.qingquanlv.testflow_service_api.entity.cases_v2.execcase;

import com.github.qingquanlv.testflow_service_api.entity.cases_v2.Config;
import lombok.Data;

import java.util.HashMap;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/14 17:41
 */
@Data
public class ExecCaseRequest {

        private String requestId;
        private HashMap<String, String> params;
        private Config config;
}
