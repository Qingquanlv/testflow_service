package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/7 22:37
 * @Version 1.0
 */
@Data
@Builder
public class FeatureCaseNextCase {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private Long sourceCaseId;

    private Long targetCaseId;

    @TableField("sourceAnchor")
    private Long sourceanchor;

    @TableField("targetAnchor")
    private Long targetanchor;

    private Timestamp datachangedLasttime;

}
