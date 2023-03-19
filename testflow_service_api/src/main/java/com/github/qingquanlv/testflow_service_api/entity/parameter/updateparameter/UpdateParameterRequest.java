package com.github.qingquanlv.testflow_service_api.entity.parameter.updateparameter;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateParameterRequest {

    /**
     * 请求唯一标识
     */
    private String requestId;
    private Long parameterId;

    /**
     * parameter_name
     */
    @NotBlank(message = "parameter name不能为null")
    private String parameterName;

    /**
     * parameters
     */
    private List<HashMap<String, String>> parameters;
}
