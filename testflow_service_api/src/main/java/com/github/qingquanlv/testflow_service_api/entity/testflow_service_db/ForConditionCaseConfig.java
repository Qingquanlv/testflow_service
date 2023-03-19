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
public class ForConditionCaseConfig {

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

        @TableField(value = "max_loop")
        private Long maxLoop;

        @TableField(value = "wait_time")
        private Long waitTime;

        @TableField(value = "loop_node_name")
        private String loopNodeName;

        @TableField(value = "next_node_name")
        private String nextNodeName;

}
