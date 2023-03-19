package com.github.qingquanlv.testflow_service_api.entity.feature_v2.createfeature;

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
public class CreateFeatureResponse {
        //status
        private Status status;

        private Long featureId;
}
