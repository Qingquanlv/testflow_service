package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: qingquan.lv
 * @Date: 2023/2/27 下午12:11
 */
@Data
@Builder
public class ParameterTab {
        /**
         * id
         */
        @TableId(value = "id", type = IdType.AUTO)
        private Long id;

        private Long appid;

        private String parameterName;
}
