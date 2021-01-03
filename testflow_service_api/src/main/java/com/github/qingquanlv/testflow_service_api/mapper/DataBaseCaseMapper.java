package com.github.qingquanlv.testflow_service_api.mapper;

import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.DatabaseCase;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Qingquan Lv
 * @Date 2020/12/24 23:55
 * @Version 1.0
 */
@Repository
public interface DataBaseCaseMapper {

    List<DatabaseCase> Sel(String name);

    Long Del(Long case_id);

    Long Insert(List<DatabaseCase> database_case);

    DatabaseCase Update(DatabaseCase database_case);

}
