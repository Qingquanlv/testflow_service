package com.github.qingquanlv.testflow_service_api.entity.env.updateenvconfig;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
public class UpdateEnvConfigResponse {
        private Status status;
}
