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
public class ComparevalCaseConfig {

        @TableId(value = "id", type = IdType.AUTO)
        private Long id;

        /**
         * case_name
         */
        @TableField(value = "case_id")
        private Long caseId;

        /**
         * exp_val
         */
        @TableField(value = "exp_val")
        private String expVal;

        /**
         * atl_val
         */
        @TableField(value = "atl_val")
        private String atlVal;

        private Timestamp datachangedLasttime;

}

