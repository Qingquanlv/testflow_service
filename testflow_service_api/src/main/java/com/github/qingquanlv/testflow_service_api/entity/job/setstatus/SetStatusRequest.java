package com.github.qingquanlv.testflow_service_api.entity.job.setstatus;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: qingquan.lv
 * @Date: 2021/8/1 下午4:29
 */
@Data
@Builder
public class SetStatusRequest {

        Long jobId;
        Integer status;
}
