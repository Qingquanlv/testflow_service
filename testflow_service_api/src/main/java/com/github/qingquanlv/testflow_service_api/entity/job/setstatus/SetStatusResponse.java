package com.github.qingquanlv.testflow_service_api.entity.job.setstatus;

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
public class SetStatusResponse {

        /**
         * status
         */
        private Status status;
}
