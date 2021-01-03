package com.github.qingquanlv.testflow_service_api.entity.testflow_service_db;

import java.sql.Timestamp;

/**
 * @Author Qingquan Lv
 * @Date 2021/1/2 11:12
 * @Version 1.0
 */
public class RequestCase {

    //case_id
    private Long case_id;
    //case_type
    private String case_name;
    //url
    private String url;
    //request_body
    private String request_body;
    //request_type
    private String request_type;
    //content_type
    private String content_type;
    //request_header
    private String request_headers;
    //request_configs
    private String request_configs;
    //时间戳
    private Timestamp datachanged_lasttime;

    public Long getCase_id() {
        return case_id;
    }

    public void setCase_id(Long case_id) {
        this.case_id = case_id;
    }

    public String getCase_name() {
        return case_name;
    }

    public void setCase_name(String case_name) {
        this.case_name = case_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequest_body() {
        return request_body;
    }

    public void setRequest_body(String request_body) {
        this.request_body = request_body;
    }

    public String getRequest_type() {
        return request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getRequest_headers() {
        return request_headers;
    }

    public void setRequest_headers(String request_headers) {
        this.request_headers = request_headers;
    }

    public String getRequest_configs() {
        return request_configs;
    }

    public void setRequest_configs(String request_configs) {
        this.request_configs = request_configs;
    }

    public Timestamp getDatachanged_lasttime() {
        return datachanged_lasttime;
    }

    public void setDatachanged_lasttime(Timestamp datachanged_lasttime) {
        this.datachanged_lasttime = datachanged_lasttime;
    }
}
