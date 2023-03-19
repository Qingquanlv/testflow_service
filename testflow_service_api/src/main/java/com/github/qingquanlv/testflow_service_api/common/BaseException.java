package com.github.qingquanlv.testflow_service_api.common;

import com.github.qingquanlv.testflow_service_api.model.po.ResponseCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: qingquan.lv
 * @Date: 2023/3/5 19:28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseException extends RuntimeException{

    private ResponseCode code;  // 枚举对象

    public BaseException(ResponseCode code) {
        this.code = code;
    }

    public BaseException(Throwable cause, ResponseCode code) {
        super(cause);
        this.code = code;
    }
}
