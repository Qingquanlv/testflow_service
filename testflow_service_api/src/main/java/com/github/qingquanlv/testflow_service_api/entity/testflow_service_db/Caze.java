package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/21 11:16
 */
@Data
@Builder
public class Caze {
        private static final long serialVersionUID = 1L;

        /**
         * case_id
         */
        @TableId(value = "id",type = IdType.AUTO)
        private Long id;

        private String label;

        private Long featureId;

        private String caseType;

        private Long x;

        private Long y;

        private Timestamp datachangedLasttime;
}
