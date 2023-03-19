package com.github.qingquanlv.testflow_service_api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.qingquanlv.testflow_service_api.entity.testflow_service_db.ParameterDetailsTab;

import java.util.List;

/**
 * @Author: qingquan.lv
 * @Date: 2023/2/27 下午12:13
 */
public interface ParameterDetailsMapper extends BaseMapper<ParameterDetailsTab> {
        boolean saveBatchByNative(List<ParameterDetailsTab> list);
}
