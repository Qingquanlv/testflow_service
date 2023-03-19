package com.github.qingquanlv.testflow_service_api.service;

import com.github.qingquanlv.testflow_service_api.model.dto.EnvTabDto;
import java.util.List;

/**
 * (EnvTab)表服务接口
 *
 * @author qingquan.lv
 * @since 2023-03-05 14:31:35
 */
public interface EnvTabService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    EnvTabDto queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<EnvTabDto> queryAllByLimit(long appId, int offset, int limit);

    /**
     * 新增数据
     *
     * @param envTabDto 实例对象
     * @return 实例对象
     */
    EnvTabDto insert(EnvTabDto envTabDto);

    /**
     * 新增多条数据
     *
     * @param envTabDtoList 实例对象
     * @return 实例对象
     */
    boolean insertBatch(List<EnvTabDto> envTabDtoList);

    /**
     * 修改数据
     *
     * @param envTabDto 实例对象
     * @return 实例对象
     */
    EnvTabDto update(EnvTabDto envTabDto);

    /**
     * 通过AppId删除数据
     * @param appId 应用ID
     * @return 是否成功
     */
    boolean deleteByAppId(Long appId);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
