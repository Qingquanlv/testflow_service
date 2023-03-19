package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: qingquan.lv
 */
@Data
@Builder
public class IfConditionCaseConfig {

        @TableId(value = "id", type = IdType.AUTO)
        private Long id;

        /**
         * case_name
         */
        @TableField(value = "case_id")
        private Long caseId;

        private String path;

        private String operator;

        private String value;

        @TableField(value = "true_node_name")
        private String trueNodeName;

        @TableField(value = "false_node_name")
        private String falseNodeName;
}
