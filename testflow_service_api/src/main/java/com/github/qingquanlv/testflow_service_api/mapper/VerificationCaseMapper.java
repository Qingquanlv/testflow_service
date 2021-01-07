package com.github.qingquanlv.testflow_service_api.mapper;

import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Feature;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.RequestCase;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.VerificationCase;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/2 12:16
 * @Version 1.0
 */
@Repository
public interface VerificationCaseMapper {

    List<VerificationCase> Sel(String name);

    VerificationCase SelOne(String name);

    Long Del(Long case_id);

    Long Insert(List<VerificationCase> database_case);

    Feature Update(VerificationCase database_case);

}


