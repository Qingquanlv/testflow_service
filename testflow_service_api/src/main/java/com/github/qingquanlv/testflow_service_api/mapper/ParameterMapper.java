package com.github.qingquanlv.testflow_service_api.mapper;

import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.ParameterCase;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @Author Qingquan Lv
 * @Date 2021/2/3 7:17
 * @Version 1.0
 */
@Repository
public interface ParameterMapper {

    List<ParameterCase> Sel(String name);

    Long Del(String id);

    Long Ins(List<ParameterCase> parameter_cases);

}

