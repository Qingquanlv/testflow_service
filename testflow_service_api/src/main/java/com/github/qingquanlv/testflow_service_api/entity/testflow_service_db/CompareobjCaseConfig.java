package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author: qingquan.lv
 * @Date: 2021/8/11 下午8:26
 */
@Data
@Builder
public class CompareobjCaseConfig {

        @TableId(value = "id", type = IdType.AUTO)
        private Long id;

        /**
         * case_name
         */
        @TableField(value = "case_id")
        private Long caseId;

        private String expObj;

        private String atlObj;

        private String pkMap;

        private String noCompareItemMap;

        private Timestamp datachangedLasttime;

}
