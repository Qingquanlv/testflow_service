package com.github.qingquanlv.testflow_service_api.mapper;

import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Feature;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.VerificationCase;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/2 12:16
 * @Version 1.0
 */
@Repository
public interface VerificationCaseMapper {

    List<VerificationCase> Sel(String name);

    List<VerificationCase> SelList(Set<Long> Ids);

    VerificationCase SelOne(Long Id);

    Long Del(Set<Long> case_id);

    Long Ins(List<VerificationCase> verification_case);

    void Upd(List<VerificationCase> verification_cases);

}


