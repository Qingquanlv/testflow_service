package com.github.qingquanlv.testflow_service_api.entity.parameter.updateparameter;

import com.github.qingquanlv.testflow_service_api.entity.Status;
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
public class UpdateParameterResponse {

    private Long parameterId;

    //status
    private Status status;
}


