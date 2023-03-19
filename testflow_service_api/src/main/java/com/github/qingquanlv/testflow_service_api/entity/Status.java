package com.github.qingquanlv.testflow_service_api.entity;

import lombok.Data;

@Data
public class Status {

    private Boolean success;
    private int errorCode;
    private String message;

}
