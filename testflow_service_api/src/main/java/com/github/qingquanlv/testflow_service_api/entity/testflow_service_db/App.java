package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author: qingquan.lv
 */
@Data
public class App {

        /**
         * case_id
         */
        @TableId(value = "id",type = IdType.AUTO)
        private Long id;

        private Long projectId;

        private String name;

        private String yapiToken;

        private Timestamp datachangedLasttime;
}
