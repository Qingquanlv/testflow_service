package com.github.qingquanlv.testflow_service_api.entity.job.taskresult;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: qingquan.lv
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTaskResultResponse {

        /**
         * status
         */
        private Status status;

        private List<IndexResult> indexResults;

        /**
         * pass
         */
        private Integer pass;

        /**
         * pass
         */
        private Integer fail;

        /**
         * total
         */
        private Integer total;

}
