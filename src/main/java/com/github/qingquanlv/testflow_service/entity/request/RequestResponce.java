package com.github.qingquanlv.testflow_service.entity.request;

import com.github.qingquanlv.testflow_service.entity.Status;

public class RequestResponce {

    private Status status;

    private String content;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
