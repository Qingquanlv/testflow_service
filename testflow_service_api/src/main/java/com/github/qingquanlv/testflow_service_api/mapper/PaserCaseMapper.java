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

    PaserCase SelOne(Long Id);

    Long Del(Long case_id);

    Long Ins(List<PaserCase> database_case);

    PaserCase Upd(PaserCase database_case);

}

