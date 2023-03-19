package com.github.qingquanlv.testflow_service_api.entity.job.exexjob;

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
public class ExecJobResponse {
        //status
        private Status status;

        private Long taskId;
}
