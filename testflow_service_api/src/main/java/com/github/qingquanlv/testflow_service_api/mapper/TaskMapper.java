package com.github.qingquanlv.testflow_service_api.mapper;

import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Task;
import org.springframework.stereotype.Repository;

/**
 * @Author: qingquan.lv
 * @Date: 2021/6/2 20:08
 */
@Repository
public interface TaskMapper {

        Long Ins(Task task);

        Task Sel(Long taskId);
}
