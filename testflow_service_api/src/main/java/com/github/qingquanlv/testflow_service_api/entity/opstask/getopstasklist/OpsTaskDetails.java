package com.github.qingquanlv.testflow_service_api.entity.opstask.getopstasklist;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
public class OpsTaskDetails {

        private Long id;
        private Long planId;
        private String planName;
        private Timestamp startTime;
        private Timestamp endTime;
        //0 失败 1 成功 2 运行中
        private Integer status;
}
