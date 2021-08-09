package com.github.qingquanlv.testflow_service_api.entity.job.setstatus;

import com.github.qingquanlv.testflow_service_api.entity.Status;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: qingquan.lv
 * @Date: 2021/8/1 下午4:29
 */
@Data
@Builder
public class SetStatusResponse {

        /**
         * status
         */
        private Status status;
}
