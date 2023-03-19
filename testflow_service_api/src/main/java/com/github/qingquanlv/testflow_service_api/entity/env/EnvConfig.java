package com.github.qingquanlv.testflow_service_api.entity.env;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: qingquan.lv
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnvConfig {

        private String caseType;
        private String configKey;
        private String configVal;

}
