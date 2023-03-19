package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author: qingquan.lv
 * @Date: 2023/2/27 下午3:30
 */
@Data
@Builder
public class FeatureTab {
        /**
         * case_id
         */
        @TableId(value = "feature_id",type = IdType.AUTO)
        private Long feature_id;
        /**
         * feature_name
         */
        @TableField("feature_name")
        private String feature_name;
        /**
         * desctription
         */
        @TableField("description")
        private String description;
        /**
         * 时间戳
         */
        @TableField("datachanged_lasttime")
        private Timestamp datachanged_lasttime;
}