package com.github.qingquanlv.testflow_service_api.model.mapper;


import com.github.qingquanlv.testflow_service_api.model.dto.EnvTabDto;
import com.github.qingquanlv.testflow_service_api.model.po.EnvTabPo;
import com.github.qingquanlv.testflow_service_api.model.vo.EnvTabVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * (EnvTab)实体转换工具类
 *
 * @author qingquan.lv
 * @since 2023-03-05 14:31:33
 */
@Mapper
public interface EnvTabModelMapper {
    EnvTabModelMapper INSTANCE = Mappers.getMapper(EnvTabModelMapper.class);
    
    EnvTabDto changePO2DTO(EnvTabPo envTabPo);
    EnvTabPo changeDTO2PO(EnvTabDto envTabDto);
    EnvTabVo changeDTO2VO(EnvTabDto envTabDto);
    EnvTabDto changeVO2DTO(EnvTabVo envTabVo);

    List<EnvTabDto> envTabPOs2EnvTabDTOs(List<EnvTabPo> envTabPoList);
    List<EnvTabPo> envTabDTOs2EnvTabPOs(List<EnvTabDto> envTabDtoList);
    List<EnvTabDto> envTabVOs2EnvTabDTOs(List<EnvTabVo> envTabVoList);
    List<EnvTabVo> envTabDTOs2EnvTabVOs(List<EnvTabDto> envTabDtoList);
}
