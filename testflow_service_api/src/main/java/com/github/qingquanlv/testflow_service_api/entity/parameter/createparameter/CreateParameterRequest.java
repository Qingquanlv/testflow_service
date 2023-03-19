package com.github.qingquanlv.testflow_service_api.entity.parameter.createparameter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;

/**
 * @Author Qingquan Lv
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateParameterRequest {

    /**
     * 请求唯一标识
     */
    @NotBlank(message = "requestId不能为null")
    private String requestId;

    /**
     * parameter_name
     */
    @NotBlank(message = "parameter name不能为null")
    private String parameter_name;

    private List<HashMap<String, String>> parameters;
}
