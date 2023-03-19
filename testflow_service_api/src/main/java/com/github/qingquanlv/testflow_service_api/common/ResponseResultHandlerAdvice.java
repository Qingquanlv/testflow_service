package com.github.qingquanlv.testflow_service_api.common;

import com.github.qingquanlv.testflow_service_api.model.po.ResponseCode;
import com.github.qingquanlv.testflow_service_api.model.po.ResponseResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Author: qingquan.lv
 * @Date: 2023/3/5 19:14
 */
@ControllerAdvice(annotations = BaseResponse.class)
public class ResponseResultHandlerAdvice implements ResponseBodyAdvice {
    // 如果接口返回的类型本身就是统一响应体的格式，那就没有必要进行额外的操作，返回true
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(MediaType.APPLICATION_JSON.equals(selectedContentType) || MediaType.APPLICATION_JSON_UTF8.equals(selectedContentType)){ // 判断响应的Content-Type为JSON格式的body
            // 如果响应返回的对象为统一响应体，则直接返回body
            if(body instanceof ResponseResult){
                return body;
            }else{
                // 只有正常返回的结果才会进入这个判断流程，返回正常成功的状态码+信息+数据。
                ResponseResult responseResult =new ResponseResult(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),body);
                return responseResult;
            }
        }
        // 非JSON格式body直接返回即可
        return body;
    }
}

