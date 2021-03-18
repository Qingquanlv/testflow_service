package com.github.qingquanlv.testflow_service_api.mapper;

import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.Feature;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/2 11:05
 * @Version 1.0
 */
@Repository
public interface FeatureMapper {

    List<Feature> SelAll();

    Feature SelByName(String name);

    Feature Sel(Long Id);

    Long Del(Long case_id);

    void Ins(Feature feature);

    Feature Upd(Feature feature);

}

