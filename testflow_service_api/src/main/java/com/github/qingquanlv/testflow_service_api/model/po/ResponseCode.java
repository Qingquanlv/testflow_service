package com.github.qingquanlv.testflow_service_api.model.po;

/**
 * @Author: qingquan.lv
 * @Date: 2023/3/5 19:25
 */
public enum ResponseCode {
    /**
     * 成功返回的状态码
     */
    SUCCESS(10000, "success"),
    /**
     * 资源不存在的状态码
     */
    RESOURCES_NOT_EXIST(10001, "资源不存在"),
    /**
     * 所有无法识别的异常默认的返回状态码
     */
    SERVICE_ERROR(50000, "服务器异常");
    /**
     * 状态码
     */
    private int code;
    /**
     * 返回信息
     */
    private String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
