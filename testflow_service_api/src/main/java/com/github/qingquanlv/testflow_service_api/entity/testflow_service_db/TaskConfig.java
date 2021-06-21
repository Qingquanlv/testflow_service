package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Qingquan Lv
 * @Date 2021/6/6 20:59
 * @Version 1.0
 */
@Data
@Builder
public class TaskConfig implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField("task_id")
    private String taskId;

    @TableField("cron")
    private String cron;

    @TableField("class_name")
    private String className;

    @TableField("description")
    private String description;

    @TableField("status")
    private Integer status;
}