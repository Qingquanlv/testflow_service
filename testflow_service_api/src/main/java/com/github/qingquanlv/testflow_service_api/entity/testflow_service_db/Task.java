package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author: qingquan.lv
 * @Date: 2021/8/14 下午2:15
 */
@Data
@Builder
public class Task {

        @TableId(value = "id",type = IdType.AUTO)
        private Long id;

        @TableField(value = "job_id")
        private Long jobId;

        @TableField(value = "parameter_name")
        private String parameter_name;

        @TableField(value = "parameter_value_index")
        private String parameter_value_index;

        private Timestamp starttime;

        private Timestamp endtime;

        private Integer status;

        private Timestamp datachanged_lasttime;

}
