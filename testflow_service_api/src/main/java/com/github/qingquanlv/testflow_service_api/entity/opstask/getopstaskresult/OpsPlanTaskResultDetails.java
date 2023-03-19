package com.github.qingquanlv.testflow_service_api.entity.opstask.getopstaskresult;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
public class OpsPlanTaskResultDetails {

        private Long id;
        private Timestamp startTime;
        private Timestamp endTime;
        private String label;
        private String clazz;
        private Integer status;
        private String config;
        private String data;
}
