package com.github.qingquanlv.testflow_service_api.entity.feature_v2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeatureTaskResult {

        private Long taskId;

        private String parameter_name;

        private Long parameter_value_index;

        private String parameter_value;

        private Timestamp starttime;

        private Timestamp endtime;

        private String logs;

        /**
         * Feature执行状态 0 失败 1 成功 2 运行中 3 异常
         */
        private Integer status;
}
