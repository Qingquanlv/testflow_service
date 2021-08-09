package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: qingquan.lv
 * @Date: 2021/8/7 下午5:50
 */
@Data
@Builder
public class JobConfig {

        @TableId(value = "id",type = IdType.AUTO)
        private Integer id;

        @TableField("job_id")
        private Long jobId;

        @TableField("cron")
        private String cron;

        @TableField("class_name")
        private String className;

        @TableField("description")
        private String description;

        @TableField("status")
        private Integer status;
}
