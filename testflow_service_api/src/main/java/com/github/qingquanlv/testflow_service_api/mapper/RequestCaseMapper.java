package com.github.qingquanlv.testflow_service_api.mapper;

import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Feature;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.PaserCase;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.RequestCase;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/2 12:15
 * @Version 1.0
 */
@Repository
public interface RequestCaseMapper {

    List<RequestCase> Sel(String name);

    List<RequestCase> SelList(Set<Long> Ids);

    RequestCase SelOne(Long name);

    Long Del(Set<Long> Ids);

    Long Ins(List<RequestCase> database_case);

    void Upd(List<RequestCase> database_case);

}


