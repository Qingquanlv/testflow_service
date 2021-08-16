package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2021-06-17
 */
@Data
@Builder
public class TaskResult  {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField(value = "task_id")
    private Long taskId;

    @TableField(value = "parameter_name")
    private String parameter_name;

    @TableField(value = "parameter_value_index")
    private Long parameter_value_index;

    @TableField(value = "parameter_value")
    private String parameter_value;

    private Timestamp starttime;

    private Timestamp endtime;

    private String logs;

    private Integer status;

    private Timestamp datachangedLasttime;

}
