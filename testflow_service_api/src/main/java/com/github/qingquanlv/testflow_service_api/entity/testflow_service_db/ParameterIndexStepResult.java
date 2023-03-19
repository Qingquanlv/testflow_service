package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
public class ParameterIndexStepResult {

        @TableId(value = "id",type = IdType.AUTO)
        private Integer id;

        @TableField(value = "task_result_id")
        private Integer taskResultId;

        @TableField(value = "result_index_id")
        private Integer resultIndexId;

        private String clazz;

        private String config;

        private String data;

        private String label;

        private Integer status;

        private Timestamp datachangedLasttime;

}
