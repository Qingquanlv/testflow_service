package com.github.qingquanlv.testflow_service_api.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: qingquan.lv
 * @Date: 2023/3/5 19:17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult implements Serializable {
    /**
     * 返回的状态码
     */
    private Integer code;
    /**
     * 返回的信息
     */
    private String msg;
    /**
     * 返回的数据
     */
    private Object data;
}
