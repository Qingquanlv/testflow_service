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
public class TidbCaseConfig {

        @TableId(value = "id", type = IdType.AUTO)
        private Long id;

        /**
         * case_name
         */
        @TableField(value = "case_id")
        private Long caseId;

        private String env;

        private String sqlStr;

        private Timestamp datachangedLasttime;

}
