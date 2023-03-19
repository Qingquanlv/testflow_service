package com.github.qingquanlv.testflow_service_api.entity.job.taskresult;

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
public class StepResult {

        private Integer id;

        private String label;

        private String clazz;

        private Integer status;

        private String config;

        private String data;
}
