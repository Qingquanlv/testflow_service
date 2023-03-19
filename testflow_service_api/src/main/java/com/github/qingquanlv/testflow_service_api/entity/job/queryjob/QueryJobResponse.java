package com.github.qingquanlv.testflow_service_api.entity.job.queryjob;

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
public class QueryJobResponse {

        //status
        private Status status;

        private List<TaskDetails> jobDetails;

        /**
         * total
         */
        private long total;
}
