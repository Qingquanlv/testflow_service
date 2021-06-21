package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/2 11:13
 * @Version 1.0
 */
@Data
@Builder
public class Feature {

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
