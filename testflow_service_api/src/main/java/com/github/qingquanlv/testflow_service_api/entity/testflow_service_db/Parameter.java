package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/20 17:23
 */
@Data
public class Parameter {
        /**
         * id
         */
        @TableId(value = "id",type = IdType.AUTO)
        private Long id;

        private String parameterName;

        private Long parameterValueIndex;

        private String parameterKey;

        private String parameterValue;

        private Timestamp datachangedLasttime;
}
