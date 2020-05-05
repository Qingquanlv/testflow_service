package com.github.qingquanlv.testflow_service.entity.testflowrequest;

import com.github.qingquanlv.testflow_service.entity.request.RequestRequest;

import java.util.List;

public class TestFlowRequest {

    //请求唯一标识
    private String requestId;

    //测试用例
    private List<RequestRequest> requestRequest;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public List<RequestRequest> getRequestRequest() {
        return requestRequest;
    }

    public void setRequestRequest(List<RequestRequest> requestRequest) {
        this.requestRequest = requestRequest;
    }
}
