package com.github.qingquanlv.testflow_service_api.entity.job.queryjob;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: qingquan.lv
 * @Date: 2021/8/14 下午3:11
 */
@Data
@Builder
public class QueryJobRequest {

        //status
        private Integer jobId;
}
