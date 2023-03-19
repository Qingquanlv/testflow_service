package com.github.qingquanlv.testflow_service_api.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.qingquanlv.testflow_service_api.model.po.EnvTabPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (EnvTab)表数据库访问层
 *
 * @author qingquan.lv
 * @since 2023-03-05 14:31:34
 */
@Mapper
public interface EnvTabMapper extends BaseMapper<EnvTabPo>{

    int insertBatch(List<EnvTabPo> envTabPoList);
}
