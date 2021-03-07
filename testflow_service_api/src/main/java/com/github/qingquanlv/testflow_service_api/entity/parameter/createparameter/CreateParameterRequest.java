package com.github.qingquanlv.testflow_service_api.entity.parameter.createparameter;

import com.github.qingquanlv.testflow_service_api.entity.parameter.Parameter;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Author Qingquan Lv
 * @Date 2021/2/3 6:53
 * @Version 1.0
 */
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

    /**
     * parameters
     */
    @Valid
    private List<Parameter> parameters;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public String getParameter_name() {
        return parameter_name;
    }

    public void setParameter_name(String parameter_name) {
        this.parameter_name = parameter_name;
    }
}
