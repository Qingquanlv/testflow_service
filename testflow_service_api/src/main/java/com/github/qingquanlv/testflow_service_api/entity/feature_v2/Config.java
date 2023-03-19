package com.github.qingquanlv.testflow_service_api.entity.feature_v2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Config {

        private Long id;
        private String label;
        private Map<String,String> exec_params;
}
