package com.github.qingquanlv.testflow_service_api.entity.cases_v2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: qingquan.lv
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseInfo {

        private String label;
        private String clazz;
        private String config;
        private String data;
        private Integer status;
}
