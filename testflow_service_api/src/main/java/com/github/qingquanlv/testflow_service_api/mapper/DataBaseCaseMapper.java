package com.github.qingquanlv.testflow_service_api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.DatabaseCase;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.TaskConfig;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @Author Qingquan Lv
 * @Date 2020/12/24 23:55
 * @Version 1.0
 */
@Repository
public interface DataBaseCaseMapper extends BaseMapper<DatabaseCase> {

    List<DatabaseCase> Sel(String name);

    List<DatabaseCase> SelList(Set<Long> Ids);

    DatabaseCase SelOne(Long Id);

    Long Del(Set<Long> case_id);

    Long Ins(List<DatabaseCase> database_case);

    DatabaseCase Upd(List<DatabaseCase> database_case);
}
