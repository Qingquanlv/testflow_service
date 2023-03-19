package com.github.qingquanlv.testflow_service_api.entity.cases_v2.execcase;

import com.github.qingquanlv.testflow_service_api.entity.cases_v2.Config;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * @Author: qingquan.lv
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecCaseRequest {

        private String requestId;
        private HashMap<String, String> params;
        private Config config;
}
