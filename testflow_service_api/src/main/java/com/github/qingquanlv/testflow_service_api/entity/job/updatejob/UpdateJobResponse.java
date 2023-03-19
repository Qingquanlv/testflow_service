package com.github.qingquanlv.testflow_service_api.entity.job.updatejob;

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
public class UpdateJobResponse {
        //status
        private Status status;
}
