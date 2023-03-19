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
public class OpsTaskNextTask {
    /**
     * id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private Long sourceTaskId;

    private Long targetTaskId;

    private Long sourceAnchor;

    private Long targetAnchor;

    private Timestamp datachanged_lasttime;
}
