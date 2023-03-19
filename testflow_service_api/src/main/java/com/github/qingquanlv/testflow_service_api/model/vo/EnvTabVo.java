package com.github.qingquanlv.testflow_service_api.model.vo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: qingquan.lv
 * @Date: 2023/3/5 16:37
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "信息类")
public class EnvTabVo extends Model<EnvTabVo> implements Serializable {
    private static final long serialVersionUID = -24936979609217006L;

    @ApiModelProperty("$column.comment")
    private Long appId;

    @ApiModelProperty("$column.comment")
    private String caseType;

    @ApiModelProperty("$column.comment")
    private String configKey;

    @ApiModelProperty("$column.comment")
    private String configVal;
}
