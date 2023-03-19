package com.github.qingquanlv.testflow_service_api.common;

import com.github.qingquanlv.testflow_service_api.model.po.ResponseCode;
import com.github.qingquanlv.testflow_service_api.model.po.ResponseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: qingquan.lv
 * @Date: 2023/3/5 19:33
 */
@ControllerAdvice(annotations = BaseResponse.class)
@ResponseBody
public class ExceptionHandlerAdvice {

    /**
     * 处理未捕获的Exception
     * @param e 异常
     * @return 统一响应体
     * data:null
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult handleException(Exception e){
        return new ResponseResult(ResponseCode.SERVICE_ERROR.getCode(),ResponseCode.SERVICE_ERROR.getMsg(),null);
    }

    /**
     * 处理未捕获的RuntimeException
     * @param e 运行异常
     * @return 统一响应体
     * data:null
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseResult handleRuntimeException(RuntimeException e){
        return new ResponseResult(ResponseCode.SERVICE_ERROR.getCode(),ResponseCode.SERVICE_ERROR.getMsg(),null);
    }

    /**
     * 处理业务异常BaseException
     * @param e 业务异常
     * @return 统一响应体
     * data:null
     */
    @ExceptionHandler(BaseException.class)
    public ResponseResult handleBaseException(BaseException e){
        ResponseCode code=e.getCode();
        return new ResponseResult(code.getCode(),code.getMsg(),null);
    }
}
