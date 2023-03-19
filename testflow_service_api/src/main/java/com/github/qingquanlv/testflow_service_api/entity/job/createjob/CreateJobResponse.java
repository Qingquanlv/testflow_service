package com.github.qingquanlv.testflow_service_api.entity.job.createjob;

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
public class CreateJobResponse {
        //status
        private Status status;

}
