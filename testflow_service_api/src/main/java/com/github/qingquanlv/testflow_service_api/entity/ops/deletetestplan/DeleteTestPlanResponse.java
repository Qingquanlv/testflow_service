package com.github.qingquanlv.testflow_service_api.entity.ops.deletetestplan;

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
public class DeleteTestPlanResponse {

        //status
        private Status status;
}
