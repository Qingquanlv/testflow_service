package com.github.qingquanlv.testflow_service_api.entity.job.jobresult;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetResultResponse {

        /**
         * status
         */
        private Status status;

        private List<TaskResults> taskResults;


}
