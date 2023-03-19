package com.github.qingquanlv.testflow_service_api.controller;

import com.github.qingquanlv.testflow_service_api.common.BaseResponse;
import com.github.qingquanlv.testflow_service_api.model.dto.EnvTabDto;
import com.github.qingquanlv.testflow_service_api.model.mapper.EnvTabModelMapper;
import com.github.qingquanlv.testflow_service_api.model.vo.EnvTabVo;
import com.github.qingquanlv.testflow_service_api.model.vo.EnvTabVoReq;
import com.github.qingquanlv.testflow_service_api.service.EnvTabService;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (EnvTab)表控制层
 *
 * @author qingquan.lv
 * @since 2023-03-05 14:31:36
 */
@Api(tags = "(Env)")
@BaseResponse
@Validated
@RestController
@RequestMapping("env")
public class EnvTabController {
    /**
     * 服务对象
     */
    @Resource
    private EnvTabService envTabService;

    /**
     * 通过AppID查询多条数据
     *
     * @param appId 应用ID
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 多条数据
     */
    @GetMapping("query")
    public List<EnvTabVo> selectList(
        @RequestParam(value = "appId", required = true) long appId,
        @RequestParam(value = "offset", defaultValue = "1") int offset,
        @RequestParam(value = "limit", defaultValue = "20") int limit){
        List<EnvTabDto> envTabDtos = envTabService.queryAllByLimit(appId, offset, limit);
        return EnvTabModelMapper.INSTANCE.envTabDTOs2EnvTabVOs(envTabDtos);
    }

    /**
     * 通过AppID新增多条数据
     *
     * @param envTabVoReq 多条数据
     * @return 新增结果
     */
    @PostMapping("update")
    public boolean insert(@RequestBody EnvTabVoReq envTabVoReq){
        List<EnvTabVo> envTabVos = envTabVoReq.getEnvConfigs();
        if (envTabVos == null || envTabVos.size() == 0){
            return true;
        }
        envTabVos.forEach(envTabVo -> envTabVo.setAppId(envTabVoReq.getAppId()));
        List<EnvTabDto> envTabDtos = EnvTabModelMapper.INSTANCE.envTabVOs2EnvTabDTOs(envTabVos);
        return envTabService.insertBatch(envTabDtos);
    }

    /**
     * 通过Id删除数据
     *
     * @param appId 主键
     * @return 删除结果
     */
    @GetMapping("delete")
    public Boolean delete(
        @RequestParam(value = "appId", required = true) Long appId){
        return envTabService.deleteByAppId(appId);
    }

}


