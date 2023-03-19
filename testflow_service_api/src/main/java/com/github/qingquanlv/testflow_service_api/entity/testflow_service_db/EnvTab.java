package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author: qingquan.lv
 * @Date: 2023/3/3 11:40
 */

@Data
@Builder
public class EnvTab {
    /**
     * case_id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private Long appId;

    private String caseType;

    private String configKey;

    private String configVal;

    private Timestamp datachangedLasttime;
}
