package com.github.qingquanlv.testflow_service_api.entity.parameter.queryallparameter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Qingquan Lv
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryParameter {

    /**
     * parameter_name
     */
    private Long id;
    private String parameter_name;
}
