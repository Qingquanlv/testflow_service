package com.github.qingquanlv.testflow_service_api.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.qingquanlv.testflow_service_api.dao.EnvTabMapper;
import com.github.qingquanlv.testflow_service_api.model.dto.EnvTabDto;
import com.github.qingquanlv.testflow_service_api.model.mapper.EnvTabModelMapper;
import com.github.qingquanlv.testflow_service_api.model.po.EnvTabPo;
import com.github.qingquanlv.testflow_service_api.service.EnvTabService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (EnvTab)表服务实现类
 *
 * @author qingquan.lv
 * @since 2023-03-05 14:31:35
 */
@Service("envTabService")
public class EnvTabServiceImpl implements EnvTabService {
    @Resource
    private EnvTabMapper envTabMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public EnvTabDto queryById(Long id) {
        return EnvTabModelMapper.INSTANCE.changePO2DTO(this.envTabMapper.selectById(id));
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<EnvTabDto> queryAllByLimit(long appId, int offset, int limit) {
        IPage<EnvTabPo> selectPage = new Page<>(offset,limit);
        Wrapper<EnvTabPo> queryWrapper = Wrappers.<EnvTabPo>lambdaQuery()
            .eq(EnvTabPo::getAppId, appId);
        IPage<EnvTabPo> page = this.envTabMapper.selectPage(selectPage, queryWrapper);
        return EnvTabModelMapper.INSTANCE.envTabPOs2EnvTabDTOs(page.getRecords());
    }

    /**
     * 新增数据
     *
     * @param envTabDto 实例对象
     * @return 实例对象
     */
    @Override
    public EnvTabDto insert(EnvTabDto envTabDto) {
        this.envTabMapper.insert(EnvTabModelMapper.INSTANCE.changeDTO2PO(envTabDto));
        return envTabDto;
    }

    /**
     * 新增多条数据
     *
     * @param envTabDtoList 实例对象
     * @return 实例对象
     */
    @Override
    public boolean insertBatch(List<EnvTabDto> envTabDtoList) {
        List<EnvTabPo> envTabPoList = EnvTabModelMapper.INSTANCE.envTabDTOs2EnvTabPOs(envTabDtoList);
        if (!envTabPoList.isEmpty()) {
            Wrapper<EnvTabPo> queryWrapper = Wrappers.<EnvTabPo>lambdaQuery()
                .eq(EnvTabPo::getAppId, envTabPoList.get(0).getAppId());
        }
        return this.envTabMapper.insertBatch(envTabPoList)>0;
    }

    /**
     * 修改数据
     *
     * @param envTabDto 实例对象
     * @return 实例对象
     */
    @Override
    public EnvTabDto update(EnvTabDto envTabDto) {
        this.envTabMapper.updateById(EnvTabModelMapper.INSTANCE.changeDTO2PO(envTabDto));
        return this.queryById(envTabDto.getId());
    }

    /**
     * 通过AppId删除数据
     * @param appId 应用ID
     * @return 是否成功
     */
    @Override
    public boolean deleteByAppId(Long appId) {
        Wrapper<EnvTabPo> queryWrapper = Wrappers.<EnvTabPo>lambdaQuery()
            .eq(EnvTabPo::getAppId, appId);
        return this.envTabMapper.delete(queryWrapper) > 0;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.envTabMapper.deleteById(id) > 0;
    }
}
