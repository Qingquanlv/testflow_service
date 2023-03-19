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
public class OpsTask {
    /**
     * id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String label;

    private Long taskPlanId;

    private Long subTaskId;

    private String taskType;

    private Long x;

    private Long y;

    private Timestamp datachanged_lasttime;
}

