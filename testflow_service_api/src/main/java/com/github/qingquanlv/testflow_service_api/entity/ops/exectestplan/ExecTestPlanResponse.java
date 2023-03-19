package com.github.qingquanlv.testflow_service_api.entity.ops.exectestplan;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecTestPlanResponse {

    private Status status;
}
