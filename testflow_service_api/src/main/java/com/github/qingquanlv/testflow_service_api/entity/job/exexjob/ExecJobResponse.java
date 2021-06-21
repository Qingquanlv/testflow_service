package com.github.qingquanlv.testflow_service_api.entity.job.exexjob;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import lombok.Data;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/20 11:04
 */
@Data
public class ExecJobResponse {
        //status
        private Status status;

        private Long taskId;
}
