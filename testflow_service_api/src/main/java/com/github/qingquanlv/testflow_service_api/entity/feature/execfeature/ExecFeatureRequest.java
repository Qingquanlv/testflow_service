package com.github.qingquanlv.testflow_service_api.entity.feature.execfeature;

import lombok.Data;

import java.util.HashMap;

/**
 * @Author Qingquan Lv
 * @Date 2021/3/9 23:06
 * @Version 1.0
 */
@Data
public class ExecFeatureRequest {

    /**
     * 请求唯一标识
     */
    private String requestId;
    /**
     * featureId
     */
    private Long featureId;
    /**
     * parameters
     */
    private HashMap<String, String> parameters;

}
