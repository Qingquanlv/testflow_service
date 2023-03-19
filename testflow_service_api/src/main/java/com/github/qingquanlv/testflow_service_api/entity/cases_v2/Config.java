package com.github.qingquanlv.testflow_service_api.entity.cases_v2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Config {

        private String label;
        private String clazz;
        private HashMap<String, String> exec_params;
}
