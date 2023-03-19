package com.github.qingquanlv.testflow_service_api.entity.parameter.queryallparameter;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author Qingquan Lv
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryAllParameterResponse {

    /**
     * status
     */
    private Status status;
    /**
     * parameters
     */
    private List<QueryParameter> parameters;
    /**
     * total
     */
    private long total;
}
