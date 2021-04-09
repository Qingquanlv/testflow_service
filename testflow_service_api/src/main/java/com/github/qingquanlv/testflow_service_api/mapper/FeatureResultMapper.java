package com.github.qingquanlv.testflow_service_api.mapper;

import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.FeatureResult;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.ParameterCase;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: qingquan.lv
 * @Date: 2021/4/8 08:42
 */
@Repository
public interface FeatureResultMapper {

        List<FeatureResult> selByFid(Long id);

        List<FeatureResult> selAll();

        void ins(FeatureResult item);
}
