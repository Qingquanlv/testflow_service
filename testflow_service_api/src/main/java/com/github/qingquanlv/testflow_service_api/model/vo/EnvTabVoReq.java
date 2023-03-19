package com.github.qingquanlv.testflow_service_api.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "信息类")
public class EnvTabVoReq  {

    @ApiModelProperty("$column.comment")
    private String requestId;

    @ApiModelProperty("$column.comment")
    private Long appId;

    @ApiModelProperty("$column.comment")
    private List<EnvTabVo> envConfigs;
}


