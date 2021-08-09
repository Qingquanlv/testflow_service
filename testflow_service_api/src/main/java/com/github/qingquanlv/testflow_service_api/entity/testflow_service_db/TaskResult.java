package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;


import com.baomidou.mybatisplus.annotation.IdType;
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

    private Integer jobId;

    private Long taskId;

    private String parameter_name;

    private Long parameter_value_index;

    private Timestamp starttime;

    private Timestamp endtime;

    private String logs;

    private Integer status;

    private Timestamp datachangedLasttime;

}
