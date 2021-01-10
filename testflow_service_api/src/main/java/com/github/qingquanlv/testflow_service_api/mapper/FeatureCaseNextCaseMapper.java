package com.github.qingquanlv.testflow_service_api.mapper;



import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.FeatureCaseNextCase;

import java.util.List;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/7 22:41
 * @Version 1.0
 */
public interface FeatureCaseNextCaseMapper {

    List<FeatureCaseNextCase> SelByFCId(Long id);

    List<FeatureCaseNextCase> SelecltByFeatureId(Long featureId);

    Long Del(Long case_id);

    Long Ins(List<FeatureCaseNextCase> database_next_cases);

    FeatureCaseNextCase Upd(FeatureCaseNextCase database_next_case);

}
