package com.github.qingquanlv.testflow_service_api.entity.job.setstatus;

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
public class SetStatusRequest {

        Long jobId;
        Integer status;
}
