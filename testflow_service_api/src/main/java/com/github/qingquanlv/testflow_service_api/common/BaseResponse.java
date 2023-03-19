package com.github.qingquanlv.testflow_service_api.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: qingquan.lv
 * @Date: 2023/3/5 19:15
 */
@Retention(RetentionPolicy.RUNTIME)  //运行时生效
@Target({ElementType.METHOD, ElementType.TYPE}) // 用于描述注解的使用范围
public @interface BaseResponse {
}

