package com.github.qingquanlv.testflow_service_api.entity.job.taskresult;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: qingquan.lv
 * @Date: 2021/8/7 下午10:07
 */
@Data
@Builder
public class StepResult {

        private Long id;

        private String label;

        private String clazz;

        private Integer status;

        private String config;

        private String data;
}
