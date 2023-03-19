package com.github.qingquanlv.testflow_service_api.service;

import com.github.qingquanlv.testflow_service_api.entity.env.deleteenvconfig.DeleteEnvConfigResponse;
import com.github.qingquanlv.testflow_service_api.entity.env.queryenvconfig.QueryEnvConfigResponse;
import com.github.qingquanlv.testflow_service_api.entity.env.updateenvconfig.UpdateEnvConfigRequest;
import com.github.qingquanlv.testflow_service_api.entity.env.updateenvconfig.UpdateEnvConfigResponse;

/**
 * @Author: qingquan.lv
 */
public interface EnvConfigService {

        UpdateEnvConfigResponse updateEnvConfig(UpdateEnvConfigRequest request);
        QueryEnvConfigResponse queryEnvConfig(Long appId);
        DeleteEnvConfigResponse deleteEnvConfig(Long appId);
}
