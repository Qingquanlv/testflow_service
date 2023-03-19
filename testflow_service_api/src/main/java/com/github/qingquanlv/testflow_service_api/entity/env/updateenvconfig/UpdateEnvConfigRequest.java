package com.github.qingquanlv.testflow_service_api.entity.env.updateenvconfig;

import com.github.qingquanlv.testflow_service_api.entity.env.EnvConfig;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
public class UpdateEnvConfigRequest {

        /**
         * 请求唯一标识
         */
        private String requestId;

        /**
         * parameter_name
         */
        private Long appId;

        private List<EnvConfig> envConfigs;

}
