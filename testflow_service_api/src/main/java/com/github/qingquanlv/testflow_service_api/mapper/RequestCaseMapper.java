package com.github.qingquanlv.testflow_service_api.mapper;

import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Feature;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.PaserCase;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.RequestCase;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/2 12:15
 * @Version 1.0
 */
@Repository
public interface RequestCaseMapper {

    List<RequestCase> Sel(String name);

    RequestCase SelOne(String name);

    Long Del(Long case_id);

    Long Insert(List<RequestCase> database_case);

    Feature Update(RequestCase database_case);

}


