package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.sql.Timestamp;

/**
 * @Author: qingquan.lv
 */
public class User {

        /**
         * case_id
         */
        @TableId(value = "id",type = IdType.AUTO)
        private Long id;

        private String name;

        private String permission;

        private Timestamp datachangedLasttime;
}
