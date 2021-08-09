package com.github.qingquanlv.testflow_service_api.entity.feature_v2;

import lombok.Data;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/22 20:10
 */
@Data
public class CaseInfo {

        private String label;
        private String clazz;
        private String config;
        private String data;
        private Integer status;
}
