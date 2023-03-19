package com.github.qingquanlv.testflow_service_api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.ParameterIndexStepResult;

import java.util.List;

/**
 * @Author: qingquan.lv
 */
public interface ParameterIndexStepResultMapper extends BaseMapper<ParameterIndexStepResult> {

        boolean saveBatchByNative(List<ParameterIndexStepResult> list);

}
