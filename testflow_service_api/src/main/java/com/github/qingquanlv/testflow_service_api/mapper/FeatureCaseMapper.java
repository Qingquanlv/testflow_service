package com.github.qingquanlv.testflow_service_api.mapper;

import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Feature;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.FeatureCase;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/2 12:13
 * @Version 1.0
 */
@Repository
public interface FeatureCaseMapper {

    List<FeatureCase> Sel(String name);

    Long Del(Long case_id);

    Long Insert(List<FeatureCase> database_case);

    Feature Update(FeatureCase database_case);

}

