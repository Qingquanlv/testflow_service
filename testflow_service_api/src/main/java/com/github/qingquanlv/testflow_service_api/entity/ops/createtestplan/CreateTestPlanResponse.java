package com.github.qingquanlv.testflow_service_api.entity.ops.createtestplan;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: qingquan.lv
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTestPlanResponse {
    //status
    private Status status;

    private Long testPlanId;
}

