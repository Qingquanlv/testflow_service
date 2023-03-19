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
public class OpsPlanTask {
        /**
         * id
         */
        @TableId(value = "id",type = IdType.AUTO)
        private Long id;

        private Long plan_id;

        private Timestamp starttime;

        private Timestamp endtime;

        //0 失败 1 成功 2 运行中
        private Integer status;
}
