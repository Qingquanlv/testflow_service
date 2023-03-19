package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author Qingquan Lv
 * @Version 1.0
 */
@Data
@Builder
public class FeatureCase {
    /**
     * id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * feature_id
     */
    @TableField("feature_id")
    private Long feature_id;
    /**
     * case_id
     */
    @TableField("case_id")
    private Long case_id;
    /**
     * case_type
     */
    @TableField("case_type")
    private String case_type;
    /**
     * label
     */
    @TableField("label")
    private String label;
    /**
     * x
     */
    @TableField("x")
    private Long x;
    /**
     * y
     */
    @TableField("y")
    private Long y;
    /**
     * 时间戳
     */
    @TableField("datachanged_lasttime")
    private Timestamp datachanged_lasttime;
}

