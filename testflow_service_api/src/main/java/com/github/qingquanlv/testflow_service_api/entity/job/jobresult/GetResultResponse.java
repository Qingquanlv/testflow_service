package com.github.qingquanlv.testflow_service_api.entity.job.jobresult;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/17 20:39
 */
@Data
@Builder
public class GetResultResponse {

        /**
         * status
         */
        private Status status;

        private List<TaskResults> taskResults;


}
