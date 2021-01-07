package com.github.qingquanlv.testflow_service_api.mapper;

import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Feature;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.PaserCase;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/2 12:14
 * @Version 1.0
 */
@Repository
public interface PaserCaseMapper {

    List<PaserCase> Sel(String name);

    PaserCase SelOne(String name);

    Long Del(Long case_id);

    Long Insert(List<PaserCase> database_case);

    PaserCase Update(PaserCase database_case);

}

