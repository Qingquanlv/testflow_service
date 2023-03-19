package com.github.qingquanlv.testflow_service_api.entity.job.queryalljob;

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
public class QueryAllJobResponse {
        /**
         * status
         */
        private Status status;

        private List<JobDetails> jobDetails;
        /**
         * total
         */
        private long total;
}
