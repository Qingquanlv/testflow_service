package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author: qingquan.lv
 * @Date: 2023/3/6 16:14
 */
@Data
@Builder
public class CaseNextCaseTab {
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