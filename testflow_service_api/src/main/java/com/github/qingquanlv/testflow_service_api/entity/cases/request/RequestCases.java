package com.github.qingquanlv.testflow_service_api.entity.cases.request;

import com.github.qingquanlv.testflow_service_api.entity.cases.BaseCase;

import java.util.List;

/**
 * @Author Qingquan Lv
 * @Version 1.0
 */
public class RequestCases extends BaseCase {
    //请求配置
    private List<RequestConfig> configs;
    //请求头
    private List<RequestHeader> headers;
    //请求body
    private String requestBody;
    //请求类型
    private String requestType;
    //请求body类型
    private String contentType;
    //请求Url
    private String url;


    public List<RequestConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<RequestConfig> configs) {
        this.configs = configs;
    }

    public List<RequestHeader> getHeaders() {
        return headers;
    }

    public void setHeaders(List<RequestHeader> headers) {
        this.headers = headers;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
