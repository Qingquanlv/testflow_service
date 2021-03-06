package com.github.qingquanlv.testflow_service_api.mapper;



import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.FeatureCaseNextCase;

import java.util.List;
import java.util.Set;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/7 22:41
 * @Version 1.0
 */
public interface FeatureCaseNextCaseMapper {

    List<FeatureCaseNextCase> SelListByFCId(Set<Long> feature_case_ids);

    List<FeatureCaseNextCase> SelByFCId(Long feature_case_id);

    Long Del(Set<Long> id);

    Long Ins(List<FeatureCaseNextCase> database_next_cases);

    FeatureCaseNextCase Upd(FeatureCaseNextCase database_next_case);

}
