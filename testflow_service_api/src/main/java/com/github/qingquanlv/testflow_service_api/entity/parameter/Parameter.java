package com.github.qingquanlv.testflow_service_api.entity.parameter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Qingquan Lv
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parameter {

    /**
     * parameter_key
     */
    private String parameter_key;

    /**
     * parameter_value
     */
    private String parameter_value;

}
