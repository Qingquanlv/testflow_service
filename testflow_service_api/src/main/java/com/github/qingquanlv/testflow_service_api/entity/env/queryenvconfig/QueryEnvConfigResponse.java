package com.github.qingquanlv.testflow_service_api.entity.env.queryenvconfig;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.env.EnvConfig;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
public class QueryEnvConfigResponse {
        private Status status;
        private List<EnvConfig> envConfigs;
}
