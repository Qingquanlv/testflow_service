package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/2 20:07
 */
@Data
@Builder
public class Task {

        private static final long serialVersionUID = 1L;

        @TableId(value = "id",type = IdType.AUTO)
        private Long id;

        private String name;

        private Long featureId;

        private String param_name;
        /**
         * 参数index，逗号分隔，例子 1,2,3,4
         */
        private String paramIndexId;

        private Timestamp datachangedLasttime;

}

