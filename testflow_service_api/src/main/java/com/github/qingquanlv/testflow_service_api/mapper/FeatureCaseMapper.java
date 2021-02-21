package com.github.qingquanlv.testflow_service_api.mapper;

import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.FeatureCase;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/2 12:13
 * @Version 1.0
 */
@Repository
public interface FeatureCaseMapper {

    List<FeatureCase> Sel(Long id);

    List<FeatureCase> SelByFId(Long id);

    FeatureCase SelOne(Long featureId);

    Long Del(Long Id);

    Long DelByFId(Set<Long> Id);

    Long Ins(List<FeatureCase> feature_cases);

    Long InsOne(FeatureCase feature_case);

    FeatureCase Upd(FeatureCase feature_case);

}

