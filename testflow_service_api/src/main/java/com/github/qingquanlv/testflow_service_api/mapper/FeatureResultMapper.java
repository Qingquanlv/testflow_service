package com.github.qingquanlv.testflow_service_api.mapper;

import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.FeatureResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: qingquan.lv
 * @Date: 2021/4/8 08:42
 */
@Repository
public interface FeatureResultMapper {

        FeatureResult selByTidSid(@Param("taskId") Long taskId, @Param("stepId") Long stepId);

        List<FeatureResult> selByTid(Long taskId);

        List<FeatureResult> selAll();

        void ins(FeatureResult item);

        void insList(List<FeatureResult> items);
}
