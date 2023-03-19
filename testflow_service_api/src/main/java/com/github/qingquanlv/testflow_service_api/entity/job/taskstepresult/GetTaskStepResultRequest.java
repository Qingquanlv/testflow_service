package com.github.qingquanlv.testflow_service_api.entity.job.taskstepresult;

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
public class GetTaskStepResultRequest {

    private Integer taskResultId;
    private Integer resultIndexId;
    private String requestId;
}
