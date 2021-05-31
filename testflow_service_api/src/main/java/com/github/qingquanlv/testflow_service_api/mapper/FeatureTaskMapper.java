package com.github.qingquanlv.testflow_service_api.mapper;

import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.FeatureTask;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.ParameterCase;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: qingquan.lv
 * @Date: 2021/5/30 19:09
 */
@Repository
public interface FeatureTaskMapper {

        Long Ins(FeatureTask featureTask);
}
