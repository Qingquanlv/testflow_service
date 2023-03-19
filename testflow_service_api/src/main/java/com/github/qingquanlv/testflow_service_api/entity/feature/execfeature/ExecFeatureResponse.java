package com.github.qingquanlv.testflow_service_api.entity.feature.execfeature;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import com.github.qingquanlv.testflow_service_api.entity.parameter.Parameter;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author Qingquan Lv
 * @Version 1.0
 */
@Data
@Builder
public class ExecFeatureResponse {
    /**
     * status
     */
    private Status status;
    /**
     * parameters
     */
    private List<Parameter> parameters;
    /**
     * info
     */
    private String info;
    /**
     * assertion
     */
    private String assertion;

}
