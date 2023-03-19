package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
public class OpsPlanTaskResult {

        /**
         * id
         */
        @TableId(value = "id",type = IdType.AUTO)
        private Long id;

        private Long planTaskId;

        private Long taskId;

        private Timestamp starttime;

        private Timestamp endtime;

        private String label;

        private String clazz;

        private String config;

        private String data;

        private Integer status;
}
