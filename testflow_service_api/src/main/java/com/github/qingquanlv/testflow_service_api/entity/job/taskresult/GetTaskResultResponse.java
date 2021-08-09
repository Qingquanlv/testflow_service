package com.github.qingquanlv.testflow_service_api.entity.job.taskresult;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import lombok.Data;

import java.util.List;

/**
 * @Author: qingquan.lv
 * @Date: 2021/8/7 下午8:46
 */
@Data
public class GetTaskResultResponse {

        /**
         * status
         */
        private Status status;

        private List<IndexResult> indexResults;


}
